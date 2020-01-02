package com.wechat.base.api;

import com.wechat.base.common.response.LoginResult;

/**
 * 认证中心
 * @author chrilwe
 *
 */
public interface WechatAuthApi {
	public LoginResult login(String username, String password);
	
	public boolean isLogin(String accessToken);
}
