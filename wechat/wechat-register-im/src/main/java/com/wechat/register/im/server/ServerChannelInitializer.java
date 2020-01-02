package com.wechat.register.im.server;

import com.wechat.register.im.server.decoder.ImDecoder;
import com.wechat.register.im.server.encoder.ImEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class ServerChannelInitializer extends ChannelInitializer {

	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ImDecoder());
		pipeline.addLast(new ImEncoder());
		pipeline.addLast(new ServerChannelHandler());
	}

}
