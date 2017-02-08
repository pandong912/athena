package com.ccb.athena.executor.scheduler.jdbc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.ccb.athena.executor.scheduler.jdbc.DbConnector;

public class ZookeeperConnector implements DbConnector<PathChildrenCache> {

	private CuratorFramework client;

	@Override
	public void connect(String host, int port, String database) {
		this.client = CuratorFrameworkFactory.builder().connectString(host + ":" + port).sessionTimeoutMs(6000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace(database).build();
		this.client.start();
	}

	@Override
	public PathChildrenCache getOperator(String collection) {
		try {
			PathChildrenCache cache = new PathChildrenCache(client, collection, true);
			cache.start();
			return cache;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void disconnect() {
		client.close();
	}

	public CuratorFramework getClient() {
		return client;
	}

}
