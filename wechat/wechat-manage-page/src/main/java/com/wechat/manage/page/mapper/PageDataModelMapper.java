package com.wechat.manage.page.mapper;

import com.wechat.base.model.page.PageDataModel;

/**
 * 
 * @author chrilwe
 *
 */
public interface PageDataModelMapper {
	public void insertPageDataModel(PageDataModel pageDataModel);
	
	public void updatePageDataModel(PageDataModel pageDataModel);
	
	public void deletePageDataModelById(int id);
	
	public PageDataModel findPageDataModelById(int id);
}
