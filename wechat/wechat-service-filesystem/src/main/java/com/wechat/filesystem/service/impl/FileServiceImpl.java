package com.wechat.filesystem.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.request.ChunkFileRequest;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.filesystem.FileDetail;
import com.wechat.base.model.filesystem.FileUploadHistoryDetail;
import com.wechat.base.model.user.User;
import com.wechat.filesystem.client.UserClient;
import com.wechat.filesystem.common.annotation.CheckImageFile;
import com.wechat.filesystem.common.code.FilesystemCode;
import com.wechat.filesystem.common.model.FdfsProperties;
import com.wechat.filesystem.common.msg.FileMsg;
import com.wechat.filesystem.common.response.UpoladImageResult;
import com.wechat.filesystem.mapper.FileDownloadDetailMapper;
import com.wechat.filesystem.mapper.FileUploadHistoryDetailMapper;
import com.wechat.filesystem.service.FileDetailService;
import com.wechat.filesystem.service.FileService;
/**
 * 
 * @author chrilwe
 *
 */
@Service
public class FileServiceImpl implements FileService {
	private final static ExecutorService threadPool = Executors.newFixedThreadPool(50);
	@Autowired
	private FdfsProperties fdfsProperties;
	@Autowired
	private UserClient userClient;
	@Autowired
	private FileDetailService fileDetailService;
	@Autowired
	private FileUploadHistoryDetailMapper fileUploadHistoryDetailMapper;
	@Autowired
	private FileSystem fileSystem;
	@Autowired
	private FileDownloadDetailMapper fileDownloadDetailMapper;

	@Transactional
	public Result uploadOrUpdateImageFile(MultipartFile image, int userId) throws Exception {
		if(image == null) {
			throw new RuntimeException(FileMsg.IMAGE_FILE_NULL);
		}
		
		String originalFilename = image.getOriginalFilename();
		String file_ext_name = originalFilename.substring(originalFilename.indexOf(".")+1);
		String imageUri = this.uploadImage(image.getBytes(), file_ext_name);
		String imageUrl = fdfsProperties.getFdfs_server_host() + "/" + imageUri;
		System.out.println("图片地址：" + imageUrl);
		
		// async thread to update imageUrl and delete originalimage from fdfs and
		// add fileDetail
		threadPool.execute(new Runnable(){
			public void run() {
				User user = userClient.queryUserById(userId);
				String userPic = user.getUserPic();
				if(!StringUtils.isEmpty(userPic)) {
					int hostNameLen = fdfsProperties.getFdfs_server_host().length();
					String fieldId = userPic.substring(hostNameLen - 1);
					deleteImage(fieldId);
					userClient.updateUserPic(userPic);
					FileDetail fileDetail = new FileDetail();
					fileDetail.setCreateTime(new Date());
					fileDetail.setFileSize(image.getSize());
					fileDetail.setFileType(file_ext_name);
					fileDetail.setIsFloder(0);// 0 不是目录
					fileDetail.setOriginalFileName(originalFilename);
					fileDetail.setParentId(0);
					fileDetail.setUserId(userId);
					fileDetailService.addFileDetail(fileDetail );
				}
			}
		});
		return new UpoladImageResult(Code.SUCCESS, Msg.SUCCESS, imageUrl);
	}
	
	/**
	 * init fdfs
	 * @throws MyException 
	 * @throws IOException 
	 */
	private void initFdfs() throws IOException, MyException {
		String classpath = this.getClass().getResource("/").getPath();
		ClientGlobal.init(classpath + "Fdfs.conf");
		ClientGlobal.setG_charset("utf-8");
		ClientGlobal.setG_connect_timeout(fdfsProperties.getConnect_timeout_in_seconds());
		ClientGlobal.setG_network_timeout(fdfsProperties.getNetwork_timeout_in_seconds());
	}
	
	/**
	 * 上传文件到fdfs
	 */
	private String uploadImage(byte[] image, String file_ext_name) throws Exception {
		this.initFdfs();
		StorageClient1 storageClient = this.client();
		String fileId = storageClient.upload_file1(image, file_ext_name, null);
		return fileId;
	}
	
	private StorageClient1 client() throws Exception {
		TrackerClient client = new TrackerClient();
		TrackerServer trackerServer = client.getConnection();
		StorageServer storeStorage = client.getStoreStorage(trackerServer);
		StorageClient1 storageClient = new StorageClient1(trackerServer, storeStorage);
		return storageClient;
	}
	
