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
     * 令牌结果中包含当前用户的基本信息（含头像URL）。
     * </p>
     *
     * @param dto 登录请求参数
     * @return JWT令牌结果（含用户信息）
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

    /**
     * 更新用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像URL（来自文件服务的上传结果）
     */
    void updateAvatar(Long userId, String avatarUrl);

    /**
     * 获取当前登录用户信息
     * <p>
     * 用于前端页面刷新后，根据 JWT 令牌重新获取用户基本信息（含头像URL）。
     * 与登录返回的 userInfo 结构一致。
     * </p>
     *
     * @param userId 当前用户ID（从 JWT 中解析）
     * @return 用户简要信息（含头像URL）
     */
    TokenResult.UserInfo getCurrentUser(Long userId);
}