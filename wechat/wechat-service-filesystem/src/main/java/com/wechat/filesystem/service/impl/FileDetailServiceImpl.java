package com.wechat.filesystem.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.base.common.msg.Msg;
import com.wechat.base.model.filesystem.FileDetail;
import com.wechat.filesystem.mapper.FileDetailMapper;
import com.wechat.filesystem.service.FileDetailService;

@Service
public class FileDetailServiceImpl implements FileDetailService {
	
	@Autowired
	private FileDetailMapper fileDetailMapper;

	@Transactional
	public FileDetail addFileDetail(FileDetail fileDetail) {
		if(fileDetail == null) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		return fileDetailMapper.insertFileDetail(fileDetail);
	}

	@Transactional
	public void deleteFileDetailById(int fileDetailId, int userId) {
		fileDetailMapper.deleteByIdAndUid(fileDetailId, userId);
	}

	public FileDetail queryFileDetailByFilenameAndPid(String filename, int userId, int parentId) {
		FileDetail fileDetail = fileDetailMapper.selectByFilenameAndPid(filename, userId, parentId);
		return fileDetail;
	}

	public FileDetail queryFileDetailById(int fileDetailId, int userId) {
		FileDetail fileDetail = fileDetailMapper.selectByIdAndUid(userId, fileDetailId);
		return fileDetail;
	}

	public List<FileDetail> queryFileFloders(int userId, int fileDetailId) {
		List<FileDetail> list = fileDetailMapper.selectFileFlodersById(userId, fileDetailId);
		return list;
	}

}
