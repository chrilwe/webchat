package com.wechat.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.wechat.auth.common.model.OauthProperties;

@Configuration
public class AppConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	/**
	 * 配置properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="oauth")
	public OauthProperties oauthProperties() {
		return new OauthProperties();
	}
}
