package com.wechat.filesystem.service;

import java.util.List;

import com.wechat.base.model.filesystem.FileDetail;

/**
 * 
 * @author chrilwe
 *
 */
public interface FileDetailService {
	//添加文件信息
	public FileDetail addFileDetail(FileDetail fileDetail);
	
	//删除当前目录以及其子目录
	public void deleteFileDetailById(int fileDetailId, int userId);
	
	//根据文件名和grade查询
	public FileDetail queryFileDetailByFilenameAndPid(String filename, int userId, int parentId);

	//根据fileDetailId查询
	public FileDetail queryFileDetailById(int fileDetailId, int userId);
	
	//根据文件id查询多级文件路径
	public List<FileDetail> queryFileFloders(int userId, int fileDetailId);
}
