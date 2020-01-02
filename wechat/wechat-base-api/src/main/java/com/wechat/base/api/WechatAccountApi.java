package com.wechat.base.api;

import com.wechat.base.common.request.Request;
import com.wechat.base.common.response.Result;

/**
 * 用户账户管理
 * @author chrilwe
 *
 */
public interface WechatAccountApi {
	// create UserAccount
	public Result createAccount(Request request);
	
	// cash money 提现
	public Result cash(Request request);
	
	//transfer out money 转账
	public Result transferMoney(Request request);
}
