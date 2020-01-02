package com.wechat.auth.common.msg;

import com.wechat.base.common.msg.Msg;

/**
 * 
 * @author chrilwe
 *
 */
public class AuthMsg extends Msg {
	public static final String USERNAME_OR_PASSWORD_NULL = "用户账号或者密码不能为空";
	public static final String UNAUTHRIZATION = "未认证";
	public static final String ERR_TOKEN = "坏的凭证";
}
