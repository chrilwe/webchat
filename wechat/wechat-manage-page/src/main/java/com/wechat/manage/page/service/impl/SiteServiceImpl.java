package com.wechat.manage.page.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.base.common.code.Code;
import com.wechat.base.common.msg.Msg;
import com.wechat.base.common.response.Result;
import com.wechat.base.model.page.Site;
import com.wechat.manage.page.annotation.NotNull;
import com.wechat.manage.page.common.type.RequestType;
import com.wechat.manage.page.mapper.SiteMapper;
import com.wechat.manage.page.service.SiteService;
/**
 * 
 * @author chrilwe
 *
 */
@Service
public class SiteServiceImpl implements SiteService {
	
	@Autowired
	private SiteMapper siteMapper;

	@NotNull(methodParamIndexs={0})
	public Result createSite(Site site) {
		String hostName = site.getHostName();
		String siteType = site.getSiteType();
		if(StringUtils.isAnyEmpty(hostName,siteType)) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		if((!RequestType.HTTP.equals(siteType)) && (!RequestType.HTTPS.equals(siteType))) {
			return new Result(Code.REQUEST_PARAM_ERR,Msg.REQUEST_PARAM_ERR);
		}
		site.setCreateTime(new Date());
		siteMapper.insertSite(site);
		return new Result(Code.SUCCESS,Msg.SUCCESS);
	}

}
