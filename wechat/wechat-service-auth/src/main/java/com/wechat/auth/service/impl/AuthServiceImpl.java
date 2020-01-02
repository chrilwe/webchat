package com.wechat.auth.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wechat.auth.common.code.AuthCode;
import com.wechat.auth.common.model.OauthProperties;
import com.wechat.auth.common.msg.AuthMsg;
import com.wechat.auth.service.AuthService;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.LoginResult;
import com.wechat.base.common.servicesname.WechatServices;
import com.wechat.base.model.auth.AuthToken;
/**
 * 
 * @author chrilwe
 *
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private OauthProperties oauthProperties;
	
	public AuthToken login(String username, String password) {
		//check username and password, if pass, generate jwt token
		AuthToken authToken = this.applayToken(username, password);
		
		//put jwt token into redis cache
		this.saveAuthTokenInRedisCache(authToken);
		
		return authToken;
	}
	
	private AuthToken applayToken(String username, String password) {
		// create applay token url
		ServiceInstance serviceInstance = loadBalancerClient.choose(WechatServices.WECHAT_SERVICE_AUTH);
		URI uri = serviceInstance.getUri();
		String applayUrl = uri + oauthProperties.getApplay_token_uri();
		
		//config http header params and request body params
		String GRANT_TYPE = "grant_type";
		String USER_NAME = "username";
		String PASSWORD = "password";
		String AUTHORIZATION = "Authorization";
		
		LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		
		header.add(AUTHORIZATION, "Basic " + new String(Base64Utils.encode((oauthProperties.getClient_id() + ":" + oauthProperties.getClient_secret()).getBytes())));
		body.add(USER_NAME, username);
		body.add(PASSWORD, password);
		
		//request token url get jwt token
		AuthToken authToken = this.postRequest(header, body, applayUrl);
		
		return authToken;
	}
	
	private AuthToken postRequest(LinkedMultiValueMap<String, String> header, LinkedMultiValueMap<String, String> body, String url) {
		//if response status code is 400 or 401, let it response msg normally
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public void handleError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				int value = statusCode.value();
				if(value != 400 || value != 401) {
					super.handleError(response);
				}
			}
		});
		HttpEntity httpEntity = new HttpEntity(body,header);
		ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
		
		//handle request response result
		int code = result.getStatusCodeValue();
		if(code == 401) {
			throw new RuntimeException(AuthMsg.UNAUTHRIZATION);
		}
		if(code == 400) {
			throw new RuntimeException(AuthMsg.ERR_TOKEN);
		}
		if(code == 200) {
			Map map = result.getBody();
			String accessToken = (String) map.get("accessToken");
			String jwt = (String) map.get("jwt");
			String refreshToken = (String) map.get("refreshToken");
			
			AuthToken authToken = new AuthToken();
			authToken.setAccessToken(accessToken);
			authToken.setJwtToken(jwt);
			authToken.setRefreshToken(refreshToken);
			return authToken;
		}
		return null;
	}
	
	private void saveAuthTokenInRedisCache(AuthToken authToken) {
		if(authToken == null) {
			throw new RuntimeException(Msg.SYSTEM_ERR);
		}
		stringRedisTemplate.opsForValue().set(authToken.getAccessToken(), JSON.toJSONString(authToken), oauthProperties.getToken_expire_time(), TimeUnit.SECONDS);
	}

	
	public boolean login(String accessToken) {
		String string = stringRedisTemplate.opsForValue().get(accessToken);
		if(!StringUtils.isEmpty(string)) {
			return true;
		}
		return false;
	}
}
