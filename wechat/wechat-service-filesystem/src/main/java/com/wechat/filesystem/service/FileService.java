package com.wechat.filesystem.service;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.springframework.web.multipart.MultipartFile;

import com.wechat.base.common.request.ChunkFileRequest;
import com.wechat.base.common.response.Result;

/**
 * 
 * @author chrilwe
 *
 */
public interface FileService {
	public Result uploadOrUpdateImageFile(MultipartFile image, int userId) throws Exception;
	
	//检查上传文件
	public Result checkChunkFile(ChunkFileRequest request, int userId);
	
	//上传文件
	public Result uploadChunkFile(ChunkFileRequest request, int userId, MultipartFile chunkFile) throws IOException;

	
	//文件下载
	public FSDataInputStream downLoadFile(int userId, int fileDetailId, int chunkName) throws Exception;
}
