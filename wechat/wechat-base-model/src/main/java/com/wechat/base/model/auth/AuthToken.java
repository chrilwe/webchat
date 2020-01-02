package com.wechat.base.model.auth;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class AuthToken {
	private String accessToken;
	private String jwtToken;
	private String refreshToken;
}
