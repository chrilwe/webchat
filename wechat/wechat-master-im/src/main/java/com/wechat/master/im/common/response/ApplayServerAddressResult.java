package com.wechat.master.im.common.response;

import com.wechat.base.common.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 申请连接地址结果
 * @author chrilwe
 *
 */
@Data
@ToString
public class ApplayServerAddressResult extends Result {
	
	private String accessToken;
	
	private String serverAddress;
	
	public ApplayServerAddressResult(int code, String msg, String accessToken, String serverAddress) {
		super(code, msg);
		this.accessToken = accessToken;
		this.serverAddress = serverAddress;
	}

}
