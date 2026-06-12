package com.fxy.xixin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.system.entity.DictType;
import com.fxy.xixin.system.service.DictTypeService;
import com.fxy.xixin.common.annotation.RequireRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型管理控制器
 * <p>
 * 提供数据字典类型的 CRUD 管理接口。
 * 字典类型用于对系统中的枚举数据进行分类管理，
 * 如体检项目类别、预约状态、科室类型等。
 * 体检预约系统中前端的下拉框和选项数据均依赖此模块。
 * </p>
 *
 * <p><b>权限：所有接口仅限 ADMIN（管理员）访问</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/system/dict-types")
@RequiredArgsConstructor
public class DictTypeController {

    private final DictTypeService dictTypeService;

    /**
     * 分页查询字典类型列表
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 字典类型分页数据
     */
    @RequireRole({"ADMIN"})
    @GetMapping
    public R<PageResult<DictType>> list(PageDTO page) {
        IPage<DictType> result = dictTypeService.page(page.toPage());
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询字典类型详情
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 字典类型ID
     * @return 字典类型详细信息
     */
    @RequireRole({"ADMIN"})
    @GetMapping("/{id}")
    public R<DictType> getById(@PathVariable Long id) {
        return R.ok(dictTypeService.getById(id));
    }

    /**
     * 新增字典类型
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param dictType 字典类型信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody DictType dictType) {
        dictTypeService.save(dictType);
        return R.ok();
    }

    /**
     * 更新字典类型信息
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param dictType 待更新的字典类型信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping
    public R<Void> update(@RequestBody DictType dictType) {
        dictTypeService.updateById(dictType);
        return R.ok();
    }

    /**
     * 删除指定字典类型（逻辑删除，其下的字典项应同步处理）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 字典类型ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        dictTypeService.removeById(id);
        return R.ok();
    }
}