package com.fxy.xixin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.xixin.system.entity.Role;

/**
 * 角色服务接口
 * <p>
 * 继承 MyBatis-Plus IService，提供角色管理的业务接口。
 * 角色定义是体检预约系统权限控制的基础，
 * 不同角色拥有不同的系统访问权限。
 * </p>
 *
 * @author dev
 */
public interface RoleService extends IService<Role> {
}
