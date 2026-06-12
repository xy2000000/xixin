package com.fxy.xixin.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.user.entity.Doctor;
import com.fxy.xixin.user.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

/**
 * 医生信息控制器
 * <p>
 * 提供医生信息的分页查询和详情查看接口。
 * 在体检预约流程中，患者和管理员通过此接口查询可预约的医生及其专业信息。
 * </p>
 *
 * <p><b>权限：DOCTOR、ADMIN</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/user/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * 分页查询医生列表
     *
     * <p><b>权限：DOCTOR、ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 医生分页数据
     */
    @RequireRole({"DOCTOR", "ADMIN"})
    @GetMapping
    public R<PageResult<Doctor>> list(PageDTO page) {
        IPage<Doctor> result = doctorService.page(page.toPage());
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询医生详情
     *
     * <p><b>权限：DOCTOR、ADMIN</b></p>
     *
     * @param id 医生ID
     * @return 医生详细信息（含科室、职称、专长、简介）
     */
    @RequireRole({"DOCTOR", "ADMIN"})
    @GetMapping("/{id}")
    public R<Doctor> getById(@PathVariable Long id) {
        return R.ok(doctorService.getById(id));
    }
}