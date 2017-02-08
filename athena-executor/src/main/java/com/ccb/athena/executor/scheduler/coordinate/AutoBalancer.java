package com.ccb.athena.executor.scheduler.coordinate;

import com.ccb.athena.executor.scheduler.jdbc.zookeeper.ZookeeperConnector;

public class AutoBalancer {

	public static void main(String[] args) throws InterruptedException {
		ZookeeperConnector connector = new ZookeeperConnector();
		connector.connect("127.0.0.1", 2181, "ltr");
		BalanceCache cache = new BalanceCache(connector);
		cache.start();
		Thread.sleep(Long.MAX_VALUE);
	}
}
