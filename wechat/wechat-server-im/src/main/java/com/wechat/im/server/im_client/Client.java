package com.wechat.im.server.im_client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.wechat.base.model.im.ImMessageModel;
import com.wechat.im.server.im_server.common.MessageType;

public class Client {
	
	public static void main(String[] args) throws Exception {
		WebSocketClient wsClient = new WebSocketClient(new URI("http://localhost:52013/ws")){
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
			
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onMessage(String msg) {
				System.out.println("收到来自于服务端的消息： " + msg);
			}

			public void onOpen(ServerHandshake arg0) {
				
			}
			
		};
		boolean connectBlocking = wsClient.connectBlocking();
		ImMessageModel model = new ImMessageModel();
		model.setAccessToken("123");
		model.setMessage("hello");
		model.setSenderId(2);
		model.setMessageType(MessageType.CONNECT);
		wsClient.send(JSON.toJSONString(model));
		
		/*Thread.sleep(2000);
		model.setAccessToken("123");
		model.setMessage("hello");
		model.setSenderId(2);
		model.setReceiverId(1);
		model.setMessageType(MessageType.CHAT);
		model.setMessage("hello");
		wsClient.send(JSON.toJSONString(model));*/
		
	}

}
