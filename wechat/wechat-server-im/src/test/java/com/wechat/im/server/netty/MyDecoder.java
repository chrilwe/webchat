package com.wechat.im.server.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
 * 自定义编码
 * @author chrilwe
 *
 */
public class MyDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int length = in.readInt();
		byte[] content = new byte[length];
		in.readBytes(content);
		
		MyProtocol myProtocol = new MyProtocol();
		myProtocol.setLength(length);
		myProtocol.setContent(content);
		
		out.add(myProtocol);
	}

}
