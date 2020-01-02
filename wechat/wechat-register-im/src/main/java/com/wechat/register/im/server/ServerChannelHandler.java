package com.wechat.register.im.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wechat.base.model.im.ImServerDetail;
import com.wechat.register.im.server.protocol.ImProtocol;
import com.wechat.register.im.zk.ZkClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerChannelHandler extends SimpleChannelInboundHandler<ImProtocol> {
	
	/**
	 * 临时存放发现的服务信息列表(单机版)
	 */
	public static Map<String,ImServerDetail> serverDetail = new HashMap<String,ImServerDetail>();
	
	public static final String SERVER_REGISTER = "/SERVER_REGISTER/";
	
	/**
	 * 客户端连接成功，发送注册信息,做出应答
	 */
	protected void channelRead0(ChannelHandlerContext ctx, ImProtocol msg) throws Exception {
		byte[] message = msg.getMessage();
		ImServerDetail imServerDetail = JSON.parseObject(new String(message), ImServerDetail.class);
		
		//从本地内存中查询服务是否存在，存在修改时间，不存在添加
		ImServerDetail imServerDetail2 = serverDetail.get(imServerDetail.getServerAddress());
		if(imServerDetail2 != null) {
			imServerDetail2.setLastHeartBeatTime(new Date());
		} else {
			serverDetail.put(imServerDetail.getServerAddress(), imServerDetail);
			//将服务列表注册到zk
			String path = SERVER_REGISTER + imServerDetail.getClientId();
			ZkClient.createNode(path, imServerDetail.getServerAddress().getBytes());
		}
		System.out.println("服务端信息: " + JSON.toJSONString(imServerDetail));
		
		//返回响应应答
		imServerDetail.setLastHeartBeatTime(new Date());
		String m = JSON.toJSONString(imServerDetail);
		
		ImProtocol p = new ImProtocol();
		p.setLen(m.getBytes().length);
		p.setMessage(m.getBytes());
		ctx.writeAndFlush(p);
	}

}
