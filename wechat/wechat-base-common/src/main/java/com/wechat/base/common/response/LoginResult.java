package com.wechat.base.common.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResult extends Result {

	public LoginResult(int code, String msg) {
		super(code, msg);
	}

}
