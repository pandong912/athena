package com.ccb.athena.executor.scheduler.jdbc;

public interface DbOperator<T> {

	T findOneBy(String id);
	
	DbIterable<T> findMany(String filter);
	
	void save(Object object);
}
