package com.wechat.im.server.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.UUID;

/**
 * nio server
 * @author chrilwe
 *
 */
public class NioServer {
	
	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(8081));
		
		//register channel to selector and like op_accept status
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			int select = selector.select();
			System.out.println("selectKey number:"+ select);
			// get selectorkeys from selector
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			for (SelectionKey selectionKey : selectedKeys) {
				// handle accept event
				if(selectionKey.isAcceptable()) {
					ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
					SocketChannel accept = channel.accept();
					accept.configureBlocking(false);
					
					String clientId = UUID.randomUUID().toString();
					System.out.println("客户端"+clientId+"已连接");
					//register socketChannel to selector
					accept.register(selector, SelectionKey.OP_READ);
				} 
				//handle read  event
				else if(selectionKey.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
					// read data into buffer from channel
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					while(socketChannel.read(readBuffer) > 0) {
						readBuffer.flip();
						byte[] array = readBuffer.array();
						System.out.println("来自于客户端的消息："+new String(array));
					}
				}
			}
			selectedKeys.clear();
		}
	}
}
