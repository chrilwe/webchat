package com.wechat.manage.page.common.result;

import com.wechat.base.common.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 页面预览请求结果
 * @author chrilwe
 *
 */
@Data
@ToString
public class PreviewPageResult extends Result {
	
	//预览页面访问地址
	private String previewUrl;

	public PreviewPageResult(int code, String msg, String previewUrl) {
		super(code, msg);
		this.previewUrl = previewUrl;
	}	

}
