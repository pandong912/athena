package com.ccb.athena.executor.scheduler.task;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import com.alibaba.fastjson.JSONObject;

public class TaskKeeper {

	private CuratorFramework client;
	
	private String basePath;

	public TaskKeeper(String hostPort, String basePath) {
		super();
		this.basePath = basePath;
		this.client = CuratorFrameworkFactory.builder()
				.connectString(hostPort)
				.sessionTimeoutMs(6000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
	}
	
	public String register(String type, String name, String pushKey) throws Exception {
		String nodePath = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(basePath + '/' + name + '-' + type + '-');
		String nodeName = nodePath.substring(nodePath.lastIndexOf('/') + 1);
		JSONObject json = new JSONObject();
		json.put("taskType", type);
		json.put("redisKey", nodeName);
		if (pushKey != null)
			json.put("pushKey", pushKey);
		client.setData().forPath(nodePath, json.toJSONString().getBytes());
		return nodeName;
	}
	
	public void close() {
		client.close();
	}
	
}
