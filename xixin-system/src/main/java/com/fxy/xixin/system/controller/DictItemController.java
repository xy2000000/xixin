package com.fxy.xixin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.system.entity.DictItem;
import com.fxy.xixin.common.annotation.RequireRole;
import com.fxy.xixin.system.mapper.DictItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 字典项管理控制器
 * <p>
 * 提供数据字典项（具体枚举值）的 CRUD 管理接口。
 * 在体检预约系统中，字典项为前端提供具体的下拉选项数据，
 * 如性别选项（男/女）、预约状态（待确认/已确认/已取消/已完成）。
 * 支持按字典类型ID筛选查询对应的字典项列表。
 * </p>
 *
 * <p><b>权限：所有接口仅限 ADMIN（管理员）访问（预约类型查询除外，患者可通过 FlowController 间接使用）</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/system/dict-items")
@RequiredArgsConstructor
public class DictItemController {

    private final DictItemMapper dictItemMapper;

    /**
     * 根据字典类型ID分页查询字典项列表
     *
     * <p><b>权限：ADMIN、PATIENT（患者端选择预约类型时使用）</b></p>
     *
     * @param dictTypeId 字典类型ID
     * @param page       分页参数
     * @return 该字典类型下的字典项分页数据
     */
    @RequireRole({"PATIENT", "ADMIN"})
    @GetMapping("/by-type/{dictTypeId}")
    public R<PageResult<DictItem>> list(@PathVariable Long dictTypeId, PageDTO page) {
        IPage<DictItem> result = dictItemMapper.selectPage(page.toPage(),
                new LambdaQueryWrapper<DictItem>().eq(DictItem::getDictTypeId, dictTypeId));
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询字典项详情
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 字典项ID
     * @return 字典项详细信息
     */
    @RequireRole({"ADMIN"})
    @GetMapping("/{id}")
    public R<DictItem> getById(@PathVariable Long id) {
        return R.ok(dictItemMapper.selectById(id));
    }

    /**
     * 新增字典项
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param dictItem 字典项信息（含所属字典类型ID、标签、值、排序等）
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody DictItem dictItem) {
        dictItemMapper.insert(dictItem);
        return R.ok();
    }

    /**
     * 更新字典项信息
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param dictItem 待更新的字典项信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping
    public R<Void> update(@RequestBody DictItem dictItem) {
        dictItemMapper.updateById(dictItem);
        return R.ok();
    }

    /**
     * 删除指定字典项（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 字典项ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        dictItemMapper.deleteById(id);
        return R.ok();
    }
}