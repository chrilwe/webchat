package com.wechat.manage.page.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.alibaba.fastjson.JSON;
import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.page.PageDataModel;
import com.wechat.base.model.page.PageHtml;
import com.wechat.base.model.page.PageInfo;
import com.wechat.base.model.page.Site;
import com.wechat.base.model.page.Template;
import com.wechat.manage.page.annotation.NotNull;
import com.wechat.manage.page.common.code.PageCode;
import com.wechat.manage.page.common.msg.PageMsg;
import com.wechat.manage.page.common.result.GenerateHtmlResult;
import com.wechat.manage.page.common.status.Status;
import com.wechat.manage.page.mapper.PageDataModelMapper;
import com.wechat.manage.page.mapper.PageHtmlMapper;
import com.wechat.manage.page.mapper.PageInfoMapper;
import com.wechat.manage.page.mapper.SiteMapper;
import com.wechat.manage.page.mapper.TemplateMapper;
import com.wechat.manage.page.service.PageService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
/**
 * 
 * @author chrilwe
 *
 */
@Service
public class PageServiceImpl implements PageService {

	private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	@Autowired
	private PageInfoMapper pageInfoMapper;
	@Autowired
	private TemplateMapper templateMapper;
	@Autowired
	private PageDataModelMapper pageDataModelMapper;
	@Autowired
	private PageHtmlMapper pageHtmlMapper;
	@Autowired
	private SiteMapper siteMapper;
	//project root address
	@Value("${page.projectRequestRootAddress}")
	private String projectRequestRootAddress;
	
