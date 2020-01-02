package com.wechat.manage.user.common.msg;

import com.wechat.base.common.msg.Msg;

public class UserMsg extends Msg {
	public static final String VALIDATE_ERR = "validateArgs or validateTypes param error";
	public static final String EMAIL_EXISTS = "邮箱已经被注册";
	public static final String PHONE_EXISTS = "手机号码已经被使用";
	public static final String NICK_NAME_EXISTS = "该昵称已经被使用";
}
