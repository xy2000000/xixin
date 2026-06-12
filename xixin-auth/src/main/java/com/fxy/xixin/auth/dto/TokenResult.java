package com.fxy.xixin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * JWT令牌返回数据传输对象
 * <p>
 * 登录认证成功后返回给前端的令牌信息，
 * 包含访问令牌、刷新令牌以及过期时间。
 * 前端后续请求需携带 accessToken 进行身份验证。
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
}
