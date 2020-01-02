package com.wechat.manage.page.common.result;

import com.wechat.base.model.page.PageInfo;

import lombok.Data;
import lombok.ToString;

/**
 * 页面生成返回结果
 * @author chrilwe
 *
 */
@Data
@ToString
public class GenerateHtmlResult {
	//生成静态页面
	private String html;
	private PageInfo pageInfo;
}
