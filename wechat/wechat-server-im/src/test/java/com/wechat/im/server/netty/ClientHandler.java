package com.wechat.im.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Long> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(5201314l);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("from server msg :" + msg);
	}
 
}
