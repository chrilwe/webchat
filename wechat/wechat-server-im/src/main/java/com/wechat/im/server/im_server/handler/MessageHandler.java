package com.wechat.im.server.im_server.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.wechat.base.model.im.ImMessageModel;
import com.wechat.im.server.common.model.ImProperties;
import com.wechat.im.server.im_server.Server;
import com.wechat.im.server.im_server.common.MessageType;
import com.wechat.im.server.im_server.common.model.Session;
import com.wechat.im.server.service.AuthService;
import com.wechat.im.server.service.JedisPoolOption;
import com.wechat.im.server.service.impl.DefaultAuthServiceImpl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * @author chrilwe
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	//保存认证成功channel
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	//保存认证没有成功的channel
	private static ChannelGroup unAuthChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	//保存客户端连接信息
	private static Map<String, Session> clientConnectionDetail = new HashMap<String, Session>();
	private static final String CLIENT_CONNECT_MSG = "CLIENT_CONNECT_MSG";
	
	/**
	 * read event
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext chc, TextWebSocketFrame message) throws Exception {
		//获取配置信息
		ImProperties imProperties = (ImProperties) Server.singletonMap.get("imProperties");
		
		String text = message.text();
		ImMessageModel msg = null;
		try {
			msg = JSON.parseObject(text, ImMessageModel.class);
		} catch (Exception e) {
			chc.writeAndFlush(new TextWebSocketFrame("非法请求内容"));
			chc.close();
			return;
		}
		String accessToken = msg.getAccessToken();
		String m = msg.getMessage();
		int senderId = msg.getSenderId();
		int receiverId = msg.getReceiverId();
		System.out.println("服务端接收客户端消息: " + msg.toString());
		
		if(StringUtils.isEmpty(accessToken)) {
			chc.close();
		}
		
		//判断用户是否已经连接到netty服务器
		//Session session = clientConnectionDetail.get(senderId+"");
		Session session = JedisPoolOption.getInstance().opsForHash_get(CLIENT_CONNECT_MSG, senderId+"", Session.class);
		if(session != null) {
			ChannelId channelId = session.getChannelId();
			Channel channel = channelGroup.find(channelId);
			String messageType = msg.getMessageType();
			if(messageType.equals(MessageType.CHAT)) {
				// 读取消息，将消息发送到目标客户端
				Session s = clientConnectionDetail.get(receiverId+"");
				// 目标客户端未登录, 保存消息
				if(s == null) {
					System.out.println("客户端"+ receiverId + "未登录,消息保存,等待客户端登录接收");
				} 
				else {
					ChannelId channelId2 = s.getChannelId();
					Channel channel2 = channelGroup.find(channelId2);
					channel2.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
				}
			} else if(messageType.equals(MessageType.CONNECT)) {
				chc.writeAndFlush(new TextWebSocketFrame("已经连接到聊天室，不必再次连接"));
			} else {
				chc.writeAndFlush(new TextWebSocketFrame("消息类型有误"));
			}
		} 
		//没有连接，首先需要登录认证，认证通过才能构建连接
		else {
			// 认证
			AuthService authService = null;
			try {
				authService = AuthServiceBuilder.getInstance().build(DefaultAuthServiceImpl.class);
			} catch (Exception e) {
				chc.close();
			}
			boolean auth = authService.auth(msg);
			if(auth == false) {
				chc.writeAndFlush(new TextWebSocketFrame("请登录"));
				chc.close();
				return;
			}
			
			//保存客户端连接信息
			Channel c = chc.channel();
			ChannelId id = c.id();
			channelGroup.add(c);
			
			Session s = new Session();
			s.setChannelId(chc.channel().id());
			s.setClientId(senderId+"");
			s.setConnectServerHost(imProperties.getWebsocketserver());
			clientConnectionDetail.put(senderId+"", s);
			JedisPoolOption.getInstance().hset(CLIENT_CONNECT_MSG, senderId+"", JSON.toJSONString(s));
		}
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		unAuthChannelGroup.add(ctx.channel());
	}


	/**
	 * exception event
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 出现未知异常，关闭连接
		super.exceptionCaught(ctx, cause);
	}


	/**
	 * 客户端与服务端断开连接, 将连接从map移除
	 */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//从channelGroup 移除channel
		ChannelId id = ctx.channel().id();
		channelGroup.remove(ctx.channel());
		
		//从clientConnectionDetail 移除session
		Set<Entry<String, Session>> entrySet = clientConnectionDetail.entrySet();
		for (Entry<String, Session> entry : entrySet) {
			String key = entry.getKey();
			Session session = entry.getValue();
			ChannelId channelId = session.getChannelId();
			if(channelId == id) {
				clientConnectionDetail.remove(key);
				System.out.println("客户端id:" +session.getClientId()+"断开连接");
				break;
			}
		}
	}
	
	
}
