package com.wechat.base.api;

import com.wechat.base.common.response.Result;

/**
 * IM通讯master节点，负责认证，暴露真正的im服务器接口
 * @author chrilwe
 *
 */
public interface WechatImMasterApi {
	/**
	 * 申请tcp连接服务器真实地址,并且当前只有完成认证之后才能进行
	 * @return Result返回认证连接地址
	 */
	public Result applayServerConnectAddress();
	
	
}
