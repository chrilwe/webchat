package com.wechat.filesystem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wechat.base.api.WechatFilesystemApi;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.controller.BaseController;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.request.ChunkFileRequest;
import com.wechat.base.common.request.Request;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.filesystem.FileDetail;
import com.wechat.base.model.user.User;
import com.wechat.filesystem.common.annotation.CheckImageFile;
import com.wechat.filesystem.common.aspect.Test;
import com.wechat.filesystem.common.code.FilesystemCode;
import com.wechat.filesystem.common.msg.FileMsg;
import com.wechat.filesystem.common.response.UpoladImageResult;
import com.wechat.filesystem.service.FileDetailService;
import com.wechat.filesystem.service.FileService;
import com.wechat.filesystem.service.impl.FileServiceImpl;
/**
 * 文件管理系统
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/filesystem")
public class FilesystemController extends BaseController implements WechatFilesystemApi {
	
	@Autowired
	private FileService fileService;
	@Autowired
	private FileDetailService fileDetailService;
	
	/**
	 * 上传或者更新图片文件
	 */
	@PostMapping("/upload")
	@CheckImageFile(index=0)
	public Result uploadOrUpdateImageFile(@RequestBody MultipartFile image) {
		UpoladImageResult result = null;
		int userId = 0;
		try {
			result = (UpoladImageResult) fileService.uploadOrUpdateImageFile(image, userId);
		} catch (Exception e) {
			//TODO 
		}
		return result;
	}
	
	@Autowired
	Test test;
	@GetMapping("/test")
	public void test() {
		test.test();
	}
	
	
	/**
	 * 自定义文件夹
	 * @Param floderName 文件名
	 * @param grade  文件级别
	 */
	@PostMapping("/createFloder")
	public Result createFloder(@RequestBody String floderName, @RequestBody int parentId) {
		int userId = 1;
		// check floder is exists
		if(StringUtils.isEmpty(floderName)) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		FileDetail fileDetail = fileDetailService.queryFileDetailByFilenameAndPid(floderName, userId, parentId);
		if(fileDetail != null) {
			return new Result(FilesystemCode.FLODER_EXISTS,FileMsg.FLODER_EXISTS);
		}
		
		//create file detail
		FileDetail file = fileDetailService.queryFileDetailById(parentId, userId);
		FileDetail fd = new FileDetail();
		fd.setCreateTime(new Date());
		fd.setFileType("floder");
		fd.setGrade(file.getGrade() + 1);
		fd.setIsFloder(1);
		fd.setOriginalFileName(floderName);
		fd.setParentId(parentId);
		fd.setUserId(userId);
		FileDetail res = fileDetailService.addFileDetail(fileDetail);
		return new Result(Code.SUCCESS, Msg.SUCCESS);
	}
	
	/**
	 * 上传切片文件到临时文件目录下
	 */
	@PostMapping("/uploadChunkFile")
	public Result uploadFile(@RequestBody ChunkFileRequest uploadRequest,@RequestBody MultipartFile chunkFile) {
		int userId = 0;
		Result result = null;
		try {
			result = fileService.uploadChunkFile(uploadRequest, userId, chunkFile);
		} catch (Exception e) {
			return new Result(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
		}
		return result;
	}
	
	/**
	 * 检查切片文件，获取上传进度
	 */
	@GetMapping("/checkChunkFile")
	public Result checkChunkFile(@RequestParam("request")ChunkFileRequest checkFileRequest) {
		int userId = 0;
		Result result = fileService.checkChunkFile(checkFileRequest, userId);
		return result;
	}
	
	/**
	 * 删除当前文件以及下面的所有文件
	 */
	@PostMapping("/deleteFile")
	public Result deleteFile(@RequestParam("fileDetailId")int fileDetailId) {
		int userId = 0;
		fileDetailService.deleteFileDetailById(fileDetailId, userId);
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	/**
	 * 文件分块下载
	 */
	@PostMapping("/downloadChunkFile")
	public Result downloadFile(int fileDetailId, int chunkName) {
		try {
			int userId = 0;
			FSDataInputStream input = fileService.downLoadFile(userId, fileDetailId, chunkName);
			response.getOutputStream().write(input.readByte());
		} catch (Exception e) {
			return new Result(Code.SYSTEM_ERR,Msg.SYSTEM_ERR);
		} 
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}
}
