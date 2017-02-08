package com.ccb.athena.executor.scheduler.sender;

import com.ccb.athena.executor.scheduler.action.AutoCase;
import com.ccb.athena.executor.scheduler.manager.SecurityManager;
import com.ccb.athena.executor.scheduler.message.MessageParser;
import com.ccb.athena.executor.scheduler.protocol.Protocol;

public abstract class Sender {

	protected String sndEncoding;

	protected String rcvEncoding;

	protected MessageParser messageParser;
	protected SecurityManager securityManager;// 安全manager
	protected Protocol protocol;// 发送的协议，socket,http
	protected boolean checkRule;

	public Sender(MessageParser messageParser, SecurityManager securityManager, Protocol protocol, String sndEncoding, String rcvEncoding, boolean checkRule) {
		this.messageParser = messageParser;
		this.securityManager = securityManager;
		this.protocol = protocol;
		this.sndEncoding = sndEncoding;
		this.rcvEncoding = rcvEncoding;
		this.checkRule = checkRule;
	}

	public abstract SenderResult send(AutoCase autoCase) throws SenderException;

}
