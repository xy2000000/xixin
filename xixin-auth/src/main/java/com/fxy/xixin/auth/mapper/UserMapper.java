package com.fxy.xixin.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.xixin.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问层接口
 * <p>
 * 继承 MyBatis-Plus BaseMapper，提供用户表的 CRUD 基础操作。
 * 用于认证服务中的登录验证、注册、用户管理等场景。
 * </p>
 *
 * @author dev
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
