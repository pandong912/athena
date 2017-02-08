package com.ccb.athena.executor.scheduler.coordinate;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import com.ccb.athena.executor.scheduler.jdbc.zookeeper.ZookeeperConnector;

public class BalanceCache {

	private ZookeeperConnector connector;

	private PathChildrenCache taskOperator;

	private PathChildrenCache threadOperator;

	public BalanceCache(ZookeeperConnector connector) {
		super();
		this.connector = connector;
		taskOperator = connector.getOperator("/task");
		threadOperator = connector.getOperator("/thread");
	}

	public void start() {
		try {
			taskOperator.getListenable().addListener(new PathChildrenCacheListener() {

				@Override
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
					switch (event.getType()) {
					case CHILD_ADDED:
					case CHILD_REMOVED:
						makeBalance();
						break;

					default:
						break;
					}
				}
			});
			threadOperator.getListenable().addListener(new PathChildrenCacheListener() {

				@Override
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
					switch (event.getType()) {
					case CHILD_ADDED:
					case CHILD_REMOVED:
						makeBalance();
						break;

					default:
						break;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeBalance() {
		try {
			List<ChildData> tasks = taskOperator.getCurrentData();
			List<ChildData> threads = threadOperator.getCurrentData();

			int taskIndex = 0;
			for (int i = 0; i < threads.size(); i++) {
				String threadPath = threads.get(i).getPath();
				byte[] taskInfo = tasks.get(taskIndex).getData();
				System.out.println(threadPath + "------" + new String(taskInfo));
				connector.getClient().setData().forPath(threadPath, taskInfo);
				if (++taskIndex > tasks.size() - 1) {
					taskIndex = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
