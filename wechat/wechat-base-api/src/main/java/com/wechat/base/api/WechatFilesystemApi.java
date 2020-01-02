package com.wechat.base.api;

import org.springframework.web.multipart.MultipartFile;

import com.wechat.base.common.request.ChunkFileRequest;
import com.wechat.base.common.request.Request;
import com.wechat.base.common.response.Result;

/**
 * 文件管理
 * @author chrilwe
 *
 */
public interface WechatFilesystemApi {
	//上传或者更新小文件头像文件到fdfs服务器
	public Result uploadOrUpdateImageFile(MultipartFile image);
	
	// 创建自定义文件夹
	public Result createFloder(String floderName, int parentId);
	
	// 上传用户大文件切成的小块文件到临时目录下
	public Result uploadFile(ChunkFileRequest uploadRequest, MultipartFile chunkFile);
	
	// 检查文件块是否已经上传过了
	public Result checkChunkFile(ChunkFileRequest checkRequest);
	
	//删除文件(如果是目录删除当前目录及其子目录)
	public Result deleteFile(int fileDetailId);
	
	//文件下载
	public Result downloadFile(int fileDetailId, int chunkName);
}
