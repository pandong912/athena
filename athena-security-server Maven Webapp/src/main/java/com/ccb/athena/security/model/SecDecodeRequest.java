package com.ccb.athena.security.model;

public class SecDecodeRequest {

	private SecData secData;
	private boolean isVerify;// 是否验签

	private String traceId;// 交易流水号

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
