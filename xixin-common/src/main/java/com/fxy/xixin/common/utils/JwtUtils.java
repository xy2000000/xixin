package com.fxy.xixin.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT（JSON Web Token）工具类
 * <p>
 * 提供 Token 的生成、解析和校验功能。
 * 支持两种 Token：
 * <ul>
 *   <li>Access Token：访问令牌，有效期24小时，包含用户ID、用户名、角色信息</li>
 *   <li>Refresh Token：刷新令牌，有效期7天，用于在 Access Token 过期后获取新令牌</li>
 * </ul>
 * 在网关层的 {@code AuthGlobalFilter} 中使用，对请求进行身份认证。
 * 体检预约系统的登录流程中，用户登录成功后由认证模块调用此工具生成 Token。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Slf4j
public class JwtUtils {

    /**
     * 签名密钥，HS256 算法要求至少 256 位
     */
    private static final String SECRET = "XiXin-Health-Exam-2024-Secret-Key-Must-Be-At-Least-256-Bits!";
    /**
     * Access Token 过期时间：24小时
     */
    private static final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * Refresh Token 过期时间：7天
     */
    private static final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24 * 7;

    /**
     * 获取 HMAC-SHA 签名密钥
     *
     * @return SecretKey 实例
     */
    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成访问令牌（Access Token）
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param role     用户角色
     * @return JWT 访问令牌字符串
     */
    public static String generateAccessToken(Long userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", role);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
                .signWith(getKey())
                .compact();
    }

    /**
     * 生成刷新令牌（Refresh Token）
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return JWT 刷新令牌字符串
     */
    public static String generateRefreshToken(Long userId, String username) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(getKey())
                .compact();
    }

    /**
     * 解析 Token 并返回 Claims
     *
     * @param token JWT 令牌字符串
     * @return 解析后的 Claims 对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 判断 Token 是否已过期
     *
     * @param token JWT 令牌字符串
     * @return true 表示已过期或无效
     */
    public static boolean isTokenExpired(String token) {
        try {
            parseToken(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.warn("Token解析失败: {}", e.getMessage());
            return true;
        }
    }

    /**
     * 从 Token 中提取用户ID
     *
     * @param token JWT 令牌字符串
     * @return 用户ID，解析失败返回 null
     */
    public static Long getUserId(String token) {
        try {
            return parseToken(token).get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从 Token 中提取用户名
     *
     * @param token JWT 令牌字符串
     * @return 用户名，解析失败返回 null
     */
    public static String getUsername(String token) {
        try {
            return parseToken(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从 Token 中提取用户角色
     *
     * @param token JWT 令牌字符串
     * @return 角色名称，解析失败返回 null
     */
    public static String getRole(String token) {
        try {
            return parseToken(token).get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
