package com.wechat.auth.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserJwt extends User {
	
	private int id;
	private String accountNo;
	private String nickName;
	private String userPic;

	public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
