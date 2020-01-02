package com.wechat.base.model.page;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 站点
 * @author chrilwe
 *
 */
@Data
@ToString
public class Site {
	private int id;
	private String hostName;//域名
	private int status;
	private Date createTime;
	private Date updateTime;
	private String siteType;//类型：http  https
}
