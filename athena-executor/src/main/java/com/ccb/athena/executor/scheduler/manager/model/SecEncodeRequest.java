package com.ccb.athena.executor.scheduler.manager.model;

public class SecEncodeRequest {
	
	private String secNode;// 目标安全节点
	private byte[] message;// 加密或者解密的报文
	private boolean isSign;// 是否签名
	
	private String traceId;//交易的流水号

	public String getSecNode() {
		return secNode;
	}

	public void setSecNode(String secNode) {
		this.secNode = secNode;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	
}
