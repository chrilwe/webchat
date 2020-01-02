package com.wechat.im.server.netty;

import lombok.Data;
import lombok.ToString;

/**
 * 自定义协议
 * @author chrilwe
 *
 */
@Data
@ToString
public class MyProtocol {
	//消息长度
	private int length;
	//消息内容
	private byte[] content;
}
