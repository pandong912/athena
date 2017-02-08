package com.ccb.athena.executor.scheduler.jdbc;

public interface DbConnector<T> {

	void connect(String host, int port, String database);
	
	T getOperator(String collection);
	
	void disconnect();
}
