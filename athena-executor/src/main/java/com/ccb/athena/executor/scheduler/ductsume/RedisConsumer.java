package com.ccb.athena.executor.scheduler.ductsume;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import com.ccb.athena.executor.scheduler.jdbc.redis.RedisConnector;

public class RedisConsumer extends AbstractJsonConsumer {
	
	private Jedis jedis;
	
	public RedisConsumer(String topic, RedisConnector connector) {
		super(topic);
		this.jedis = connector.getOperator(null);
	}

	@Override
	String fetch(String topic) {
		try {
			List<String> brpop = jedis.brpop(60, topic);
			if (brpop != null && brpop.size() > 0) {
				return brpop.get(1);
			}
		} catch (JedisDataException e) {
			return null;
		}
		return null;
	}

}
