package com.wechat.im.server.im_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;


import com.wechat.im.server.common.model.ImProperties;
import com.wechat.im.server.im_client.RegisterClient;
import com.wechat.im.server.service.JedisPoolOption;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty server
 * 
 * @author chrilwe
 *
 */
public class Server {
	
	//单例池
	public final static Map<String, Object> singletonMap = new HashMap<String, Object>();

	/**
	 * start server
	 */
	public void start() {
		// boossGroups处理连接事件
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// workGroup处理已连接的channelHandler事件
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ImProperties imProperties = this.parsePropertiesFile();
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class) // 用的是nio的方式来处理连接
					.childHandler(new ServerChannelInitializer()).option(ChannelOption.SO_BACKLOG, 20)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// 绑定端口
			ChannelFuture future = serverBootstrap.bind(imProperties.getPort()).sync();
			// 事件是异步处理的，要想知道事件完成,监听事件返回future判断是否事件处理成功
			Channel channel = future.channel();
			//服务端成功启动，连接到注册中心注册,并发送心跳
			boolean success = future.isSuccess();
			RegisterClient.start();
			//初始化redis连接
			JedisPoolOption.getInstance();
			// 等待关闭
			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();

		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	
	/**
	 * 解析properties文件
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private ImProperties parsePropertiesFile() throws Exception {
		//获取配置文件
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "config.properties");
		
		//解析
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		String serverPort = properties.getProperty("server.port");
		String websocketserver = (String) properties.get("websocketserver");
		String authServiceUrls = (String)properties.getProperty("authServiceUrls");
		
		//封装解析内容
		ImProperties imProperties = new ImProperties();
		imProperties.setPort(Integer.parseInt(serverPort));
		imProperties.setWebsocketserver(websocketserver);
		imProperties.setAuthServiceUrls(authServiceUrls);
		//将解析的properties放到单例池
		singletonMap.put("imProperties", imProperties);
		return imProperties;
	}
}
