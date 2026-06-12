package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.ExamItem;
import com.fxy.xixin.exam.service.ExamItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

/**
 * 体检项目控制器
 * <p>
 * 提供体检项目的 RESTful API 接口，项目管理嵌套在套餐路径下。
 * 在体检预约流程中，用户查看套餐详情时，通过此控制器获取该套餐包含的所有检查项目。
 * </p>
 *
 * <p><b>权限：查询接口供 PATIENT、ADMIN / 写入接口仅 ADMIN</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam/packages/{packageId}/items")
@RequiredArgsConstructor
public class ExamItemController {

    private final ExamItemService examItemService;

    /**
     * 分页查询指定套餐下的体检项目列表
     *
     * <p><b>权限：PATIENT、ADMIN</b></p>
     *
     * @param packageId 套餐ID
     * @param page      分页参数
     * @return 分页项目数据
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping
    public R<PageResult<ExamItem>> list(@PathVariable Long packageId, PageDTO page) {
        IPage<ExamItem> result = examItemService.page(page.toPage(),
                new LambdaQueryWrapper<ExamItem>().eq(ExamItem::getPackageId, packageId));
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询单个体检项目详情
     *
     * <p><b>权限：PATIENT、ADMIN</b></p>
     *
     * @param id 项目ID
     * @return 项目详情
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping("/{id}")
    public R<ExamItem> getById(@PathVariable Long id) {
        return R.ok(examItemService.getById(id));
    }

    /**
     * 在指定套餐下新增体检项目
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param packageId 套餐ID
     * @param examItem  项目信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@PathVariable Long packageId, @RequestBody ExamItem examItem) {
        examItem.setPackageId(packageId);
        examItemService.save(examItem);
        return R.ok();
    }

    /**
     * 更新体检项目
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param packageId 套餐ID
     * @param id        项目ID
     * @param examItem  更新的项目内容
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long packageId, @PathVariable Long id, @RequestBody ExamItem examItem) {
        examItem.setId(id);
        examItem.setPackageId(packageId);
        examItemService.updateById(examItem);
        return R.ok();
    }

    /**
     * 删除体检项目（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 项目ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        examItemService.removeById(id);
        return R.ok();
    }
}