package com.wechat.filesystem.common.model;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class HadoopProperties {
	// hdfs namenode uri
	private String hdfsURI;
	// hadoop.home.dir
	private String hadoop_home_dir;
}
