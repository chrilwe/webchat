package com.wechat.register.im.task;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.wechat.base.model.im.ImServerDetail;
import com.wechat.register.im.server.ServerChannelHandler;
import com.wechat.register.im.zk.ZkClient;

/**
 * 判断客户端是否存活
 * @author chrilwe
 *
 */
public class ClientIsAliveTask implements Runnable {
	
	/**
	 * 客户端发送心跳超时，认为该客户端服务已经宕机
	 */
	private long clientDieMillis = 30 * 1000;
	

	@Override
	public void run() {
		while(true) {
			long currentTimeMillis = System.currentTimeMillis();
			Map<String, ImServerDetail> serverDetail = ServerChannelHandler.serverDetail;
			System.out.println("判断服务是否宕机任务：" + JSON.toJSONString(serverDetail));
			for(Iterator iterator = serverDetail.entrySet().iterator();iterator.hasNext();) {
				String next = (String) iterator.next();
				ImServerDetail imServerDetail = serverDetail.get(next);
				Date lastHeartBeatTime = imServerDetail.getLastHeartBeatTime();
				if((currentTimeMillis - lastHeartBeatTime.getTime()) > clientDieMillis) {
					System.out.println("tcp服务器地址为: " + next + "已经宕机");
					ServerChannelHandler.serverDetail.remove(next);
					//从zk移除当前服务器节点
					ZkClient.deleteNode(ServerChannelHandler.SERVER_REGISTER + imServerDetail.getClientId());
				}
			}
		}
	}

}
