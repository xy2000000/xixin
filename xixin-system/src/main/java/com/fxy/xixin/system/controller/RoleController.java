package com.fxy.xixin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import com.fxy.xixin.system.entity.Role;
import com.fxy.xixin.system.service.RoleService;
import com.fxy.xixin.common.annotation.RequireRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理控制器
 * <p>
 * 提供系统角色的 CRUD 管理接口。
 * 角色用于定义用户在体检预约系统中的权限范围，
 * 如体检人、医生、管理员等不同角色访问不同的功能模块。
 * </p>
 *
 * <p><b>权限：所有接口仅限 ADMIN（管理员）访问</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/system/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询角色列表
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 角色分页数据
     */
    @RequireRole({"ADMIN"})
    @GetMapping
    public R<PageResult<Role>> list(PageDTO page) {
        IPage<Role> result = roleService.page(page.toPage());
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询角色详情
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 角色ID
     * @return 角色详细信息
     */
    @RequireRole({"ADMIN"})
    @GetMapping("/{id}")
    public R<Role> getById(@PathVariable Long id) {
        return R.ok(roleService.getById(id));
    }

    /**
     * 新增角色，医生
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param role 角色信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PostMapping
    public R<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }

    /**
     * 更新角色信息
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param role 待更新的角色信息
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping
    public R<Void> update(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

    /**
     * 删除指定角色（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 角色ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        roleService.removeById(id);
        return R.ok();
    }
}