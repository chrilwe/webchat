package com.wechat.register.im.server.decoder;

import java.util.List;

import com.wechat.register.im.server.protocol.ImProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
 * 自定义解码器
 * @author chrilwe
 *
 */
public class ImDecoder extends ReplayingDecoder<Void> {

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int len = in.readInt();
		byte[] b = new byte[len];
		in.readBytes(b);
		
		ImProtocol protocol = new ImProtocol();
		protocol.setLen(len);
		protocol.setMessage(b);
		
		out.add(protocol);
	}

}
