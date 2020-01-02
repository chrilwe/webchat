package com.wechat.filesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 
 * @author chrilwe
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages="com.wechat.filesystem.mapper")
@EnableFeignClients(basePackages="com.wechat.filesystem.client")
public class FilesystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilesystemApplication.class, args);
	}
}
