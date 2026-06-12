package com.fxy.xixin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供角色表的 CRUD 基础操作。
 * 用于系统管理中角色定义的管理和维护。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
