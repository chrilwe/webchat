package com.wechat.manage.page.mapper;

import com.wechat.base.model.page.Site;

/**
 * 
 * @author chrilwe
 *
 */
public interface SiteMapper {
	public void insertSite(Site Site);
	
	public void updateSite(Site Site);
	
	public void deleteSiteById(int id);
	
	public Site findSiteById(int id);
}
