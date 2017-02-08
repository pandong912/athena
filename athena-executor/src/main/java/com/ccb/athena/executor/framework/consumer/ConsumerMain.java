package com.ccb.athena.executor.framework.consumer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.KeeperException;

import redis.clients.jedis.JedisPool;

import com.ccb.athena.executor.framework.consumer.task.impl.AbstractExeTask;
import com.ccb.athena.executor.framework.consumer.thread.ConsumerThread;

/**
 * 用于启动线程，监视zk线程节点
 * (1)init()
 * (2)start()开始运行
 * @author flytoice
 *
 */
public class ConsumerMain{
	private static final Logger logger = LogManager.getLogger();
	private Config cfg = new Config();
	
	private CuratorFramework curatorFramework;
	
	private JedisPool redisPool;
	
	private int threadNum;
	private String proRoot;
	
	//任务处理类
	private HashMap<String,AbstractExeTask> taskMap;
	
	/**
	 * 初始化
	 */
	public void init() throws IOException, KeeperException, InterruptedException{
		//获取本机IP，用于生成zookeeper中的线程对应的节点名
		String machineIp;
		try {
			machineIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("获取机器IP时发生异常");
			e.printStackTrace(System.out);
			return;
		}
		
		//初始化配置
		cfg.setThreadNum(threadNum);
		cfg.setIp(machineIp);
		cfg.setProRootPath(proRoot);
		cfg.init(threadNum);	//根据线程数实例化关于线程的多个map
	}

	public void start(){
		logger.info("准备连接到zookeeper");
		try {
			curatorFramework.blockUntilConnected();
			curatorFramework
				.getConnectionStateListenable()
				.addListener(new ConnectionStateListener(){
					public void stateChanged(CuratorFramework client,
							ConnectionState newState) {
						if (ConnectionState.RECONNECTED.equals(newState)){
							//重新连接后，需要初始化线程节点
							for (ConsumerThread t :cfg.getConsumerThreads().values()){
								t.initZNodeOfThread();
							}
						}
					}
				});
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
		logger.info("连接到zookeeper");
		
		Thread t;
		ConsumerThread ct;
		for(int i=1;i<=cfg.getThreadNum();i++){
			ct = new ConsumerThread(i,cfg,curatorFramework,redisPool,taskMap);
			t = new Thread(ct);
			logger.info("线程启动");
			t.start();
		}
	}

	
	public Config getConfig(){
		return this.cfg;
	}

	public JedisPool getRedisPool() {
		return redisPool;
	}

	public void setRedisPool(JedisPool redisPool) {
		this.redisPool = redisPool;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public String getProRoot() {
		return proRoot;
	}

	public void setProRoot(String proRoot) {
		this.proRoot = proRoot;
	}

	public HashMap<String,AbstractExeTask> getTaskMap() {
		return taskMap;
	}

	public void setTaskMap(HashMap<String,AbstractExeTask> taskMap) {
		this.taskMap = taskMap;
	}
	
	public CuratorFramework getCuratorFramework() {
		return curatorFramework;
	}

	public void setCuratorFramework(CuratorFramework curatorFramework) {
		this.curatorFramework = curatorFramework;
	}
}
