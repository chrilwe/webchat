package com.wechat.base.common.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.wechat.base.model.user.User;

/**
 * get httpservletrequest and httpservletresponse
 * @author chrilwe
 *
 */
public class BaseController {
	
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	@ModelAttribute
	public void postConstr(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
}
