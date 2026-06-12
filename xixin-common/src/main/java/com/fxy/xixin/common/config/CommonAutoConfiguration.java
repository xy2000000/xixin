package com.fxy.xixin.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 公共模块自动配置类
 * <p>
 * Spring Boot 自动配置入口，通过 {@code @ComponentScan} 扫描 com.fxy.xixin.common 包下的所有组件，
 * 包括全局异常处理器、统一响应、工具类等。其他业务模块（exam、report 等）引入 common 依赖后，
 * 会自动加载这些公共 Bean，无需手动配置。
 * 在体检预约系统中，所有微服务模块均依赖此公共模块，确保全局异常处理、统一响应格式等基础能力一致。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = "com.fxy.xixin.common")
public class CommonAutoConfiguration {
}
