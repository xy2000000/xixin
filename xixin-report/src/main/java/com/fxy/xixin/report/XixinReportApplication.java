package com.fxy.xixin.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.fxy.xixin.common.feign")
public class XixinReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(XixinReportApplication.class, args);
    }
}
