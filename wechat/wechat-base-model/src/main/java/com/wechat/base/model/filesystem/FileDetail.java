package com.wechat.base.model.filesystem;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class FileDetail {
	private int id;
	//源文件名称
	private String originalFileName;
	//文件类型
	private String fileType;
	//文件大小
	private long fileSize;
	//创建日期
	private Date createTime;
	//更新日期
	private Date updateTime;
	//所属者
	private int userId;
	//是否为目录文件
	private int isFloder;
	//排序号
	private int orderNo;
	//父类id
	private int parentId;
	//目录级别
	private int grade;
	//文件地址
	private String fileAddress;
}
