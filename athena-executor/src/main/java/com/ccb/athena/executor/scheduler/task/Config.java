package com.ccb.athena.executor.scheduler.task;

/**
 *  
 * @author xuxq
 * 
 * 发送时所需字段
 *
 */
public class Config {

	private boolean isSec;
	
	private String address;
	
	private String secEnv;
	
	private String secNode;

	public boolean isSec() {
		return isSec;
	}

	public void setSec(boolean isSec) {
		this.isSec = isSec;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSecEnv() {
		return secEnv;
	}

	public void setSecEnv(String secEnv) {
		this.secEnv = secEnv;
	}

	public String getSecNode() {
		return secNode;
	}

	public void setSecNode(String secNode) {
		this.secNode = secNode;
	}
}
