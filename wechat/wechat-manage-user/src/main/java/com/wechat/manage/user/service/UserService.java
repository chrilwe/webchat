package com.wechat.manage.user.service;

import com.wechat.base.model.user.User;

public interface UserService {
	
	//根据邮箱查询
	public User queryUserByEmail(String email);
	
	//根据手机号码
	public User queryUserByPhone(String phone);
	
	//根据账号查询
	public User queryUserByAccountNo(String accountNo);
	
	//添加用户
	public void addUser(User user);
	
	//根据userId查询
	public User queryById(int userId);
	
	//update userPic
	public void updateUserPic(int userId, String userPic);
}
