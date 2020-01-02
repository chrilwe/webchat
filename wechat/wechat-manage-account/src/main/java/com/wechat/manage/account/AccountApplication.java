package com.wechat.manage.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * @author chrilwe
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages="com.wechat.manage.account.mapper")
public class AccountApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}