	@Transactional
	@NotNull(methodParamIndexs={0})
	public Result createPageInfo(PageInfo pageInfo) {
		String url = pageInfo.getUrl();
		String pageAddress = pageInfo.getPageAddress();
		int siteId = pageInfo.getSiteId();
		int pageDataModelId = pageInfo.getPageDataModelId();
		int templateId = pageInfo.getTemplateId();
		String pageNameCode = pageInfo.getPageNameCode();
		String preViewPageAddress = pageInfo.getPreViewPageAddress();
		if(StringUtils.isAnyEmpty(url,pageAddress,preViewPageAddress,pageNameCode)) {
			return new Result(Code.REQUEST_PARAM_ERR, Msg.REQUEST_PARAM_ERR);
		}
		PageDataModel pageDataModel = pageDataModelMapper.findPageDataModelById(pageDataModelId);
		if(pageDataModel == null) {
			return new Result(PageCode.PAGEDATAMODEL_NOT_EXISTS,PageMsg.PAGEDATAMODEL_NOT_EXISTS);
		}
		Template template = templateMapper.findTemplateById(templateId);
		if(template == null) {
			return new Result(PageCode.TEMPLATE_NOT_EXISTS,PageMsg.TEMPLATE_NOT_EXISTS);
		}
		PageInfo pageInf = pageInfoMapper.findByPageName(pageInfo.getPageName());
		if(pageInf != null) {
			return new Result(PageCode.PAGENAME_EXISTS,PageMsg.PAGENAME_EXISTS);
		}
		
		pageInfo.setCreateTime(new Date());
		pageInfo.setStatus(Status.PUBLISH_NO);
		pageInfo.setProjectRequestRootAddress(projectRequestRootAddress);
		pageInfo.setPageAddress(projectRequestRootAddress + "/" +pageAddress);
		pageInfo.setPreViewPageAddress(projectRequestRootAddress + "/" + preViewPageAddress);
		pageInfoMapper.insertPageInfo(pageInfo);
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	@Transactional
	@NotNull(methodParamIndexs={0})
	public Result createPageDateModel(PageDataModel pageDataModel) {
		if(pageDataModel == null) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		pageDataModel.setCreateTime(new Date());
		pageDataModelMapper.insertPageDataModel(pageDataModel);
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	@Transactional
	@NotNull(methodParamIndexs={0})
	public Result createPageTemplate(Template template) {
		if(template == null) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		String temp = template.getTemplate();
		String templateName = template.getTemplateName();
		if(StringUtils.isAnyEmpty(temp,templateName)) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		template.setCreateTime(new Date());
		templateMapper.insertTemplate(template);
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	public PageInfo queryPageInfoByPageId(int pageId) {
		
		return pageInfoMapper.findByPageId(pageId);
	}

	
	public GenerateHtmlResult generateStaticHtmlPage(int pageId) throws Exception {
		PageInfo pageInfo = pageInfoMapper.findByPageId(pageId);
		if(pageInfo == null) {
			throw new RuntimeException(PageMsg.PAGE_NOT_EXISTS);
		}
		
		//获取模板
		int templateId = pageInfo.getTemplateId();
		Template template = templateMapper.findTemplateById(templateId);
		if(template == null) {
			throw new RuntimeException(PageMsg.TEMPLATE_NOT_EXISTS);
		}
		
		//获取页面数据模型
		PageDataModel pageDataModel = pageDataModelMapper.findPageDataModelById(pageInfo.getPageDataModelId());
		if(pageDataModel == null) {
			throw new RuntimeException(PageMsg.PAGEDATAMODEL_NOT_EXISTS);
		}
		String pageJsonData = pageDataModel.getPageJsonData();
		Map modelMap = JSON.parseObject(pageJsonData, Map.class);
		
		
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDefaultEncoding("utf-8");
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("template", template.getTemplate());
		configuration.setTemplateLoader(templateLoader);
		
		freemarker.template.Template t = configuration.getTemplate("template");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, modelMap);
		GenerateHtmlResult result = new GenerateHtmlResult();
		result.setHtml(html);
		result.setPageInfo(pageInfo);
		return result;
	}

	@Transactional
	@NotNull(methodParamIndexs={0})
	public Result createPageHtml(PageHtml pageHtml) {
		PageInfo pageInfo = pageInfoMapper.findByPageId(pageHtml.getPageId());
		if(pageInfo == null) {
			throw new RuntimeException(PageMsg.PAGE_NOT_EXISTS);
		}
		
		int siteId = pageInfo.getSiteId();
		Site site = siteMapper.findSiteById(siteId);
		if(site == null) {
			throw new RuntimeException(PageMsg.SITE_NOT_EXISTS);
		}
		
		//insert PageHtml to database
		PageHtml ph = pageHtmlMapper.findByPageId(pageHtml.getPageId());
		if(ph != null) {
			pageHtmlMapper.deleteByPageId(pageHtml.getPageId());
		}
		pageHtml.setCreateTime(new Date());
		pageHtmlMapper.insertPageHtml(pageHtml);
		
		//update PageInfo preViewUrl 
		String projectRequestRootAddress = pageInfo.getProjectRequestRootAddress();
		int length = projectRequestRootAddress.length();
		String preViewPageAddress = pageInfo.getPreViewPageAddress();
		String substring = preViewPageAddress.substring(0, length);
		String previewUrl = site.getSiteType() + "://" + site.getHostName() + "/" + substring + pageInfo.getPageName() + ".html";
		pageInfoMapper.updatePageInfo(pageInfo);
		
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

	public void generateHtmlTextToPath(PageInfo pageInfo, String html) {
		FileOutputStream output = null;
		try {
			//use io wite string to html text file
			String filePath = pageInfo.getPreViewPageAddress() + pageInfo.getPageName() + ".html";
			File file = new File(filePath);
			if(file.exists()) {
				file.delete();
				file = new File(filePath);
			}
			output = new FileOutputStream(file);
			IOUtils.write(html.getBytes(), output);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
			}
		}
	}

	public Result publishPage(int pageId) {
		//check page exists
		PageInfo pageInfo = pageInfoMapper.findByPageId(pageId);
		if(pageInfo == null) {
			return new Result(PageCode.PAGE_NOT_EXISTS,PageMsg.PAGE_NOT_EXISTS);
		}
		
		//update page http request url
		String pageAddress = pageInfo.getPageAddress();
		int length = projectRequestRootAddress.length();
		String substring = pageAddress.substring(0, length);
		
		Site site = siteMapper.findSiteById(pageInfo.getSiteId());
		String hostName = site.getHostName();
		String siteType = site.getSiteType();
		String requestUrl = siteType + "://" + hostName + "/" + substring + pageInfo.getPageName() + ".html";
		
		pageInfo.setUrl(requestUrl);
		pageInfo.setUpdateTime(new Date());
		pageInfoMapper.updatePageInfo(pageInfo);
		
		//generate page html txt to pageAddress
		singleThreadExecutor.submit(new Runnable(){
			@Override
			public void run() {
				PageHtml pageHtml = pageHtmlMapper.findByPageId(pageId);
				generateHtmlTextToPath(pageInfo,pageHtml.getHtml());
			}
		});
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

}
