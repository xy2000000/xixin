package com.fxy.xixin.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 东软熙心健康体检管理系统 - 认证授权服务启动类
 * <p>
 * 该模块负责用户登录认证、注册、Token签发与验证，
 * 是体检预约系统的安全入口，所有前端请求经网关路由后
 * 由本模块校验身份合法性。
 * </p>
 *
 * @author dev
 */
@SpringBootApplication
@EnableDiscoveryClient
public class XixinAuthApplication {

    /**
     * 认证服务启动入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(XixinAuthApplication.class, args);
    }
}

