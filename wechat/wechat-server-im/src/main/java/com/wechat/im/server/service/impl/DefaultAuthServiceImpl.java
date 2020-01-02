package com.wechat.im.server.service.impl;

import java.net.URI;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.wechat.base.common.code.Code;
import com.wechat.base.model.im.ImMessageModel;
import com.wechat.im.server.common.model.ImProperties;
import com.wechat.im.server.im_server.Server;
import com.wechat.im.server.service.AuthService;
/**
 * 默认的认证
 * @author chrilwe
 *
 */
public class DefaultAuthServiceImpl implements AuthService {

	 
	public boolean auth(ImMessageModel model) {
		//获取配置类
		ImProperties properties = (ImProperties) Server.singletonMap.get("imProperties");
		RestTemplate restTemplate = new RestTemplate();
		
		//随机获取一个认证地址
		String authServiceUrls = properties.getAuthServiceUrls();
		if(StringUtils.isEmpty(authServiceUrls)) {
			return false;
		}
		String[] split = authServiceUrls.split(",");
		Random r = new Random();
		int nextInt = r.nextInt(split.length);
		String authUrl = split[nextInt];
		
		ResponseEntity<Boolean> res = restTemplate.getForEntity(authUrl, Boolean.class);
		int code = res.getStatusCodeValue();
		if(Code.SUCCESS != code) {
			return false;
		}
		Boolean body = res.getBody();
		if(body == true) {
			return true;
		}
		return true;
	}

}
