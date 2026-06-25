package com.fxy.xixin.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.exam.entity.ExamItem;
import com.fxy.xixin.exam.entity.ExamPackage;
import com.fxy.xixin.exam.service.ExamItemService;
import com.fxy.xixin.exam.service.ExamPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检套餐控制器
 * <p>
 * 提供体检套餐的 RESTful API 接口，支持套餐的分页查询、详情查看、增删改和上下架操作。
 * 在体检预约流程中，用户进入系统首页后首先浏览套餐列表，选择感兴趣的套餐查看详情。
 * </p>
 *
 * <p><b>权限：查询接口供 PATIENT、ADMIN / 管理接口仅 ADMIN</b></p>
 *
 * @author dev
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/exam/packages")
@RequiredArgsConstructor
public class ExamPackageController {

    private final ExamPackageService examPackageService;
    private final ExamItemService examItemService;

    /**
     * 分页查询套餐列表
     *
     * <p><b>权限：PATIENT、ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 分页套餐数据
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping
    public R<PageResult<ExamPackage>> list(PageDTO page) {
        IPage<ExamPackage> result = examPackageService.page(page.toPage());
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询套餐详情（含检查项目列表）
     *
     * <p><b>权限：PATIENT、DOCTOR、ADMIN</b></p>
     *
     * @param id 套餐ID
     * @return 套餐详情（含该套餐下的全部检查项目）
     */
    @RequireRole({"PATIENT", "DOCTOR", "ADMIN"})
    @GetMapping("/{id}")
    public R<ExamPackage> getById(@PathVariable Long id) {
        ExamPackage examPackage = examPackageService.getById(id);
        if (examPackage != null) {
            List<ExamItem> items = examItemService.list(
                    new LambdaQueryWrapper<ExamItem>()
                            .eq(ExamItem::getPackageId, id)
                            .orderByAsc(ExamItem::getSortOrder));
            examPackage.setItems(items);
        }
        return R.ok(examPackage);
    }

    /**
     * 新增体检套餐
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param examPackage 套餐信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody ExamPackage examPackage) {
        examPackageService.save(examPackage);
        return R.ok();
    }

    /**
     * 更新套餐信息
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param examPackage 更新的套餐内容
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping
    public R<Void> update(@RequestBody ExamPackage examPackage) {
        examPackageService.updateById(examPackage);
        return R.ok();
    }

    /**
     * 删除套餐（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 套餐ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        examPackageService.removeById(id);
        return R.ok();
    }

    /**
     * 切换套餐上下架状态
     * <p>
     * 上架（status=1）的套餐在前端可见，用户可以浏览和选择；
     * 下架（status=0）的套餐在前端隐藏，但已有预约不受影响。
     * </p>
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id     套餐ID
     * @param status 目标状态：0=下架，1=上架
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping("/{id}/status")
    public R<Void> toggleStatus(@PathVariable Long id, @RequestParam Integer status) {
        ExamPackage examPackage = examPackageService.getById(id);
        if (examPackage == null) {
            return R.ok();
        }

        examPackage.setStatus(status);
        examPackageService.updateById(examPackage);
        return R.ok();
    }
}