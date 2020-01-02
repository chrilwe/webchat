package com.wechat.im.server.common.protocol;

import lombok.Data;
import lombok.ToString;

/**
 * 自定义协议
 * @author chrilwe
 *
 */
@Data
@ToString
public class ImProtocol {
	//消息字节长度
	private int len;
	//消息字节码
	private byte[] message;
}
