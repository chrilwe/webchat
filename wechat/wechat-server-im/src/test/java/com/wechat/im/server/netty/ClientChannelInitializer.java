package com.wechat.im.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class ClientChannelInitializer extends ChannelInitializer {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//pipeline.addLast("myLongDecoder",new MyLongDecoder());
		//pipeline.addLast("myLongEncoder", new MyLongEncoder());
		pipeline.addLast("myDecoder", new MyDecoder());
		pipeline.addLast("myEncoder", new MyEncoder());
		//pipeline.addLast(new ClientHandler());
		pipeline.addLast("myClientHandler", new MyClientHandler());
	}


}
