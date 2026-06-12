package com.fxy.xixin.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 跨域（CORS）配置类
 * <p>
 * 针对 Spring Cloud Gateway（WebFlux 响应式架构）配置跨域访问策略，
 * 允许前端应用（运行在不同域名或端口下）通过 AJAX 请求访问网关 API。
 * 配置项包括：
 * <ul>
 *   <li>允许所有来源域名</li>
 *   <li>允许所有 HTTP 方法（GET、POST、PUT、DELETE 等）</li>
 *   <li>允许所有请求头</li>
 *   <li>允许携带 Cookie 凭证</li>
 *   <li>预检请求缓存1小时</li>
 * </ul>
 * 在体检预约系统中，前端（Vue/React）通过此配置可以无阻碍地调用后端接口。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Configuration
public class CorsConfig {

    /**
     * 注册跨域过滤器 Bean
     *
     * @return CorsWebFilter 实例，应用于所有路径
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
