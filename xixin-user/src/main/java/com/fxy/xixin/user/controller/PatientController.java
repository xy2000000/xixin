package com.fxy.xixin.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.user.entity.Patient;
import com.fxy.xixin.user.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

/**
 * 体检人控制器
 * <p>
 * 提供体检人档案的分页查询和详情查看接口。
 * 在体检预约流程中，医生需要查看患者档案以了解其健康状况，
 * 为体检评估提供参考依据。
 * </p>
 *
 * <p><b>权限：DOCTOR、ADMIN</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/user/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /**
     * 分页查询体检人列表
     *
     * <p><b>权限：DOCTOR、ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 体检人分页数据
     */
    @RequireRole({"DOCTOR", "ADMIN"})
    @GetMapping
    public R<PageResult<Patient>> list(PageDTO page) {
        IPage<Patient> result = patientService.page(page.toPage());
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询体检人详情
     *
     * <p><b>权限：DOCTOR、ADMIN</b></p>
     *
     * @param id 体检人ID
     * @return 体检人详细信息（含姓名、性别、出生日期、身份证号、紧急联系人等）
     */
    @RequireRole({"DOCTOR", "ADMIN"})
    @GetMapping("/{id}")
    public R<Patient> getById(@PathVariable Long id) {
        return R.ok(patientService.getById(id));
    }
}