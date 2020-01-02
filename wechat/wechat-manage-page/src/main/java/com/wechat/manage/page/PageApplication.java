package com.wechat.manage.page;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages="com.wechat.manage.page.mapper")
public class PageApplication {
	public static void main(String[] args) {
		SpringApplication.run(PageApplication.class, args);
	}
}
