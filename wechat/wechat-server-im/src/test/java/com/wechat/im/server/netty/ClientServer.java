package com.wechat.im.server.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
 * 客户端
 * @author chrilwe
 *
 */
public class ClientServer {
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());
			
			ChannelFuture connect = bootstrap.connect("localhost", 52013);
			ChannelFuture channelFuture = connect.sync();
			ChannelFuture sync = channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
