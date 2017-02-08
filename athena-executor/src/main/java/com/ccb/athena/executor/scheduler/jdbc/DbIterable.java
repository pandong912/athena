package com.ccb.athena.executor.scheduler.jdbc;

public interface DbIterable<T> extends Iterable<T> {

	DbIterable<T> sort(String sort);
	
	DbIterable<T> limit(int num);
}