	/**
	 * 删除图片文件
	 */
	private void deleteImage(String fieldId) {
		try {
			this.initFdfs();
			StorageClient1 client = this.client();
			client.delete_file1(fieldId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查文件目录是否创建
	 */
	public Result checkChunkFile(ChunkFileRequest request, int userId) {
		if(request == null) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		int fileDetailId = request.getFileDetailId();
		//校验当前是否为文件目录,如果为文件，则该文件的父类目录为上传文件目录
		FileDetail fileDetail = fileDetailService.queryFileDetailById(request.getFileDetailId(), userId);
		if(fileDetail == null) {
			return new Result(FilesystemCode.FILE_UPLOAD_ADDRESS_NULL,FileMsg.FILE_UPLOAD_ADDRESS_NULL);
		}
		if(fileDetail.getIsFloder() == 1) {
			fileDetailId = fileDetail.getParentId();
		}
		
		// 校验文件存放目录是否创建,没有则创建
		String fileTmp = this.fileTmp(userId, fileDetailId, request.getFileName());
		this.createFloderIfNotExists(fileTmp);
		
		//检查文件块是否已经上传过了
		FileUploadHistoryDetail fileUploadHistoryDetail = fileUploadHistoryDetailMapper.selectByFileDetailIdAndChunkName(userId, request.getChunkFileName(), fileDetailId);
		if(fileUploadHistoryDetail != null) {
			return new Result(FilesystemCode.CHUNK_FILE_EXISTS,FileMsg.CHUNK_FILE_EXISTS);
		}
		return new Result(Code.SUCCESS, Msg.SUCCESS);
	}
	
	/**
	 * 获取文件地址路径
	 * @param userId
	 * @param fileDetailId
	 * @return
	 */
	private String fileTmp(int userId, int fileDetailId, String fileName) {
		List<FileDetail> floders = fileDetailService.queryFileFloders(userId, fileDetailId);
		String fileTmp = userId + "/" + fileName + "/";
		for(int i = 0;i < floders.size();i++) {
			if(i == (floders.size() - 1)) {
				fileTmp += floders.get(i).getOriginalFileName();
			} else {
				fileTmp += floders.get(i).getOriginalFileName() + "/";
			}
		}
		return fileTmp;
	}
	
	/**
	 * 文件
	 */
	private void createFloderIfNotExists(String fileTmp) {
		Path path = new Path(fileTmp);
		try {
			boolean exists = fileSystem.exists(path);
			if(exists == false) {
				fileSystem.mkdirs(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Result uploadChunkFile(ChunkFileRequest request, int userId, MultipartFile chunkFile) throws IOException {
		int fileDetailId = request.getFileDetailId();
		String chunkFileName = request.getChunkFileName();
		String fileName = request.getFileName();
		
		// get file tmp floder
		String fileTmp = this.fileTmp(userId, fileDetailId, fileName);
		
		//upload chunk file to hdfs
		InputStream inputStream = chunkFile.getInputStream();
		FSDataOutputStream output = fileSystem.create(new Path(fileTmp + "/" + chunkFileName), true);
		int copy = IOUtils.copy(inputStream, output);
		
		// add fileUploadHistory
		FileUploadHistoryDetail detail = new FileUploadHistoryDetail();
		detail.setChunkFileName(Integer.parseInt(chunkFileName));
		detail.setChunkFileNum(request.getChunkFileNum());
		detail.setChunkSize(request.getChunkSize());
		detail.setFileDetailId(fileDetailId);
		detail.setOriginalFileName(fileName);
		detail.setUploadTime(new Date());
		detail.setUserId(userId);
		fileUploadHistoryDetailMapper.insertFileUploadHistory(detail);
		
		// upload success, add fileDetail
		FileDetail fd = fileDetailService.queryFileDetailById(fileDetailId, userId);
		if((request.getChunkFileNum()-1) == Integer.parseInt(chunkFileName)) {
			FileDetail fileDetail = new FileDetail();
			fileDetail.setCreateTime(new Date());
			fileDetail.setFileSize(request.getFileSize());
			fileDetail.setParentId(fileDetailId);
			fileDetail.setGrade(fd.getGrade() + 1);
			fileDetail.setIsFloder(0);
			fileDetail.setOriginalFileName(fileName);
			fileDetail.setUserId(userId);
			fileDetail.setFileType(request.getFileType());
			fileDetailService.addFileDetail(fileDetail);
		}
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	@Transactional
	public FSDataInputStream downLoadFile(int userId, int fileDetailId, int chunkName) throws Exception {
		//查询用户下载文件的进度, 对比进度是否匹配
		int max = fileDownloadDetailMapper.selectMaxChunkFileName(userId, fileDetailId);
		if(max != (chunkName-1)) {
			throw new RuntimeException(FileMsg.DOWNLOAD_ERR);
		}
		
		//从hdfs 下载文件块
		Path path = new Path("/");
		FSDataInputStream input = fileSystem.open(path);
		
		return input;
	}
	
}
