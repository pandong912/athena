package com.ccb.athena.executor.framework.consumer.thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.executor.framework.Constants;
import com.ccb.athena.executor.framework.consumer.Config;
import com.ccb.athena.executor.framework.consumer.task.impl.AbstractExeTask;

public class ConsumerThread implements Runnable {
	private static final Logger logger = LogManager.getLogger();
	// 线程状态
	private String threadStatus;
	// notify、wait
	private Object lock = new Byte((byte) 1);
	//
	private String ip;
	// zookeeper中的节点名 ip_线程id，该值能够唯一标识一个线程
	private String zkThreadNodeName;
	NodeCache nodeCache = null;

	// zookeeper中表示线程的节点的值{taskName:xx,taskType:send|save,pullKey:xx}
	private volatile JSONObject threadNodeValue;
	private JSONObject nodeValue;
	// 正在处理中的对象。该对象是从threadNodeValue克隆过来的。
	private JSONObject dealingThreadNodeValue;
	// dealingThreadNodeValue中包含的节点信息
	private JSONObject dealingNodeValue = null;
	private int threadNo = 0;

	// 线程处理的任务数量
	// private HashMap<String,Integer> tasNums= new HashMap<String,Integer>();

	private Config cfg;

	private CuratorFramework cf;

	private JedisPool rdPool;
	private Jedis rd;

	// 任务处理实现类
	private HashMap<String, AbstractExeTask> doTaskMap;

	public ConsumerThread(int num, Config config, CuratorFramework curatorFramework, JedisPool redisPool, HashMap<String, AbstractExeTask> taskMap) {
		this.threadNo = num;
		this.cfg = config;
		this.ip = cfg.getIp();
		this.cf = curatorFramework;
		this.rdPool = redisPool;
		this.doTaskMap = taskMap;
	}

	@Override
	public void run() {
		init();

		List<String> msgList = null;
		String msg = null;
		long dumyNum = 0;
		// 任务处理完后返回的消息
		String taskMessage = null;

		while (true) {
			// 如果通过外部程序设置了stop，结束线程
			if (threadStatus.equals(Constants.THREAD_STATUS_STOP)) {
				// 删除zk节点
				try {
					cf.delete().forPath(zkThreadNodeName);
					logger.warn("外部程序设置线程STOP:" + zkThreadNodeName);
				} catch (Exception e) {
					logger.error("Exception", e);
				}
				try {
					nodeCache.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
				return;
			}
			// 可以在此处加一个暂停功能，暂停需要一个对象锁，用于控制线程暂停后继续执行

			while (true) {
				synchronized (lock) {
					// 获取队列名
					if (threadNodeValue == null) {
						try {
							threadStatus(Constants.THREAD_STATUS_PAUSE);
							lock.wait();
							threadStatus(Constants.THREAD_STATUS_RUNNINIG);
						} catch (InterruptedException e) {
							logger.error("InterruptedException", e);
						}
					}
					nodeValue = threadNodeValue.getJSONObject(Constants.TASK_NODE_KEY);
					if (null == nodeValue) {
						// if ((null == nodeValue.getString(Constants.TASK_NAME_KEY)) || ("null".equals(nodeValue.getString(Constants.TASK_NAME_KEY)))){
						try {
							threadStatus(Constants.THREAD_STATUS_PAUSE);
							lock.wait();
							threadStatus(Constants.THREAD_STATUS_RUNNINIG);
						} catch (InterruptedException e) {
							logger.error("InterruptedException", e);
						}
					} else {
						dealingThreadNodeValue = threadNodeValue;
						dealingNodeValue = nodeValue;
						break;
					}
				}
			}

			// 从队列中获取消息
			msgList = null;
			msg = null;
			dumyNum = 0;
			taskMessage = null;

			try {
				rd = rdPool.getResource();
				msgList = rd.brpop(60, dealingNodeValue.getString(Constants.TASK_PULL_KEY));
				if (msgList.size() == 0) {
					logger.warn("超时:" + dealingNodeValue.getString(Constants.TASK_PULL_KEY) + "暂无数据");
				} else {
					msg = msgList.get(1);
					if (msg != null) {
						if (Constants.FLAG_MESSAGE.equals(msg)) {
							// 删除任务节点
							try {
								// 等待足够的时间
								LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(doTaskMap.get(dealingNodeValue.getString(Constants.TASK_TYPE_KEY)).getWaitTime()));
								// 删除节点
								cf.delete().forPath(cfg.getTaskPath() + dealingNodeValue.getString(Constants.TASK_NAME_KEY));
								logger.warn("删除节点:" + cfg.getTaskPath() + dealingNodeValue.getString(Constants.TASK_NAME_KEY));

								switch (doTaskMap.get(dealingNodeValue.getString(Constants.TASK_TYPE_KEY)).getNextStepFlag()) {
								case HasNextStep:
									// 将{}加入到下一个任务队列
									rd.lpush(dealingNodeValue.getString(Constants.TASK_PUSH_KEY), Constants.FLAG_MESSAGE);
									logger.warn(dealingNodeValue.getString(Constants.TASK_PUSH_KEY) + Constants.FLAG_MESSAGE);
									break;
								case NoNextStep:
									break;
								}
							} catch (InterruptedException e) {
								outputStatusError(msg);
								logger.error("InterruptedException", e);
							} catch (KeeperException e) {
								outputStatusError(msg);
								logger.error("KeeperException", e);
							}
						} else {
							// 执行任务
							taskMessage = doTaskMap.get(dealingNodeValue.getString(Constants.TASK_TYPE_KEY)).process(msg, dealingThreadNodeValue.getString(Constants.TASK_INFO_KEY));
							switch (doTaskMap.get(dealingNodeValue.getString(Constants.TASK_TYPE_KEY)).getNextStepFlag()) {
							// TODO 连接断开会怎么样？
							case HasNextStep:
								// 将新消息放到下一个队列
								rd.lpush(dealingNodeValue.getString(Constants.TASK_PUSH_KEY), taskMessage);
								// 生产任务数量+1
								dumyNum = rd.incr(dealingNodeValue.getString(Constants.TASK_PUSH_KEY) + Constants.TASK_COUNT_PRODUCER_KEYNAME_SUFFIX);
								outputStatus(dealingNodeValue.getString(Constants.TASK_PUSH_KEY) + Constants.TASK_COUNT_PRODUCER_KEYNAME_SUFFIX + ":" + dumyNum);
								break;
							case NoNextStep:
								break;
							}
							// 消费者数量+1
							dumyNum = rd.incr(dealingNodeValue.getString(Constants.TASK_PULL_KEY) + Constants.TASK_COUNT_CONSUMER_KEYNAME_SUFFIX);
							outputStatus(dealingNodeValue.getString(Constants.TASK_PULL_KEY) + Constants.TASK_COUNT_CONSUMER_KEYNAME_SUFFIX + ":" + dumyNum);
						}
					} else {
						outputStatus(dealingNodeValue.getString(Constants.TASK_PULL_KEY) + "队列中无数据");
					}
				}
			} catch (Exception e) {
				if (null != msg) {
					outputStatusError(dealingNodeValue.toString() + msg);
				}
				logger.error("Exception", e);
			} finally {
				closeRedis(rd);
			}
		}
	}

