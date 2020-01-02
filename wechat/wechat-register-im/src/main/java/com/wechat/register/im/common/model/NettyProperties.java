package com.wechat.register.im.common.model;

import lombok.Data;
import lombok.ToString;

/**
 * netty配置参数
 * @author chrilwe
 *
 */
@Data
@ToString
public class NettyProperties {
	//服务端绑定端口
	private int serverPort;
	//zookeeper的地址
	private String zkServersAddress;
}
