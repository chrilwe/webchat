package com.wechat.manage.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.loadbalancer.IRule;
import com.wechat.base.api.WechatUserApi;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.user.Permission;
import com.wechat.base.model.user.User;
import com.wechat.base.model.user.ext.UserExt;
import com.wechat.manage.user.common.code.UserCode;
import com.wechat.manage.user.common.msg.UserMsg;
import com.wechat.manage.user.service.PermissionService;
import com.wechat.manage.user.service.UserService;
/**
 * 
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/user")
public class UserController implements WechatUserApi {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 检查邮箱是否已经被注册
	 */
	@GetMapping("/checkEmail")
	public Result checkEmail(@RequestParam("email")String email) {
		User user = null;
		try {
			user = userService.queryUserByEmail(email);
		} catch (Exception e) {
			if(e.getMessage().equals(Msg.REQUEST_PARAM_ERR)) {
				return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
			} else if(e.getMessage().equals(UserMsg.VALIDATE_ERR)) {
				return new Result(UserCode.VALIDATE_ERR,UserMsg.VALIDATE_ERR);
			} else {
				return new Result(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
			}
		}
		if(user != null) {
			return new Result(UserCode.EMAIL_EXISTS,UserMsg.EMAIL_EXISTS);
		}
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	/**
	 * 检查手机号码是否已经被注册
	 */
	@GetMapping("/checkPhone")
	public Result checkPhone(@RequestParam("phone")String phone) {
		User user = null;
		try {
			user = userService.queryUserByPhone(phone);
		} catch (Exception e) {
			if(e.getMessage().equals(Msg.REQUEST_PARAM_ERR)) {
				return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
			} else if(e.getMessage().equals(UserMsg.VALIDATE_ERR)) {
				return new Result(UserCode.VALIDATE_ERR,UserMsg.VALIDATE_ERR);
			} else {
				return new Result(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
			}
		}
		if(user != null) {
			return new Result(UserCode.PHONE_EXISTS,UserMsg.PHONE_EXISTS);
		}
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}
	
	/**
	 * 注册接口
	 */
	@PostMapping("/registe")
	public Result registe(@RequestBody User user) {
		//校验必要参数是否填写
		if(user == null) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		
		return null;
	} 
	
	/**
	 * 根据多个条件查询user
	 */
	@GetMapping("/queryUserByAccountNoOrPhoneOrEmail")
	public User queryUserByAccountNoOrPhoneOrEmail(@RequestParam("param")String param) {
		// query user
		User user = userService.queryUserByAccountNo(param);
		if(user == null) {
			user = userService.queryUserByEmail(param);
			if(user == null) {
				user = userService.queryUserByPhone(param);
			}
		}
		
		//query user permissions
		List<Permission> permissionList = permissionService.queryPermissionsByUserId(user.getId());
		UserExt userExt = new UserExt();
		userExt.setPermissions(permissionList);
		return userExt;
	}

	/**
	 * query by userId
	 */
	@GetMapping("/queryUserById")
	public User queryUserById(@RequestParam("userId")int userId) {
		
		return userService.queryById(userId);
	}

	@PostMapping("/updateUserPic")
	public Result updateUserPic(@RequestBody String userPic) {
		
		return null;
	}
	
}
