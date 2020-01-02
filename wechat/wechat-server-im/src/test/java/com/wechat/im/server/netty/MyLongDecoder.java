package com.wechat.im.server.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 自定义解码器
 * @author chrilwe
 *
 */
public class MyLongDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//将字节流buffer解码long
		long readLong = in.readLong();
		System.out.println("decode byte to long:  " + readLong);
		if(readLong >= 0) {
			out.add(readLong);
		}
	}

}
