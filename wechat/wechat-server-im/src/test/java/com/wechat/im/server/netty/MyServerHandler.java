package com.wechat.im.server.netty;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {
	
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
		int length = msg.getLength();
		byte[] content = msg.getContent();
		
		String message = new String(content);
		System.out.println("服务端接收来自客户端的消息:" + message + "    " + ++count);
		
		MyProtocol protocol = new MyProtocol();
		byte[] res = UUID.randomUUID().toString().getBytes();
		protocol.setContent(res);
		protocol.setLength(res.length);
		ctx.writeAndFlush(protocol);
	}

}
