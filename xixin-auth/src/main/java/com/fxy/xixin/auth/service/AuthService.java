package com.fxy.xixin.auth.service;

import com.fxy.xixin.auth.dto.LoginDTO;
import com.fxy.xixin.auth.dto.RegisterDTO;
import com.fxy.xixin.auth.dto.TokenResult;

/**
 * 认证服务接口
 * <p>
 * 定义用户登录认证和账号注册的核心业务逻辑。
 * 是体检预约系统安全体系的基础服务。
 * </p>
 *
 * @author dev
 */
public interface AuthService {

    /**
     * 用户登录认证
     * <p>
     * 校验用户名和密码，验证账号状态，生成并返回JWT令牌。
     * </p>
     *
     * @param dto 登录请求参数
     * @return JWT令牌结果
     */
    TokenResult login(LoginDTO dto);

    /**
     * 用户注册
     * <p>
     * 校验用户名唯一性，创建新用户账号并设置初始状态。
     * </p>
     *
     * @param dto 注册请求参数
     */
    void register(RegisterDTO dto);
}
