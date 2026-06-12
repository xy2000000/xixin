package com.fxy.xixin.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 东软熙心健康体检管理系统 - 用户服务启动类
 * <p>
 * 该模块负责体检人（Patient）和医生（Doctor）信息的管理，
 * 是体检预约系统中用户档案数据的核心服务。
 * 体检人管理包括基本信息、紧急联系人等档案维护；
 * 医生管理包括科室、职称、专长等信息的查询。
 * </p>
 *
 * @author dev
 */
@SpringBootApplication
@EnableDiscoveryClient
public class XixinUserApplication {

    /**
     * 用户服务启动入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(XixinUserApplication.class, args);
    }
}
