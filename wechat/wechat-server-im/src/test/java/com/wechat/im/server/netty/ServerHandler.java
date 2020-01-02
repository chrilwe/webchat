package com.wechat.im.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Long> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("server receive message:   " +msg);
		ctx.writeAndFlush(msg);
	}

}