	public void init() {
		threadStatus = Constants.THREAD_STATUS_START; // 线程开始
		threadStatus = Constants.THREAD_STATUS_PAUSE; // 线程暂停

		initZNodeOfThread();

		cfg.getConsumerThreads().put(zkThreadNodeName, this);
		cfg.getConsumerThreadStatus().put(zkThreadNodeName, threadStatus);
		cfg.getConsumerThreadLocks().put(zkThreadNodeName, lock);
	}

	/**
	 * 初始化zookeeper中的线程节点
	 */
	public void initZNodeOfThread() {
		zkThreadNodeName = ip + "_" + threadNo;
		logger.debug(zkThreadNodeName);

		// 确保节点的唯一性
		try {
			cf.delete().forPath(cfg.getThreadPath() + zkThreadNodeName);
		} catch (KeeperException.NoNodeException e) {
			logger.debug("删除时不存在该节点");
		} catch (Exception e) {
			logger.debug("Exception", e);
		}

		// 如果是临时节点，连接超时后不能自动创建节点。
		try {
			zkThreadNodeName = cf.create().withMode(CreateMode.EPHEMERAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath(cfg.getThreadPath() + zkThreadNodeName, Constants.FLAG_MESSAGE.getBytes());
			Thread.currentThread().setName(zkThreadNodeName);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		// 添加监视
		nodeCache = new NodeCache(cf, zkThreadNodeName);
		try {
			nodeCache.start();
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		addListener(nodeCache);
	}

	/**
	 * 在节点上添加监视
	 * 
	 * @param cache
	 */
	private void addListener(NodeCache cache) {
		NodeCacheListener listener = new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				String zkNodeValue = null;
				zkNodeValue = new String(cf.getData().forPath(zkThreadNodeName), "UTF-8");
				logger.debug(zkThreadNodeName + " nodeChanged:" + zkNodeValue);
				// 获取队列名
				synchronized (lock) {
					threadNodeValue = JSONObject.parseObject(zkNodeValue);
					lock.notify();
				}

			}
		};
		cache.getListenable().addListener(listener);
	}

	public void closeRedis(Jedis redis) {
		if (null != redis) {
			try {
				redis.close();
			} catch (Exception e) {
				logger.error("RedisError", e);
			} finally {
				redis = null;
			}
		}
	}

	public String getThreadStatus() {
		return threadStatus;
	}

	public void setThreadStatus(String threadStatus) {
		this.threadStatus = threadStatus;
		cfg.getConsumerThreadStatus().put(zkThreadNodeName, threadStatus);
	}

	public String getZkName() {
		return zkThreadNodeName;
	}

	public void setZkName(String zkName) {
		this.zkThreadNodeName = zkName;
	}

	private void threadStatus(String status) {
		setThreadStatus(status);
		logger.info("线程:" + Thread.currentThread().getId() + ",状态:" + threadStatus);
	}

	private void outputStatus(String msg) {
		logger.info("线程:" + Thread.currentThread().getId() + ",状态:" + threadStatus + "," + dealingNodeValue.toString() + ",附加内容:" + msg);
	}

	private void outputStatusError(String msg) {
		logger.error("线程:" + Thread.currentThread().getId() + ",状态:" + threadStatus + "," + dealingNodeValue.toString() + ",附加内容:" + msg);
	}

}