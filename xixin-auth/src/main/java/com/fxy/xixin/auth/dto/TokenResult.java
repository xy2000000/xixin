package com.fxy.xixin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * JWT令牌返回数据传输对象
 * <p>
 * 登录认证成功后返回给前端的令牌信息，
 * 包含访问令牌、刷新令牌、过期时间以及当前用户的基本信息（含头像）。
 * 前端后续请求需携带 accessToken 进行身份验证，
 * 页面刷新后可通过 GET /api/auth/me 接口重新获取用户信息。
 * </p>
 *
 * @author dev
 */
@Data
@AllArgsConstructor
public class TokenResult {

    /** JWT访问令牌，用于后续接口请求的身份认证 */
    private String accessToken;

    /** JWT刷新令牌，用于在访问令牌过期后获取新令牌 */
    private String refreshToken;

    /** 令牌过期时间（秒） */
    private Long expiresIn;

    /** 当前登录用户的简要信息 */
    private UserInfo userInfo;

    /**
     * 登录响应中携带的用户简要信息
     */
    @Data
    @AllArgsConstructor
    public static class UserInfo {
        /** 用户ID */
        private Long userId;
        /** 用户名 */
        private String username;
        /** 真实姓名 */
        private String realName;
        /** 头像URL */
        private String avatar;
        /** 角色 */
        private String role;
    }
}