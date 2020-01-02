package com.wechat.filesystem.mapper;

import org.apache.ibatis.annotations.Param;

import com.wechat.base.model.filesystem.FileUploadHistoryDetail;

/**
 * 文件上传记录
 * @author chrilwe
 *
 */
public interface FileUploadHistoryDetailMapper {
	//添加上传记录
	public void insertFileUploadHistory(FileUploadHistoryDetail detail);
	
	//根据fileDetailId和chunkName查询
	public FileUploadHistoryDetail selectByFileDetailIdAndChunkName(@Param("userId")int userId, @Param("chunkName")String chunkName, @Param("fileDetailId")int fileDetailId);
}
