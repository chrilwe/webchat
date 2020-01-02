package com.wechat.filesystem.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wechat.base.common.response.Result;
import com.wechat.base.common.servicesname.WechatServices;
import com.wechat.base.model.user.User;
/**
 * user client
 * @author chrilwe
 *
 */
@FeignClient(WechatServices.WECHAT_MANAGE_USER)
@RequestMapping("/user")
public interface UserClient {
	
	@GetMapping("/queryUserById")
	public User queryUserById(@RequestParam("userId")int userId);
	
	@PostMapping("/updateUserPic")
	public Result updateUserPic(@RequestBody String userPic);
}
