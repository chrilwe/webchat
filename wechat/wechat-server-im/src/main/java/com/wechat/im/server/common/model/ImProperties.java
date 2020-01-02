package com.wechat.im.server.common.model;

import lombok.Data;
import lombok.ToString;

/**
 * properties
 * @author chrilwe
 *
 */
@Data
@ToString
public class ImProperties {
	//服务端口
	private int port;
	//服务端websocket连接
	private String websocketserver;
	//通过nginx和网关代理负载均衡以后的认证服务器地址
	private String authServiceUrls;
}
