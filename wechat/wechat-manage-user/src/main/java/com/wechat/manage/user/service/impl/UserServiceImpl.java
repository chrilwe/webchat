package com.wechat.manage.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.base.common.msg.Msg;
import com.wechat.base.model.user.User;
import com.wechat.manage.user.Mapper.UserMapper;
import com.wechat.manage.user.annotation.Validate;
import com.wechat.manage.user.aspect.ValidateType;
import com.wechat.manage.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Validate(validateArgs={0},validateType={ValidateType.EMAIL})
	public User queryUserByEmail(String email) {
		
		return userMapper.findUserByEmail(email);
	}

	@Validate(validateArgs={0},validateType={ValidateType.PHONE})
	public User queryUserByPhone(String phone) {
		
		return userMapper.findUserByPhone(phone);
	}

	public User queryUserByAccountNo(String accountNo) {
		
		return userMapper.findUserByAccountNo(accountNo);
	}

	@Transactional
	public void addUser(User user) {
		if(user == null) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		userMapper.insertUser(user);
	}
	
	
	public User queryById(int userId) {
		
		return userMapper.findUserByUserId(userId);
	}

	@Transactional
	public void updateUserPic(int userId, String userPic) {
		userMapper.updateUserPic(userId, userPic);
	}
}
