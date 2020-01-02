package com.wechat.article;

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
@MapperScan(basePackages="com.wechat.article.mapper")
public class ArticleApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
	}
}
