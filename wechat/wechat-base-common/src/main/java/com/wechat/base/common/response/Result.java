package com.wechat.base.common.response;

import com.wechat.base.common.code.Code;

import lombok.Data;
import lombok.ToString;

/**
 * 请求响应结果
 * @author chrilwe
 *
 */
@Data
@ToString
public class Result {
	private int code;
	private String msg;
	private boolean SUCCESS;
	
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
		if(code == Code.SUCCESS) {
			this.SUCCESS = true;
		} else {
			this.SUCCESS = false;
		}
	}
}
