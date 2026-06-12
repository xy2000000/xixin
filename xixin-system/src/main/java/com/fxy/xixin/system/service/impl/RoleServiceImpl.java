package com.fxy.xixin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.xixin.system.entity.Role;
import com.fxy.xixin.system.mapper.RoleMapper;
import com.fxy.xixin.system.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现类
 * <p>
 * 继承 MyBatis-Plus ServiceImpl，提供角色管理的默认实现。
 * 体检预约系统中通过角色控制用户权限，
 * 如体检人可预约体检、医生可查看排班、管理员可管理系统配置。
 * </p>
 *
 * @author dev
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
