package com.wechat.master.im.balancer;

import java.util.List;

/**
 * 选择连接的服务器
 * @author chrilwe
 *
 */
public interface IRuler {
	public String choose(List<String> serverAddress);
}
