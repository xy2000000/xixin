package com.fxy.xixin.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 熙心健康体检管理系统 - 体检服务启动类
 * <p>
 * 体检业务核心微服务，负责体检套餐管理、体检项目管理、预约管理和体检机构管理等核心功能。
 * 在体检预约流程中的定位：
 * <ul>
 *   <li>套餐查询：用户浏览可预约的体检套餐及包含的检查项目</li>
 *   <li>机构查询：用户选择体检机构，查看机构提供的套餐</li>
 *   <li>预约管理：用户提交预约、查询预约状态、取消预约</li>
 *   <li>预约锁定：通过 {@link com.fxy.xixin.exam.controller.AppointmentFlowController} 在支付前锁定号源</li>
 * </ul>
 * 该服务通过 Nacos 注册中心向网关暴露服务，是系统用户端的主要交互入口。
 * </p>
 *
 * @author dev
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class XixinExamApplication {

    /**
     * 应用主入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(XixinExamApplication.class, args);
    }
}
