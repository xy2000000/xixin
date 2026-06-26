package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.InstitutionPackage;
import com.fxy.xixin.exam.mapper.InstitutionPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构-套餐关联管理控制器
 * <p>
 * 提供机构与体检套餐关联关系的增删查接口，供管理员配置哪些机构提供哪些套餐。
 * </p>
 *
 * <p><b>权限：仅 ADMIN</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam/institution-packages")
@RequiredArgsConstructor
public class InstitutionPackageController {

    private final InstitutionPackageMapper institutionPackageMapper;

    /**
     * 查询某机构已关联的套餐列表
     *
     * @param institutionId 机构ID
     * @return 关联记录列表
     */
    @RequireRole({"ADMIN"})
    @GetMapping
    public R<List<InstitutionPackage>> list(@RequestParam Long institutionId) {
        List<InstitutionPackage> list = institutionPackageMapper.selectList(
                new LambdaQueryWrapper<InstitutionPackage>()
                        .eq(InstitutionPackage::getInstitutionId, institutionId));
        return R.ok(list);
    }

    /**
     * 新增机构-套餐关联
     *
     * @param institutionPackage 关联信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody InstitutionPackage institutionPackage) {
        institutionPackageMapper.insert(institutionPackage);
        return R.ok();
    }

    /**
     * 删除机构-套餐关联
     *
     * @param id 关联记录ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        institutionPackageMapper.deleteById(id);
        return R.ok();
    }
}
