package com.wechat.im.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 * @author Kim
 *
 */
public class MyLongEncoder extends MessageToByteEncoder<Long> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
		System.out.println("encode long :   " + msg);
		out.writeLong(msg);
	}

}
