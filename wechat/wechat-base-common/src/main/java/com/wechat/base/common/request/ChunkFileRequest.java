package com.wechat.base.common.request;

import com.wechat.base.common.request.Request;

import lombok.Data;
/**
 * 
 * @author chrilwe
 *
 */
@Data
public class ChunkFileRequest extends Request {
	//分块文件名
	private String chunkFileName;
	//分块文件大小
	private int chunkSize;
	//文件存放在哪个文件目录
	private int fileDetailId;
	//文件全名
	private String fileName;
	//块的数量
	private int chunkFileNum;
	//文件大小
	private int fileSize;
	//文件类型
	private String fileType;
}
