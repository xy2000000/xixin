package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.context.UserContext;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.exception.BusinessException;
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
 * <p><b>权限：查询供所有角色 / 提交和取消仅 PATIENT / 删除仅 ADMIN</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ExamPackageService examPackageService;

    /**
     * 查询预约列表
     * <p>
     * 根据角色返回不同的数据范围：
     * <ul>
     *   <li>PATIENT：返回当前患者的全部预约（不分页），按创建时间倒序</li>
     *   <li>DOCTOR：返回分配给当前医生的预约（分页），按创建时间倒序</li>
     *   <li>ADMIN：返回全部预约（分页），按创建时间倒序</li>
     * </ul>
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param page 分页参数（PATIENT 角色忽略此参数）
     * @return PATIENT 返回全部预约列表，DOCTOR/ADMIN 返回分页数据
     */
    @GetMapping
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<?> list(PageDTO page) {
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        LambdaQueryWrapper<Appointment> wrapper = new LambdaQueryWrapper<Appointment>()
                .orderByDesc(Appointment::getCreateTime);

        if ("PATIENT".equalsIgnoreCase(role)) {
            // 患者：返回本人的全部预约，不分页
            wrapper.eq(Appointment::getUserId, userId);
            List<Appointment> list = appointmentService.list(wrapper);
            return R.ok(list);
        } else if ("DOCTOR".equalsIgnoreCase(role)) {
            // 医生：只查看分配给自己的预约，保持分页
            wrapper.eq(Appointment::getDoctorId, userId);
            IPage<Appointment> result = appointmentService.page(page.toPage(), wrapper);
            return R.ok(PageResult.of(result));
        } else {
            // 管理员：查看全部预约，保持分页
            IPage<Appointment> result = appointmentService.page(page.toPage(), wrapper);
            return R.ok(PageResult.of(result));
        }
    }

    /**
     * 根据ID查询预约详情
     * <p>
     * PATIENT 只能查看自己的预约，DOCTOR 只能查看分配给自己的预约，ADMIN 可查看任意预约。
     * </p>
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param id 预约ID
     * @return 预约详情
     */
    @GetMapping("/{id}")
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    public R<Appointment> getById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) {
            return R.ok();
        }

        String role = UserContext.getRole();
        Long userId = UserContext.getUserId();

        // 患者只能看自己的预约，医生只能看分配给自己的预约
        if ("PATIENT".equalsIgnoreCase(role) && !appointment.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看他人的预约");
        }
        if ("DOCTOR".equalsIgnoreCase(role) && !appointment.getDoctorId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看未分配给您的预约");
        }

        return R.ok(appointment);
    }

    /**
     * 提交体检预约（自动确认并分配医生）
     * <p>
     * 患者选择套餐、机构、日期后提交预约。系统自动完成以下操作：
     * <ol>
     *   <li>从 Token 中获取当前用户ID，覆盖请求中的 userId（防止代他人预约）</li>
     *   <li>将预约状态设为"已确认"（status=1），无需管理员手动确认</li>
     *   <li>如果请求未指定医生，则从套餐绑定的 doctorId 自动分配</li>
     * </ol>
     * </p>
     *
     * <p><b>权限：PATIENT</b></p>
     *
     * @param appointment 预约信息（含套餐ID、机构ID、预约日期、时间段；userId 和 doctorId 由系统自动填充）
     * @return 操作结果
     */
    @PostMapping
    @RequireRole({"PATIENT"})
    public R<Void> save(@RequestBody Appointment appointment) {
        // 从 Token 中获取当前用户ID，确保预约归属正确
        Long userId = UserContext.getUserId();
        appointment.setUserId(userId);

        // 预约直接确认为"已确认"状态
        appointment.setStatus(1);

        // 如果未指定医生，从套餐绑定的 doctorId 自动分配
        if (appointment.getDoctorId() == null) {
            ExamPackage examPackage = examPackageService.getById(appointment.getPackageId());
            if (examPackage == null) {
                throw new BusinessException(ErrorCode.EXAM_PACKAGE_NOT_EXIST);
            }
            if (examPackage.getDoctorId() != null) {
                appointment.setDoctorId(examPackage.getDoctorId());
            }
        }

        appointmentService.save(appointment);
        return R.ok();
    }

    /**
     * 取消预约
     * <p>
     * 患者只能取消本人的预约，状态变为"已取消"（status=2）。
     * </p>
     *
     * <p><b>权限：PATIENT</b></p>
     *
     * @param id 预约ID
     * @return 操作结果
     */
    @PutMapping("/{id}/cancel")
    @RequireRole({"PATIENT"})
    public R<Void> cancel(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) {
            return R.ok();
        }

        // 只有预约本人可以取消
        Long userId = UserContext.getUserId();
        if (!appointment.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "只能取消自己的预约");
        }

        // 状态改为"已取消"
        appointment.setStatus(2);
        appointmentService.updateById(appointment);
        return R.ok();
    }

    /**
     * 删除预约记录（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 预约ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @RequireRole({"ADMIN"})
    public R<Void> delete(@PathVariable Long id) {
        appointmentService.removeById(id);
        return R.ok();
    }
}
