package com.wechat.manage.page.service;

import com.wechat.base.common.response.Result;
import com.wechat.base.model.page.PageDataModel;
import com.wechat.base.model.page.PageHtml;
import com.wechat.base.model.page.PageInfo;
import com.wechat.base.model.page.Template;
import com.wechat.manage.page.common.result.GenerateHtmlResult;
/**
 * 
 * @author chrilwe
 *
 */
public interface PageService {
	/**
	 * 创建页面基本信息接口
	 */
	public Result createPageInfo(PageInfo pageInfo);
	
	/**
	 * 创建页面数据模型接口
	 */
	public Result createPageDateModel(PageDataModel pageDataModel);
	
	/**
	 * 创建页面模板
	 */
	public Result createPageTemplate(Template template);
	
	/**
	 * 根据pageId查询页面信息
	 */
	public PageInfo queryPageInfoByPageId(int pageId);
	
	/**
	 * 生成静态页面字符串
	 * @throws Exception 
	 */
	public GenerateHtmlResult generateStaticHtmlPage(int pageId) throws Exception;
	
	/**
	 * 创建PageHtml到database
	 */
	public Result createPageHtml(PageHtml pageHtml);
	
	/**
	 * 生成html文本文件到目标目录
	 */
	public void generateHtmlTextToPath(PageInfo pageInfo, String html);
	
	/**
	 * 页面发布
	 */
	public Result publishPage(int pageId);
	
}
