package com.fxy.xixin.common.filter;

import com.fxy.xixin.common.context.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用户上下文过滤器
 * <p>
 * 在每个 HTTP 请求进入时，从网关注入的请求头（X-User-Id、X-Username、X-User-Role）
 * 中提取当前用户信息，保存到 {@link UserContext} 的 ThreadLocal 中。
 * 请求完成后自动清理 ThreadLocal，防止内存泄漏。
 * </p>
 *
 * <p>网关白名单路径（登录、注册等）不会注入这些请求头，
 * 此时 UserContext 不会被设置，后续权限校验组件会将此类请求视为未登录。</p>
 *
 * <p>体检预约系统中，所有微服务模块均依赖此过滤器，
 * 确保在控制器方法执行前已准备好用户上下文。</p>
 *
 * @author dev
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String userIdHeader = httpRequest.getHeader("X-User-Id");
        String usernameHeader = httpRequest.getHeader("X-Username");
        String roleHeader = httpRequest.getHeader("X-User-Role");

        if (userIdHeader != null && !userIdHeader.isBlank()) {
            try {
                Long userId = Long.valueOf(userIdHeader);
                UserContext.set(userId, usernameHeader, roleHeader);
            } catch (NumberFormatException e) {
                log.warn("X-User-Id 格式不正确: {}", userIdHeader);
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
