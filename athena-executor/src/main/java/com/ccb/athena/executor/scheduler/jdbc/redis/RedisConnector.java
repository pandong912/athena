package com.ccb.athena.executor.scheduler.jdbc.redis;

import redis.clients.jedis.Jedis;

import com.ccb.athena.executor.scheduler.jdbc.DbConnector;

public class RedisConnector implements DbConnector<Jedis> {

	private Jedis jedis;
	
	public void connect(String host, int port, String database) {
		jedis = new Jedis(host, port);
	}

	public Jedis getOperator(String collection) {
		return jedis;
	}

	public void disconnect() {
		jedis.disconnect();
	}

}
