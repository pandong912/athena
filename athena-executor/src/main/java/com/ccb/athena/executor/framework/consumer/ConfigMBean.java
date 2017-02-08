package com.ccb.athena.executor.framework.consumer;

public interface ConfigMBean {
	public int getThreadNum();
	public void setThreadNum(int threadNum);
	
	public String getIp();
	public void setIp(String ip);
	
	public String getThreadStatus(String threadName);
	public void setThreadStatus(String threadName,String status);
}
