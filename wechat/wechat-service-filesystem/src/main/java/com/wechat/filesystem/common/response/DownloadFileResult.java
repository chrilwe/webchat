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
public class DownloadFileResult extends Result {
	
	//当前文件块名
	private int chunkName;

	public DownloadFileResult(int code, String msg, int chunkName) {
		super(code, msg);
		this.chunkName = chunkName;
	}

}
