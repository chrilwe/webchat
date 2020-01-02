package com.wechat.im.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 * @author chrilwe
 *
 */
public class MyEncoder extends MessageToByteEncoder<MyProtocol> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getLength());
		out.writeBytes(msg.getContent());
	}

}
