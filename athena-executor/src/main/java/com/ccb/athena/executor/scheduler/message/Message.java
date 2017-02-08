package com.ccb.athena.executor.scheduler.message;

public class Message {
	
	private byte[] secHeader;
	
	private byte[] messageBody;

	public byte[] getSecHeader() {
		return secHeader;
	}

	public void setSecHeader(byte[] secHeader) {
		this.secHeader = secHeader;
	}

	public byte[] getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(byte[] messageBody) {
		this.messageBody = messageBody;
	}
	
	
	

}
