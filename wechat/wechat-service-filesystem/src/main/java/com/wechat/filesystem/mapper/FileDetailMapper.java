package com.wechat.filesystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wechat.base.model.filesystem.FileDetail;

public interface FileDetailMapper {
	// insert 
	public FileDetail insertFileDetail(FileDetail fileDetail);
	
	// delete by id
	public void deleteByIdAndUid(@Param("fileId")int fileId, @Param("userId")int userId);
	
	// update 
	public void update(FileDetail fileDeatil);
	
	// select by id
	public FileDetail selectById(int fileId);
	
	// slelect by filename
	public FileDetail selectByFilenameAndPid(@Param("filename")String filename, @Param("userId")int userId, @Param("parentId")int parentId);
	
	// select by id and uid
	public FileDetail selectByIdAndUid(@Param("userId")int userId,@Param("fileDetailId")int fileDetailId);

	// select file floders
	public List<FileDetail> selectFileFlodersById(@Param("userId")int userId, @Param("fileDetailId")int fileDetailId);
}
