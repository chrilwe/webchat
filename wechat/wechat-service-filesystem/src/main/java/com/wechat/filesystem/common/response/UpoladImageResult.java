package com.wechat.filesystem.common.response;

import com.wechat.base.common.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class UpoladImageResult extends Result {
	
	//file address
	private String imageAddress;

	public UpoladImageResult(int code, String msg, String imageAddress) {
		super(code, msg);
		this.imageAddress = imageAddress;
	}

}
