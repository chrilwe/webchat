package com.wechat.filesystem.common.model;

import lombok.Data;
import lombok.ToString;

/**
 * FastDfs properties
 * @author chrilwe
 *
 */
@Data
@ToString
public class FdfsProperties {
	//fdfs 服务器域名地址
	private String fdfs_server_host;
	private int network_timeout_in_seconds;
	private int connect_timeout_in_seconds;
	//文件上传最大值(/b)
	private long image_max;
	//允许文件上传类型
	private String image_types;
}
