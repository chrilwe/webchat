package com.wechat.manage.page.mapper;

import com.wechat.base.model.page.PageHtml;

/**
 * 静态页面数据
 * @author chrilwe
 *
 */
public interface PageHtmlMapper {
	public void insertPageHtml(PageHtml PageHtml);
	
	public void updatePageHtml(PageHtml PageHtml);
	
	public void deleteByPageId(int pageId);
	
	public PageHtml findByPageId(int pageId);
	
}
