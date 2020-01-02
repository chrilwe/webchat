package com.wechat.master.im.balancer.impl;

import java.util.List;

import com.wechat.master.im.balancer.IRuler;
/**
 * 随机算法
 * @author chrilwe
 *
 */
public class RandomRule implements IRuler {

	@Override
	public String choose(List<String> serverAddress) {
		if(serverAddress == null || serverAddress.size() <= 0) {
			throw new RuntimeException("没有可用的服务器");
		}
		return null;
	}

}
