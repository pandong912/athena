package com.ccb.athena.executor.scheduler.ductsume;

import redis.clients.jedis.Jedis;

import com.ccb.athena.executor.scheduler.jdbc.redis.RedisConnector;

public class RedisProductor extends AbstractJsonProductor {
	
	private Jedis jedis;
	
	public RedisProductor(String topic, RedisConnector connector) {
		super(topic);
		this.jedis = connector.getOperator(null);
	}

	@Override
	void store(String topic, String message) {
		jedis.lpush(topic, message);
	}

}
