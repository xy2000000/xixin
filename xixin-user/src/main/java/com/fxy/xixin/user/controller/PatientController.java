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
import com.fxy.xixin.common.context.UserContext;
import com.fxy.xixin.common.constant.ErrorCode;
import com.fxy.xixin.common.exception.BusinessException;

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
     * 根据用户ID查询体检人详情
     *
     * <p><b>权限：PATIENT（仅自己）、DOCTOR、ADMIN</b></p>
     *
     * @param id 关联的系统用户ID（userId）
     * @return 体检人详细信息（含姓名、性别、出生日期、身份证号、紧急联系人等）
     */
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    @GetMapping("/{id}")
    public R<Patient> getById(@PathVariable Long id) {
        // 患者只能查看自己的档案
        if ("PATIENT".equalsIgnoreCase(UserContext.getRole())) {
            Long currentUserId = UserContext.getUserId();
            if (currentUserId == null || !currentUserId.equals(id)) {
                throw new BusinessException(ErrorCode.FORBIDDEN, "无权限查看他人档案");
            }
        }
        return R.ok(patientService.lambdaQuery().eq(Patient::getUserId, id).one());
    }

    /**
     * 更新体检人档案
     *
     * <p><b>权限：PATIENT（仅自己）、ADMIN</b></p>
     *
     * @param id 关联的系统用户ID（userId）
     * @param patient 更新的档案信息
     * @return 更新后的档案
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @PutMapping("/{id}")
    public R<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        // 患者只能更新自己的档案
        if ("PATIENT".equalsIgnoreCase(UserContext.getRole())) {
            Long currentUserId = UserContext.getUserId();
            if (currentUserId == null || !currentUserId.equals(id)) {
                throw new BusinessException(ErrorCode.FORBIDDEN, "无权限修改他人档案");
            }
        }
        Patient existing = patientService.lambdaQuery().eq(Patient::getUserId, id).one();
        if (existing == null) {
            throw new BusinessException(ErrorCode.PATIENT_NOT_EXIST, "档案不存在");
        }
        patient.setId(existing.getId());
        patient.setUserId(id);
        patientService.updateById(patient);
        return R.ok(patientService.getById(existing.getId()));
    }
}