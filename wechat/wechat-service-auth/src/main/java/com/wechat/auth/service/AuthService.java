package com.wechat.auth.service;

import com.wechat.base.common.response.LoginResult;
import com.wechat.base.model.auth.AuthToken;

public interface AuthService {
	public AuthToken login(String accountNo, String password);
	
	public boolean login(String accessToken);
}
