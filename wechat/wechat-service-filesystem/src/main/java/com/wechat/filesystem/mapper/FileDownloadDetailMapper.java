package com.wechat.filesystem.mapper;

import org.apache.ibatis.annotations.Param;

import com.wechat.base.model.filesystem.FileDownloadHistoryDetail;

/**
 * 
 * @author chrilwe
 *
 */
public interface FileDownloadDetailMapper {
	public int selectMaxChunkFileName(@Param("userId")int userId, @Param("fileDetailId")int fileDetailId);
}
