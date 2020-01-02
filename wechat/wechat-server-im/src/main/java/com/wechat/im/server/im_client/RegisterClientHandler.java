package com.wechat.im.server.im_client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.wechat.base.model.im.ImServerDetail;
import com.wechat.im.server.common.protocol.ImProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author chrilwe
 *
 */
public class RegisterClientHandler extends SimpleChannelInboundHandler<ImProtocol> {
	
	/**
	 * 注册成功，得到服务端响应，在响应后的一段时间发送心跳
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ImProtocol msg) throws Exception {
		System.out.println("服务上报:  " + new String(msg.getMessage()));
		ImServerDetail imServerDetail = JSON.parseObject(msg.getMessage(), ImServerDetail.class);
		Date lastHeartBeatTime = imServerDetail.getLastHeartBeatTime();
		Thread.sleep(2000);
		
		imServerDetail.setLastHeartBeatTime(new Date());
		String message = JSON.toJSONString(imServerDetail);
		
		ImProtocol p = new ImProtocol();
		p.setLen(message.getBytes().length);
		p.setMessage(message.getBytes());
		ctx.writeAndFlush(p);
	}

	/**
	 * 连接到注册中心成功，发送客户端信息
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ImServerDetail isd = new ImServerDetail();
		isd.setClientId(UUID.randomUUID().toString());
		isd.setLastHeartBeatTime(new Date());
		isd.setServerAddress("ws://localhost:52013/ws");
		String message = JSON.toJSONString(isd);
		
		ImProtocol p = new ImProtocol();
		p.setLen(message.getBytes().length);
		p.setMessage(message.getBytes());
		ctx.writeAndFlush(p);
	}
	
	
}
