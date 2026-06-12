package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.ApptTypeInstitution;
import com.fxy.xixin.exam.entity.ExamPackage;
import com.fxy.xixin.exam.entity.Institution;
import com.fxy.xixin.exam.entity.InstitutionPackage;
import com.fxy.xixin.exam.mapper.ApptTypeInstitutionMapper;
import com.fxy.xixin.exam.mapper.InstitutionPackageMapper;
import com.fxy.xixin.exam.service.ExamPackageService;
import com.fxy.xixin.exam.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 体检预约流程控制器
 * <p>
 * 负责体检预约前端流程的级联选择逻辑。在预约流程中提供两步级联接口：
 * <ol>
 *   <li>选择预约类型（个人/团体/入职/出入境）→ 获取该类型可用的机构列表</li>
 *   <li>选择机构 → 获取该机构提供的上架套餐列表</li>
 * </ol>
 * 用户完成套餐选择后，通过 {@link AppointmentController} 提交最终的预约信息。
 * </p>
 *
 * <p><b>权限：所有接口供 PATIENT（患者）使用</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class AppointmentFlowController {

    private final ApptTypeInstitutionMapper apptTypeInstitutionMapper;
    private final InstitutionPackageMapper institutionPackageMapper;
    private final InstitutionService institutionService;
    private final ExamPackageService examPackageService;

    /**
     * 根据预约类型获取可用的机构列表
     * <p>
     * 用户在预约流程第一步选择预约类型后，调用此接口获取支持该预约类型的机构。
     * 仅返回启用状态（status=1）的机构，按排序号升序排列。
     * </p>
     *
     * <p><b>权限：PATIENT</b></p>
     *
     * @param type 预约类型（individual=个人, group=团体, onboarding=入职, travel=出入境）
     * @return 该类型可用的启用机构列表
     */
    @RequireRole({"PATIENT"})
    @GetMapping("/appointment-types/{type}/institutions")
    public R<List<Institution>> getInstitutionsByType(@PathVariable String type) {
        // 第一步：查询预约类型-机构关联表，获取该类型对应的机构ID列表
        List<ApptTypeInstitution> relations = apptTypeInstitutionMapper.selectList(
                new LambdaQueryWrapper<ApptTypeInstitution>()
                        .eq(ApptTypeInstitution::getApptType, type));

        List<Long> institutionIds = new ArrayList<>();
        for (ApptTypeInstitution relation : relations) {
            institutionIds.add(relation.getInstitutionId());
        }

        // 如果没有关联的机构，直接返回空列表
        if (institutionIds.isEmpty()) {
            return R.ok(List.of());
        }

        // 第二步：根据机构ID列表查询启用的机构详情
        List<Institution> institutions = institutionService.list(
                new LambdaQueryWrapper<Institution>()
                        .in(Institution::getId, institutionIds)
                        .eq(Institution::getStatus, 1)
                        .orderByAsc(Institution::getSortOrder));
        return R.ok(institutions);
    }

    /**
     * 根据机构ID获取该机构提供的上架套餐列表
     * <p>
     * 用户在预约流程第二步选择机构后，调用此接口获取该机构提供的套餐。
     * 仅返回上架状态（status=1）的套餐，按排序号升序排列。
     * </p>
     *
     * <p><b>权限：PATIENT</b></p>
     *
     * @param id 机构ID
     * @return 该机构提供的上架套餐列表
     */
    @RequireRole({"PATIENT"})
    @GetMapping("/institutions/{id}/packages")
    public R<List<ExamPackage>> getPackagesByInstitution(@PathVariable Long id) {
        // 第一步：查询机构-套餐关联表，获取该机构提供的套餐ID列表
        List<InstitutionPackage> relations = institutionPackageMapper.selectList(
                new LambdaQueryWrapper<InstitutionPackage>()
                        .eq(InstitutionPackage::getInstitutionId, id));

        List<Long> packageIds = new ArrayList<>();
        for (InstitutionPackage relation : relations) {
            packageIds.add(relation.getPackageId());
        }

        // 如果没有关联的套餐，直接返回空列表
        if (packageIds.isEmpty()) {
            return R.ok(List.of());
        }

        // 第二步：根据套餐ID列表查询上架的套餐详情
        List<ExamPackage> packages = examPackageService.list(
                new LambdaQueryWrapper<ExamPackage>()
                        .in(ExamPackage::getId, packageIds)
                        .eq(ExamPackage::getStatus, 1)
                        .orderByAsc(ExamPackage::getSortOrder));
        return R.ok(packages);
    }
}