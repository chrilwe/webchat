package com.wechat.manage.page.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.wechat.base.api.WechatPageApi;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.page.PageDataModel;
import com.wechat.base.model.page.PageHtml;
import com.wechat.base.model.page.PageInfo;
import com.wechat.base.model.page.Site;
import com.wechat.base.model.page.Template;
import com.wechat.manage.page.common.code.PageCode;
import com.wechat.manage.page.common.msg.PageMsg;
import com.wechat.manage.page.common.result.GenerateHtmlResult;
import com.wechat.manage.page.common.result.PreviewPageResult;
import com.wechat.manage.page.service.PageService;
import com.wechat.manage.page.service.SiteService;

@RestController
@RequestMapping("/cms")
public class PageController implements WechatPageApi {
	
	private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

	@Autowired
	private PageService pageService;
	@Autowired
	private SiteService siteService;
	
	/**
	 * 创建页面信息接口
	 */
	@PostMapping("/create/pageInfo")
	public Result createPageInfo(@RequestBody PageInfo pageInfo) {
		Result result = pageService.createPageInfo(pageInfo);
		return result;
	}
	
	/**
	 * 创建页面数据模型
	 */
	@PostMapping("/create/pageDataModel")
	public Result createPageDateModel(@RequestBody PageDataModel pageDataModel) {
		Result result = pageService.createPageDateModel(pageDataModel);
		return result;
	}
	
	/**
	 * 创建页面模型
	 */
	@PostMapping("/create/pageTemplate")
	public Result createPageTemplate(@RequestBody Template template) {
		Result result = pageService.createPageTemplate(template);
		return result;
	}
	
	/**
	 * 查询页面信息
	 */
	@GetMapping("/query/pageInfoByPageId")
	public PageInfo queryPageInfoByPageId(@RequestParam("pageId")int pageId) {
		
		return pageService.queryPageInfoByPageId(pageId);
	}
	
	/**
	 * 页面预览
	 */
	@GetMapping("/page/preView")
	public Result preViewPage(@RequestParam("pageId")int pageId) { 
		PageInfo pageInfo = pageService.queryPageInfoByPageId(pageId);
		if(pageInfo == null) {
			return new Result(PageCode.PAGE_NOT_EXISTS,PageMsg.PAGE_NOT_EXISTS);
		}
		String preViewUrl = pageInfo.getPreViewUrl();
		if(StringUtils.isEmpty(preViewUrl)) {
			return new Result(PageCode.PRE_VIEW_URL_NOT_EXISTS,PageMsg.PRE_VIEW_URL_NOT_EXISTS);
		}
		return new PreviewPageResult(Code.SUCCESS, Msg.SUCCESS, preViewUrl);
	}	
	
	/**
	 * 页面发布
	 */ 
	@GetMapping("/page/publish")
	public Result publishPage(@RequestParam("pageId")int pageId) {
		
		return pageService.publishPage(pageId);
	}
	
	/**
	 * 页面生成接口
	 */
	@GetMapping("/page/generate")
	public Result generatePage(@RequestParam("pageId")int pageId) {
		try {
			//generate string html
			GenerateHtmlResult generateStaticHtmlPage = pageService.generateStaticHtmlPage(pageId);
			
			String html = generateStaticHtmlPage.getHtml();
			PageInfo pageInfo = generateStaticHtmlPage.getPageInfo();
			
			//insert pageHtml into database
			PageHtml pageHtml = new PageHtml();
			pageHtml.setHtml(html);
			pageHtml.setPageId(pageId);
			Result result = pageService.createPageHtml(pageHtml);
			
			//generate html text file to floder
			if(result.getCode() == Code.SUCCESS) {
				singleThreadExecutor.submit(new Runnable(){
					public void run() {
						pageService.generateHtmlTextToPath(pageInfo, html);
					}
				});
			}
		} catch (Exception e) {
			//TODO
		}
		return null;
	}
	
	/**
	 * 站点创建接口
	 */
	@PostMapping("/site/create")
	public Result createSite(@RequestBody Site site) {
		
		return siteService.createSite(site);
	}

}
