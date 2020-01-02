package com.wechat.base.api;

import com.wechat.base.common.response.Result;
import com.wechat.base.model.page.PageDataModel;
import com.wechat.base.model.page.PageInfo;
import com.wechat.base.model.page.Site;
import com.wechat.base.model.page.Template;

/**
 * 页面管理api
 * @author chrilwe
 *
 */
public interface WechatPageApi {
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
	 * 页面预览接口
	 */
	public Result preViewPage(int pageId);
	
	
	/**
	 * 发布页面
	 */
	public Result publishPage(int pageId);
	
	/**
	 * 页面生成
	 */
	public Result generatePage(int pageId);
	
	/**
	 * 创建站点
	 */
	public Result createSite(Site site);
}
