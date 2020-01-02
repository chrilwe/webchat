package com.wechat.base.model.page;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 页面模板基本数据
 * @author chriwe
 *
 */
@Data
@ToString
public class Template { 
	private int id;
	private String templateName;//模板名称
	private Date createTime;
	private Date updateTime;
	private String template;//模板文件
}
