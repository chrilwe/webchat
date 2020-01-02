package com.wechat.im.server.im_server.common.model;

import java.io.Serializable;

import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.ToString;

/**
 * 保存客户端连接详情
 * @author chhrilwe
 *
 */
@Data
@ToString
public class Session implements Serializable {
	//客户端id
	private String clientId;
	//连接到服务器地址
	private String connectServerHost;
	//连接通道id
	private ChannelId channelId;
}
