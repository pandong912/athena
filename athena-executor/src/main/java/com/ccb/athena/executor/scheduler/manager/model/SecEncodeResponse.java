package com.ccb.athena.executor.scheduler.manager.model;

import com.ccb.athena.executor.scheduler.manager.SecData;
import com.ccb.athena.executor.scheduler.manager.SecResult;

public class SecEncodeResponse {

	private SecData secData;// 解密后的报文
	private SecResult result;

	public SecData getSecData() {
		return secData;
	}

	public void setSecData(SecData secData) {
		this.secData = secData;
	}

	public SecResult getResult() {
		return result;
	}

	public void setResult(SecResult result) {
		this.result = result;
	}

}
