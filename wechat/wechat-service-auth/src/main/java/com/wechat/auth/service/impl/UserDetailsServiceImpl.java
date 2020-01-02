package com.wechat.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.wechat.auth.client.UserServiceClient;
import com.wechat.auth.config.UserJwt;
import com.wechat.base.model.user.Permission;
import com.wechat.base.model.user.ext.UserExt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private UserServiceClient userServiceClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 取出身份，如果身份为空说明没有认证
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
		if (authentication == null) {
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
			if (clientDetails != null) {
				// 密码
				String clientSecret = clientDetails.getClientSecret();
				return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
			}
		}
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		// 调用服务查询用户
		/*UserExt userExt = (UserExt) userServiceClient.queryUserByAccountNoOrPhoneOrEmail(username);
		if (userExt == null) {
			return null;
		}*/
		List<Permission> list = new ArrayList<>();
		Permission p = new Permission();
		p.setName("全部权限");
		p.setPermissionCode("admin");
		list.add(p);
		UserExt userExt = new UserExt();
		userExt.setAccountNo("123");
		userExt.setNickName("admin");
		userExt.setPassword(new String(Base64Utils.encode("123".getBytes())));
		userExt.setPermissions(list);
		userExt.setUserPic("userpic");
		
		
		List<Permission> permissions = userExt.getPermissions();
		String permissionCodeString = "";
		for(int i = 0;i < permissions.size();i++) {
			String permissionCode = permissions.get(i).getPermissionCode();
			if(i == (permissions.size() - 1)) {
				permissionCodeString += permissionCode;
			} else {
				permissionCodeString += permissionCode + ",";
			}
		}
		UserDetails userDetails = new User(username, userExt.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(permissionCodeString));
		return userDetails;
	}
}
