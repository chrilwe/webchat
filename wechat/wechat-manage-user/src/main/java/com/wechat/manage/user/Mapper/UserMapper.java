package com.wechat.manage.user.Mapper;

import org.apache.ibatis.annotations.Param;

import com.wechat.base.model.user.User;

public interface UserMapper {
	public User findUserByEmail(String email);
	
	public User findUserByPhone(String phone);
	
	public User findUserByAccountNo(String accountNo);
	
	public void insertUser(User user);
	
	public User findUserByUserId(int userId);
	
	public void updateUserPic(@Param("userId")int userId,@Param("userPic")String userPic);
}
