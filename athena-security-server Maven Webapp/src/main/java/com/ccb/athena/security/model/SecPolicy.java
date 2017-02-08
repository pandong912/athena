package com.ccb.athena.security.model;

public class SecPolicy {

	private boolean isEnc;
	private boolean isMac;
	private boolean isContext;
	
	public SecPolicy(byte[] policy) {
		isEnc = policy[0] == 49;
		isMac = policy[1] == 49;
		isContext = policy[2] == 49;
	}

	public boolean isEnc() {
		return isEnc;
	}

	public void setEnc(boolean isEnc) {
		this.isEnc = isEnc;
	}

	public boolean isMac() {
		return isMac;
	}

	public void setMac(boolean isMac) {
		this.isMac = isMac;
	}

	public boolean isContext() {
		return isContext;
	}

	public void setContext(boolean isContext) {
		this.isContext = isContext;
	}

}
