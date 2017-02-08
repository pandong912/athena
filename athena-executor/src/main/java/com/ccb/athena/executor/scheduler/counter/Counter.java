package com.ccb.athena.executor.scheduler.counter;

public interface Counter {

	void increase(String key);
	
	void decrease(String key);
	
	int sum(String key);
}
