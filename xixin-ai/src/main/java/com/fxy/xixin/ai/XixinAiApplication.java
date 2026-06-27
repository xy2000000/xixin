package com.fxy.xixin.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.fxy.xixin.ai", "com.fxy.xixin.common"})
@EnableDiscoveryClient
public class XixinAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XixinAiApplication.class, args);
    }
}