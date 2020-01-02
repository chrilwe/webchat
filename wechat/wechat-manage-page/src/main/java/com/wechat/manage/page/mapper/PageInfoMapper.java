package com.wechat.manage.page.mapper;

import com.wechat.base.model.page.PageInfo;

/**
 * 
 * @author chrilwe
 *
 */
public interface PageInfoMapper {
	public void insertPageInfo(PageInfo pageInfo);
	
	public void updatePageInfo(PageInfo pageInfo);
	
	public void deleteByPageId(int pageId);
	
	public PageInfo findByPageId(int pageId);
	
	public PageInfo findByPageName(String pageName);
}
