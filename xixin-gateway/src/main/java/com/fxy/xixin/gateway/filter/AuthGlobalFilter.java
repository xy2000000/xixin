package com.fxy.xixin.gateway.filter;

import com.fxy.xixin.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 全局认证过滤器
 * <p>
 * Spring Cloud Gateway 全局过滤器，对所有进入网关的请求进行 JWT Token 认证校验。
 * 处理流程：
 * <ol>
 *   <li>检查请求路径是否在白名单中（登录、注册、刷新 Token），白名单路径直接放行</li>
 *   <li>从 Authorization 请求头中提取 Bearer Token</li>
 *   <li>校验 Token 是否过期</li>
 *   <li>解析 Token 获取用户信息（ID、用户名、角色）</li>
 *   <li>将用户信息通过请求头（X-User-Id、X-Username、X-User-Role）传递给下游微服务</li>
 * </ol>
 * 任何一步校验失败均返回 401 未认证状态码。
 * 在体检预约流程中，用户必须先登录获取 Token，后续所有操作（查询套餐、提交预约、查看报告）
 * 都需携带 Token 通过此过滤器的校验。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 认证白名单路径，无需 Token 即可访问
     */
    private static final List<String> WHITE_LIST = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh"
    );

    /**
     * Ant 风格路径匹配器，用于白名单路径匹配
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 过滤器核心逻辑
     * <p>
     * 对每个进入网关的请求执行以下校验步骤：
     * <ol>
     *   <li>白名单路径（登录/注册/刷新令牌）直接放行</li>
     *   <li>检查请求是否携带 Authorization: Bearer xxx 头，没有则返回 401</li>
     *   <li>校验 Token 是否过期，过期则返回 401</li>
     *   <li>解析 Token 获取用户信息，注入到请求头中传给下游微服务</li>
     * </ol>
     * </p>
     *
     * @param exchange 请求-响应交互上下文
     * @param chain    过滤器链
     * @return Mono 信号，表示过滤完成
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 步骤1：白名单路径直接放行（登录、注册、刷新Token）
        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        // 步骤2：检查 Authorization 请求头是否存在且格式正确
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("请求缺少Authorization头或格式不正确: path={}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 步骤3：提取 Token 并检查是否过期
        // Bearer 后面有一个空格，所以从第7个字符开始截取
        String token = authHeader.substring(7);
        if (JwtUtils.isTokenExpired(token)) {
            log.warn("Token已过期: path={}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 步骤4：解析 Token，将用户信息注入请求头后转发给下游微服务
        try {
            Claims claims = JwtUtils.parseToken(token);

            // 从 Token 中提取用户ID、用户名、角色
            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            // 将这些信息注入到请求头中，下游微服务可以直接读取
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .header("X-Username", username)
                    .header("X-User-Role", role)
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        } catch (Exception e) {
            log.warn("Token解析失败: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 判断请求路径是否在白名单中
     *
     * @param path 请求路径
     * @return true 表示白名单路径，无需认证
     */
    private boolean isWhiteListed(String path) {
        return WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    /**
     * 设置过滤器执行顺序，值越小优先级越高
     *
     * @return 顺序值（-100 确保在其他过滤器之前执行）
     */
    @Override
    public int getOrder() {
        return -100;
    }
}
