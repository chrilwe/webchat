package com.wechat.base.model.page;

import lombok.Data;

import java.util.Date;

import lombok.ToString;

/**
 * 页面数据模型
 * @author chrilwe
 *
 */
@Data
@ToString
public class PageDataModel {
	private int id;
	private String pageJsonData;//页面模型map json
	private String description;//描述
	private Date createTime;
	private Date updateTime;
}
