package com.wechat.base.model.page;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 页面模型
 * @author chrilwe
 *
 */
@Data
@ToString
public class PageInfo {
	private int id;
	private String pageName;//页面名称
	private String pageNameCode;//页面字符，命名生成的html页面
	private int siteId;//站点id
	private String url;//访问地址
	private String preViewUrl;//预览访问路径
	private int templateId;//模板id
	private Date createTime;
	private Date updateTime;
	private int pageDataModelId;//页面数据模型
	private String pageAddress;//生成页面存放目录物理位置
	private String preViewPageAddress;//生成预览页面目录物理路径
	private String projectRequestRootAddress;//项目访问根目录地址
	private String status;//状态
}
