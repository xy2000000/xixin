package com.fxy.xixin.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 东软熙心健康体检管理系统 - 系统管理服务启动类
 * <p>
 * 该模块负责系统基础数据的管理，包括角色管理和数据字典。
 * 角色管理为系统的权限控制提供角色定义；
 * 数据字典为体检预约系统提供统一的枚举值管理
 * （如体检项目类型、科室类型、预约状态等）。
 * </p>
 *
 * @author dev
 */
@SpringBootApplication
@EnableDiscoveryClient
public class XixinSystemApplication {

    /**
     * 系统管理服务启动入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(XixinSystemApplication.class, args);
    }
}
