package com.fxy.xixin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 熙心健康体检管理系统 - 网关服务启动类
 * <p>
 * 基于 Spring Cloud Gateway 的 API 网关，作为系统的统一入口。
 * 主要职责：
 * <ul>
 *   <li>请求路由转发：将前端请求根据路径转发到对应的后端微服务（exam、report、auth 等）</li>
 *   <li>统一认证鉴权：通过 {@link com.fxy.xixin.gateway.filter.AuthGlobalFilter} 对所有请求进行 Token 校验</li>
 *   <li>跨域处理：通过 {@link com.fxy.xixin.gateway.config.CorsConfig} 解决前后端分离的跨域问题</li>
 *   <li>服务发现：通过 Nacos 注册中心动态发现后端服务实例</li>
 * </ul>
 * 在体检预约流程中，所有前端请求（套餐查询、预约提交、报告查看等）均先经过网关层认证，
 * 再路由到对应的业务微服务处理。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class XixinGatewayApplication {

    /**
     * 应用主入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(XixinGatewayApplication.class, args);
    }
}
