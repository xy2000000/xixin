package com.fxy.xixin.common.context;

/**
 * 用户上下文工具类
 * <p>
 * 基于 ThreadLocal 保存当前请求的用户信息（用户ID、用户名、角色）。
 * 由 {@link com.fxy.xixin.common.filter.UserContextFilter} 在请求进入时从网关注入的
 * X-User-Id、X-Username、X-User-Role 请求头中提取并设置，
 * 在请求结束时自动清理，防止线程池复用导致的内存泄漏。
 * </p>
 *
 * <p>使用方式：</p>
 * <pre>
 *   Long userId = UserContext.getUserId();
 *   String role = UserContext.getRole();
 * </pre>
 *
 * <p>在体检预约系统中，下游微服务通过此类获取当前操作用户的身份信息，
 * 用于数据隔离（患者只能看自己的预约）和权限校验（管理员才能管理用户）。</p>
 *
 * @author dev
 * @since 1.0.0
 */
public class UserContext {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<String> ROLE = new ThreadLocal<>();

    /**
     * 设置当前请求的用户上下文信息
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param role     用户角色（PATIENT / DOCTOR / ADMIN）
     */
    public static void set(Long userId, String username, String role) {
        USER_ID.set(userId);
        USERNAME.set(username);
        ROLE.set(role);
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID，未登录时返回 null
     */
    public static Long getUserId() {
        return USER_ID.get();
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名，未登录时返回 null
     */
    public static String getUsername() {
        return USERNAME.get();
    }

    /**
     * 获取当前用户角色
     *
     * @return 角色名称（PATIENT / DOCTOR / ADMIN），未登录时返回 null
     */
    public static String getRole() {
        return ROLE.get();
    }

    /**
     * 清理当前线程的用户上下文
     * <p>
     * 必须在请求结束时调用，防止在线程池环境下信息泄露到其他请求。
     * </p>
     */
    public static void clear() {
        USER_ID.remove();
        USERNAME.remove();
        ROLE.remove();
    }
}
