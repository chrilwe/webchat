package com.wechat.filesystem.common.msg;

import com.wechat.base.common.msg.Msg;
/**
 * error msg
 * @author chrilwe
 *
 */
public class FileMsg extends Msg {
	public static final String IMAGE_FILE_NULL = "图片文件不能为空";
	public static final String IMAGE_SIZE_OUT_INDEX = "图片大小超过限制值";
	public static final String IMAGE_TYPE_UNSUPPORT = "不支持当前图片类型";
	public static final String FLODER_EXISTS = "文件已经存在";
	public static final String FILE_UPLOAD_ADDRESS_NULL = "请指定上传文件的存放目录";
	public static final String CHUNK_FILE_EXISTS = "文件块已存在";
	public static final String DOWNLOAD_ERR = "文件下载错误，重新下载";
}
