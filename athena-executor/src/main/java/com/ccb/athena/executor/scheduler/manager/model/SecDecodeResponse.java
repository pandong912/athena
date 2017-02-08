package com.ccb.athena.executor.scheduler.manager.model;

import com.ccb.athena.executor.scheduler.manager.SecResult;

public class SecDecodeResponse {

	private byte[] message;// 解密后的报文
	private SecResult result;

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public SecResult getResult() {
		return result;
	}

	public void setResult(SecResult result) {
		this.result = result;
	}

}
