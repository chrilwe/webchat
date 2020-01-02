package com.wechat.im.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
		byte[] content = msg.getContent();
		String message = new String(content);
		System.out.println("接收来自于服务端消息: " + message);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i = 0;i < 11;i++) {
			String message = "hello";
			byte[] m = message.getBytes();
			MyProtocol protocol = new MyProtocol();
			protocol.setLength(m.length);
			protocol.setContent(m);
			ctx.writeAndFlush(protocol);
		}
	}

}
