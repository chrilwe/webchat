package com.wechat.register.im.server.encoder;

import com.wechat.register.im.server.protocol.ImProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 * @author chrilwe
 *
 */
public class ImEncoder extends MessageToByteEncoder<ImProtocol> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ImProtocol msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getLen());
		out.writeBytes(msg.getMessage());
	}

}
