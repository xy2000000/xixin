package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.Institution;
import com.fxy.xixin.exam.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

import java.util.List;

/**
 * 体检机构控制器
 * <p>
 * 提供体检机构的 RESTful API 接口，支持机构的分页查询、列表获取、详情查看和增删改操作。
 * 在体检预约流程中，用户选择套餐后通过此控制器查询可提供该套餐的机构列表，
 * 获取机构地址、电话、营业时间等详细信息，帮助用户选择合适的体检场所。
 * </p>
 *
 * <p><b>权限：患者可查看启用机构 / 管理员管理全部机构</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;

    /**
     * 分页查询机构列表（管理端，含已停用的机构）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 分页机构数据
     */
    @RequireRole({"ADMIN"})
    @GetMapping
    public R<PageResult<Institution>> list(PageDTO page) {
        IPage<Institution> result = institutionService.page(page.toPage(),
                new LambdaQueryWrapper<Institution>().orderByAsc(Institution::getSortOrder));
        return R.ok(PageResult.of(result));
    }

    /**
     * 获取所有启用状态的机构列表（患者端选择机构时使用）
     *
     * <p><b>权限：PATIENT</b></p>
     *
     * @return 启用的机构列表
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping("/active")
    public R<List<Institution>> activeList() {
        List<Institution> list = institutionService.list(
                new LambdaQueryWrapper<Institution>().eq(Institution::getStatus, 1).orderByAsc(Institution::getSortOrder));
        return R.ok(list);
    }

    /**
     * 根据ID查询机构详情
     *
     * <p><b>权限：PATIENT、ADMIN</b></p>
     *
     * @param id 机构ID
     * @return 机构详情
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping("/{id}")
    public R<Institution> getById(@PathVariable Long id) {
        return R.ok(institutionService.getById(id));
    }

    /**
     * 新增体检机构
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param institution 机构信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody Institution institution) {
        institutionService.save(institution);
        return R.ok();
    }

    /**
     * 更新机构信息
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param institution 更新的机构内容
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping
    public R<Void> update(@RequestBody Institution institution) {
        institutionService.updateById(institution);
        return R.ok();
    }

    /**
     * 删除机构（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 机构ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        institutionService.removeById(id);
        return R.ok();
    }
}