package com.ccb.athena.executor.scheduler.manager.model;

import com.ccb.athena.executor.scheduler.manager.SecData;

public class SecDecodeRequest {

	private String secNode;// 目标安全节点
	private SecData secData;
	private boolean isVerify;// 是否验签
	private String traceId;//交易流水号

	public String getSecNode() {
		return secNode;
	}

	public void setSecNode(String secNode) {
		this.secNode = secNode;
	}

	public SecData getSecData() {
		return secData;
	}

	public void setSecData(SecData secData) {
		this.secData = secData;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	

}
