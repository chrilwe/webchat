 package com.wechat.im.server.netty;

import com.wechat.im.server.common.model.ImProperties;
import com.wechat.im.server.im_server.handler.MessageHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerChannelInitializer extends ChannelInitializer {

	@Override
	protected void initChannel(Channel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		//pipeline.addLast("myLongDecoder",new MyLongDecoder());
		//pipeline.addLast("myLongEncoder", new MyLongEncoder());
		pipeline.addLast("myDecoder", new MyDecoder());
		pipeline.addLast("myEncoder", new MyEncoder());
		//pipeline.addLast(new ServerHandler());
		pipeline.addLast(new MyServerHandler());
	}

}
