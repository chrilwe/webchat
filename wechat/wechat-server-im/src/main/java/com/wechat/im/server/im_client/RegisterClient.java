package com.wechat.im.server.im_client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 注册中心客户端
 * 
 * @author chrilwe
 *
 */
public class RegisterClient {

	public static void start() {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new RegisterClientInitializer());

			ChannelFuture connect = bootstrap.connect("localhost", 9000);
			ChannelFuture channelFuture = connect.sync();
			ChannelFuture sync = channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		RegisterClient.start();
	}
}
