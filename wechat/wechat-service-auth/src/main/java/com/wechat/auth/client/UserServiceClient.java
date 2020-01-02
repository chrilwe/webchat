package com.wechat.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wechat.base.common.servicesname.WechatServices;
import com.wechat.base.model.user.User;

@FeignClient(WechatServices.WECHAT_MANAGE_USER)
public interface UserServiceClient {
	
	@GetMapping("/queryUserByAccountNoOrPhoneOrEmail")
	public User queryUserByAccountNoOrPhoneOrEmail(String param);
}
