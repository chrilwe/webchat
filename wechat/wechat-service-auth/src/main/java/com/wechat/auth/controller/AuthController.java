package com.wechat.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.auth.common.code.AuthCode;
import com.wechat.auth.common.model.OauthProperties;
import com.wechat.auth.common.msg.AuthMsg;
import com.wechat.auth.service.AuthService;
import com.wechat.base.api.WechatAuthApi;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.controller.BaseController;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.LoginResult;
import com.wechat.base.model.auth.AuthToken;
import com.wechat.base.utils.CookieUtil;
/**
 * 
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/")
public class AuthController extends BaseController implements WechatAuthApi {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private OauthProperties oauthProperties;
	
	/**
	 * 登录 认证接口
	 */
	@PostMapping("/login")
	public LoginResult login(String accountNo, String password) {
		if(StringUtils.isAnyEmpty(accountNo,password)) {
			return new LoginResult(AuthCode.USERNAME_OR_PASSWORD_NULL,AuthMsg.USERNAME_OR_PASSWORD_NULL);
		}
		try{
			//applay jwt token
			AuthToken authToken = authService.login(accountNo, password);
			if(authToken == null) {
				return new LoginResult(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
			}
			
			//save access token into cookie
			String accessToken = authToken.getAccessToken();
			CookieUtil.setCookie(response, oauthProperties.getCookie_name(), accessToken, oauthProperties.getCookie_max_age(), oauthProperties.getDomain());
		} catch (Exception e) {
			String message = e.getMessage();
			if(message.equals(AuthMsg.ERR_TOKEN)) {
				return new LoginResult(AuthCode.ERR_TOKEN,AuthMsg.ERR_TOKEN);
			} else if(message.equals(AuthMsg.UNAUTHRIZATION)) {
				return new LoginResult(AuthCode.UNAUTHROIZATION,AuthMsg.UNAUTHRIZATION);
			} else {
				return new LoginResult(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
			}
		}
		return new LoginResult(Code.SUCCESS,Msg.SUCCESS);
	}

	@GetMapping("/isLogin")
	public boolean isLogin(String accessToken) {
		
		return false;
	}

}
