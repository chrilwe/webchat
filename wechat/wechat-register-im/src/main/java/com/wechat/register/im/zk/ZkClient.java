package com.wechat.register.im.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.wechat.register.im.server.RegisterServer;

/**
 * zk客户端
 * @author chrilwe
 *
 */
public class ZkClient {
	
	private CountDownLatch cdl = new CountDownLatch(1);
	
	private static ZooKeeper zooKeeper;
	
	private ZkClient() {
		try {
			zooKeeper = new ZooKeeper(RegisterServer.p.getZkServersAddress(), 60 * 1000, new ConnectWatcher());
			System.out.println("connecting.........");
			cdl.await();
			System.out.println("zk客户端连接到服务端成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	/**
	 * 连接watcher
	 */
	private class ConnectWatcher implements Watcher {
		@Override
		public void process(WatchedEvent event) {
			KeeperState state = event.getState();
			if(state == KeeperState.SyncConnected) {
				cdl.countDown();
			}
		}	
	}
	
	/**
	 * 静态内部类
	 */
	private static class Singleton {
		private static ZkClient zkClient = new ZkClient();
		
		private static ZkClient getInstance() {
			return zkClient;
		}
	}
	
	/**
	 * 获取实例
	 */
	public static ZkClient getInstance() {
		return Singleton.getInstance();
	}
	
	/**
	 * 添加或者修改数据
	 * @param path
	 * @param data
	 */
	public static void addData(String path, byte[] data) {
		//判断节点是否存在, 存在设置当前节点下的数据, 不存在创建节点
		Stat stat = null;
		try {
			stat = checkNodeExists(path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改节点数据失败");
		}
		if(stat == null) {
			createNode(path, data);
		} else {
			setDataNode(path, data, stat.getVersion());
		}
	}
	
	/**
	 * 创建节点
	 */
	public static void createNode(String path, byte[] data) {
		try {
			zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除节点
	 * @param path
	 */
	public static void deleteNode(String path) {
		//检查当前节点是否存在
		Stat stat = checkNodeExists(path);
		if(stat != null) {
			try {
				zooKeeper.delete(path, stat.getVersion());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取节点数据
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public static byte[] getData(String path) throws KeeperException, InterruptedException {
		return zooKeeper.getData(path, false, null);
	}
	/**
	 * 设置节点数据
	 */
	public static void setDataNode(String path, byte[] data, int version) {
		try {
			zooKeeper.setData(path, data, version);
		} catch (KeeperException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查节点是否已经存在
	 * @return
	 */
	public static Stat checkNodeExists(String path) {
		try {
			Stat stat = zooKeeper.exists(path, true);
			return stat;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("检查节点存在发生未知异常");
		}
	}
	
	/**
	 * 获取当前节点下的所有子节点
	 * @param path
	 */
	public static List<String> getChrildrenNode(String path) throws Exception {
		return zooKeeper.getChildren(path, false);	
	}
	
	/**
	 * 获取当前父节点下所有子节点的data(当前只有2级目录结构)
	 * @param parentPath
	 * @return
	 */
	public static List<byte[]> getAllData(String parentPath) throws Exception {
		List<byte[]> list = new ArrayList<byte[]>();
		List<String> chrildrenNode = getChrildrenNode(parentPath);
		for (String string : chrildrenNode) {
			byte[] data = getData(parentPath + "/" + string);
			list.add(data);
		}
		return list;
	}
	
	/*public static void main(String[] args) throws Exception {
		ZkClient client = ZkClient.getInstance();
		//client.addData("/test3/test3", "hello1".getBytes());
		//client.addData("/test3/hello", "hello2".getBytes());
		//client.getChrildrenNode("/test3");
		try {
			byte[] data = ZkClient.getData("/test3/hello");
			System.out.println(new String(data));
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//client.deleteNode("/test");
	}*/
	
}
