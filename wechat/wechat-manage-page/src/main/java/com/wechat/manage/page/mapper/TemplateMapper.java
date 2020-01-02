package com.wechat.manage.page.mapper;

import com.wechat.base.model.page.Template;

/**
 * 
 * @author chrilwe
 *
 */
public interface TemplateMapper {
	public void insertTemplate(Template Template);
	
	public void updateTemplate(Template Template);
	
	public void deleteTemplateById(int id);
	
	public Template findTemplateById(int id);
}
