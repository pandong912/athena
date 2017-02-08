package com.ccb.athena.executor.scheduler.counter;

import redis.clients.jedis.Jedis;

public class RedisCounter implements Counter {
	
	private Jedis jedis;
	
	public RedisCounter(String host, int port) {
		super();
		this.jedis = new Jedis(host, port);
		jedis.connect();
	}

	public void increase(String key) {
		jedis.incr(key);
	}

	public void decrease(String key) {
		jedis.decr(key);
	}
	
	public int sum(String key) {
		return Integer.parseInt(jedis.get(key));
	}

	public void close() {
		jedis.close();
	}

}
