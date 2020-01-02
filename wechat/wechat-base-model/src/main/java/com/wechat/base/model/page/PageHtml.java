package com.wechat.base.model.page;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 生成的html页面
 * @author chrilwe
 *
 */
@Data
@ToString
public class PageHtml {
	private int id;
	private String html;
	private int pageId;
	private Date createTime;
}
