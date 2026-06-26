package com.fxy.xixin.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fxy.xixin.auth.entity.User;
import com.fxy.xixin.auth.mapper.UserMapper;
import com.fxy.xixin.common.dto.PageDTO;
import com.fxy.xixin.common.result.PageResult;
import com.fxy.xixin.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.fxy.xixin.common.annotation.RequireRole;

/**
 * 用户管理控制器
 * <p>
 * 提供系统用户的 CRUD 管理接口，包括用户列表查询、
 * 用户详情查看、用户信息编辑、账号状态切换、
 * 密码重置和用户删除等功能。
 * </p>
 *
 * <p><b>权限：所有接口仅限 ADMIN（管理员）访问</b></p>
 *
 * @author dev
 */
@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    /**
     * 分页查询用户列表
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param page 分页参数
     * @return 用户分页数据
     */
    @RequireRole({"ADMIN"})
    @GetMapping
    public R<PageResult<User>> list(PageDTO page, @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(User::getUsername, keyword)
                   .or()
                   .like(User::getRealName, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> result = userMapper.selectPage(page.toPage(), wrapper);
        return R.ok(PageResult.of(result));
    }

    /**
     * 根据ID查询单个用户详情
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @RequireRole({"ADMIN"})
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        return R.ok(userMapper.selectById(id));
    }

    /**
     * 更新用户基本信息
     * <p>
     * 仅更新前端传递的非空字段，未传递的字段保持原值不变。
     * 支持更新：真实姓名、手机号、邮箱、角色、状态。
     * 不涉及密码修改（密码通过独立的重置密码接口操作）。
     * </p>
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id  用户ID
     * @param dto 待更新的用户信息（仅包含需要修改的字段）
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody User dto) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, id);

        // 逐个检查字段是否有值，有值才更新（避免把未传的字段覆盖为 null）
        if (StringUtils.hasText(dto.getRealName())) {
            wrapper.set(User::getRealName, dto.getRealName());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            wrapper.set(User::getPhone, dto.getPhone());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            wrapper.set(User::getEmail, dto.getEmail());
        }
        if (StringUtils.hasText(dto.getRole())) {
            wrapper.set(User::getRole, dto.getRole());
        }
        if (dto.getStatus() != null) {
            wrapper.set(User::getStatus, dto.getStatus());
        }

        userMapper.update(wrapper);
        return R.ok();
    }

    /**
     * 切换用户账号启用/禁用状态
     * <p>
     * 管理员可通过此接口启用（status=1）或禁用（status=0）用户账号。
     * 禁用后用户无法登录系统。
     * </p>
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id     用户ID
     * @param status 目标状态：0=禁用，1=启用
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping("/{id}/status")
    public R<Void> toggleStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return R.ok();
        }

        user.setStatus(status);
        userMapper.updateById(user);
        return R.ok();
    }

    /**
     * 重置用户密码
     * <p>
     * 管理员为指定用户设置新密码，密码明文存储。
     * </p>
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id          用户ID
     * @param newPassword 新密码（明文存储）
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @PutMapping("/{id}/reset-password")
    public R<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return R.ok();
        }

        // 明文存储新密码
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return R.ok();
    }

    /**
     * 删除指定用户（逻辑删除）
     *
     * <p><b>权限：ADMIN</b></p>
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return R.ok();
    }
}