package com.wechat.base.api;

import com.wechat.base.common.response.Result;
import com.wechat.base.model.user.User;

/**
 * 用户中心
 * @author chrilwe
 *
 */
public interface WechatUserApi {
	
	//检查邮箱是否被注册
	public Result checkEmail(String email);
	
	//检查手机号是否被注册
	public Result checkPhone(String phone);
	
	//用户注册
	public Result registe(User user);
	
	//根据账号或者手机号或者邮箱查询
	public User queryUserByAccountNoOrPhoneOrEmail(String param);
	
	//根据userId查询
	public User queryUserById(int userId);
	
	//更新userPic
	public Result updateUserPic(String userPic);
	
}
