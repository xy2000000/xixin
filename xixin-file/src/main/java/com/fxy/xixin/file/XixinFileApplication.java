package com.fxy.xixin.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.fxy.xixin.common.feign")
public class XixinFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(XixinFileApplication.class, args);
    }
}