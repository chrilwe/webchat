package com.wechat.base.model.filesystem;

import java.util.Date;

/**
 * 文件下载进度
 * @author chrilwe
 *
 */
public class FileDownloadHistoryDetail {
	private int userId;
	private String id;
	//全文件名
	private String originalFileName;
	//下载时间
	private Date downloadTime;
	//当前下载文件分块名
	private int chunkFileName;
	//文件切片数量
	private int chunkFileNum;
	//文件切片大小
	private int chunkSize;
	//文件详情
	private int fileDetailId;
}
