package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.context.UserContext;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.exception.BusinessException;
import com.fxy.xixin.common.mq.DbWriteEvent;
import com.fxy.xixin.common.mq.MessageSender;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.Appointment;
import com.fxy.xixin.exam.entity.ExamPackage;
import com.fxy.xixin.exam.service.AppointmentService;
import com.fxy.xixin.exam.service.ExamPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检预约控制器
 * <p>
 * 提供体检预约的 RESTful API 接口。
 * 每个体检套餐绑定一名医生，患者预约后系统自动分配医生并确认预约。
 * </p>
 *
 * <p>预约生命周期：</p>
 * <ol>
 *   <li>患者提交预约 → 自动确认（status=1），医生从套餐绑定的 doctorId 自动分配</li>
 *   <li>患者可取消预约 → status=2</li>
 *   <li>体检完成 → status=3</li>
 * </ol>
 *
 * <p><b>注意：</b>写入操作（创建/取消/完成/删除）通过 RabbitMQ 缓冲，
 * 请求先发到 MQ 队列再异步写入数据库，利用 MQ 持久化实现削峰填谷。</p>
 *
 * <p><b>权限：查询供所有角色 / 提交和取消仅 PATIENT / 删除仅 ADMIN</b></p>
 */
@RestController
@RequestMapping("/api/exam/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ExamPackageService examPackageService;
    private final MessageSender messageSender;

    @GetMapping
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<?> list(PageDTO page) {
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<Appointment>()
                .orderByDesc(Appointment::getCreateTime);

        if ("PATIENT".equalsIgnoreCase(role)) {
            wrapper.eq(Appointment::getUserId, userId);
            List<Appointment> list = appointmentService.list(wrapper);
            return R.ok(list);
        } else if ("DOCTOR".equalsIgnoreCase(role)) {
            wrapper.eq(Appointment::getDoctorId, userId);
            IPage<Appointment> result = appointmentService.page(page.toPage(), wrapper);
            return R.ok(PageResult.of(result));
        } else {
            IPage<Appointment> result = appointmentService.page(page.toPage(), wrapper);
            return R.ok(PageResult.of(result));
        }
    }

    @GetMapping("/{id}")
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<Appointment> getById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) {
            return R.ok();
        }

        String role = UserContext.getRole();
        Long userId = UserContext.getUserId();

        if ("PATIENT".equalsIgnoreCase(role) && !appointment.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看他人的预约");
        }
        if ("DOCTOR".equalsIgnoreCase(role) && !appointment.getDoctorId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看未分配给您的预约");
        }

        return R.ok(appointment);
    }

    /**
     * 提交体检预约（通过 MQ 缓冲写入）
     * <p>
     * 校验逻辑同步执行，DB 写入通过 MQ 异步完成。
     * 请求先入 MQ 队列，消费者逐条写入 DB，天然实现削峰填谷。
     * </p>
     */
    @PostMapping
    @RequireRole({"PATIENT"})
    public R<Void> save(@RequestBody Appointment appointment) {
        Long userId = UserContext.getUserId();
        appointment.setUserId(userId);
        appointment.setStatus(1);

        if (appointment.getDoctorId() == null) {
            ExamPackage examPackage = examPackageService.getById(appointment.getPackageId());
            if (examPackage == null) {
                throw new BusinessException(ErrorCode.EXAM_PACKAGE_NOT_EXIST);
            }
            if (examPackage.getDoctorId() != null) {
                appointment.setDoctorId(examPackage.getDoctorId());
            }
        }

        messageSender.sendDbWriteEvent(DbWriteEvent.builder()
                .entityType("appointment")
                .action(DbWriteEvent.Action.CREATE)
                .data(appointment)
                .operatorId(userId)
                .build());

        return R.ok();
    }

    /**
     * 取消预约（通过 MQ 缓冲写入）
     */
    @PutMapping("/{id}/cancel")
    @RequireRole({"PATIENT"})
    public R<Void> cancel(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) {
            return R.ok();
        }

        Long userId = UserContext.getUserId();
        if (!appointment.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "只能取消自己的预约");
        }

        appointment.setStatus(2);

        messageSender.sendDbWriteEvent(DbWriteEvent.builder()
                .entityType("appointment")
                .action(DbWriteEvent.Action.UPDATE)
                .data(appointment)
                .operatorId(userId)
                .build());

        return R.ok();
    }

    /**
     * 标记体检完成（通过 MQ 缓冲写入）
     */
    @PutMapping("/{id}/complete")
    @RequireRole({"DOCTOR", "ADMIN"})
    public R<Void> complete(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) {
            throw new BusinessException(ErrorCode.APPOINTMENT_NOT_EXIST);
        }

        String role = UserContext.getRole();
        Long userId = UserContext.getUserId();

        if ("DOCTOR".equalsIgnoreCase(role) && !appointment.getDoctorId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权完成未分配给您的预约");
        }

        if (appointment.getStatus() != 1) {
            throw new BusinessException(ErrorCode.APPOINTMENT_CANNOT_CANCEL, "当前状态不可标记完成");
        }

        appointment.setStatus(3);

        messageSender.sendDbWriteEvent(DbWriteEvent.builder()
                .entityType("appointment")
                .action(DbWriteEvent.Action.UPDATE)
                .data(appointment)
                .operatorId(userId)
                .build());

        return R.ok();
    }

    /**
     * 删除预约（通过 MQ 缓冲写入）
     */
    @DeleteMapping("/{id}")
    @RequireRole({"ADMIN"})
    public R<Void> delete(@PathVariable Long id) {
        messageSender.sendDbWriteEvent(DbWriteEvent.builder()
                .entityType("appointment")
                .action(DbWriteEvent.Action.DELETE)
                .data(id)
                .operatorId(UserContext.getUserId())
                .build());

        return R.ok();
    }
}