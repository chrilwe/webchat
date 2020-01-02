package com.wechat.master.im.controller;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.base.api.WechatImMasterApi;
import com.wechat.base.common.controller.BaseController;
import com.wechat.base.common.response.Result;
import com.wechat.base.utils.CookieUtil;
/**
 * 
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/im_master")
public class ImMasterController extends BaseController implements WechatImMasterApi {
	
	@Value("${im.cookie_name}")
	private String cookieName;
	
	/**
	 * 申请获取连接服务器的地址
	 */
	@GetMapping("/applay_connect_server_address")
	public Result applayServerConnectAddress() {
		Cookie cookie = CookieUtil.getCookieValueByName(request, cookieName);
		String accessToken = cookie.getValue();
		return null;
	}

}
