package com.wechat.manage.account.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.base.api.WechatAccountApi;
import com.wechat.base.common.request.Request;
import com.wechat.base.common.response.Result;
/**
 * 
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController implements WechatAccountApi {

	/**
	 * 创建账户
	 */
	@PostMapping
	public Result createAccount(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 提现
	 */
	@PostMapping("/cash")
	public Result cash(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 转账
	 */
	@PostMapping("/transfer")
	public Result transferMoney(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

}
