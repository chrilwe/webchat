package com.wechat.register.im.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.google.api.client.json.Json;
import com.wechat.register.im.common.model.NettyProperties;
import com.wechat.register.im.task.ClientIsAliveTask;
import com.wechat.register.im.zk.ZkClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 注册中心服务器
 * @author chrilwe
 *
 */
public class RegisterServer {
	
	public static NettyProperties p = new NettyProperties();
	
	/**
	 * 开启服务器
	 * @param port 服务器端口号
	 */
	public static void start(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					 .childHandler(new ServerChannelInitializer());
			// 绑定端口
			ChannelFuture future = bootstrap.bind(port).sync();
			// 事件是异步处理的，要想知道事件完成,监听事件返回future判断是否事件处理成功
			System.out.println(future.isSuccess());
			//获取与zookeeper连接，实例化单例zkclient
			ZkClient.getInstance();
			//启动成功，开始执行判断服务是否宕机任务
			if(future.isSuccess() == true) {
				new Thread(new ClientIsAliveTask());
			}
			Channel channel = future.channel();
			// 等待关闭
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	

	public static void parseProperties() throws Exception {
		RegisterServer registerServer = new RegisterServer();
		String path = registerServer.getClass().getClassLoader().getResource("").getPath();
		System.out.println(path);
		String propertiesPath = path + "netty.properties";
		System.out.println(propertiesPath);
		
		File file = new File(path);
		FileInputStream input = new FileInputStream(file);
		
		Properties properties = new Properties();
		properties.load(input);
		
		p.setServerPort(Integer.parseInt(properties.getProperty("server.port")));
		p.setZkServersAddress(properties.getProperty("zookeeper.zkServersAddress"));
	}
	
	public static void main(String[] args) throws Exception {
		//解析properties 文件
		parseProperties();
		
		//启动服务器
		RegisterServer.start(p.getServerPort());
	}
}
