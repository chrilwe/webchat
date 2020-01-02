package com.wechat.base.api;

import java.util.List;

import com.wechat.base.model.im.ImServerDetail;

/**
 * 注册中心im
 * @author chrilwe
 *
 */
public interface WechatImRegisterApi {
	/**
	 * 获取注册服务器列表
	 */
	public List<ImServerDetail> loadServerDetail();
}
