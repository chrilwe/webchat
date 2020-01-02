package com.wechat.im.server.im_client;

import com.wechat.im.server.common.decoder.ImDecoder;
import com.wechat.im.server.common.enocder.ImEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class RegisterClientInitializer extends ChannelInitializer {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new ImDecoder());
		pipeline.addLast("encoder", new ImEncoder());
		pipeline.addLast("handler", new RegisterClientHandler());
	}

}
