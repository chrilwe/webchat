package com.wechat.base.model.filesystem;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 文件上传进度记录
 * @author chrilwe
 *
 */
@Data
@ToString
public class FileUploadHistoryDetail {
	private int userId;
	private String id;
	//全文件名
	private String originalFileName;
	//上传时间
	private Date uploadTime;
	//当前上传文件分块名
	private int chunkFileName;
	//文件切片数量
	private int chunkFileNum;
	//文件切片大小
	private int chunkSize;
	//上传到指定文件夹下
	private int fileDetailId;
}
