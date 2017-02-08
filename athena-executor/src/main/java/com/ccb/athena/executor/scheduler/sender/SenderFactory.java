package com.ccb.athena.executor.scheduler.sender;

import com.ccb.athena.executor.scheduler.manager.SecurityManager;
import com.ccb.athena.executor.scheduler.manager.DefaultSecurityManager;
import com.ccb.athena.executor.scheduler.message.MessageParser;
import com.ccb.athena.executor.scheduler.message.p6.P6MessageParser;
import com.ccb.athena.executor.scheduler.protocol.Protocol;
import com.ccb.athena.executor.scheduler.protocol.http.HttpStreamProtocol;

public class SenderFactory {

	public static Sender createDefault(String chanel, String secEnv, String secNode) {

		SecurityManager secManager = new DefaultSecurityManager(secEnv, secNode);// 安全管理
		String respCodePath = "//TX/TX_HEADER/SYS_RESP_CODE";
		String respDescPath = "//TX/TX_HEADER/SYS_RESP_DESC";
		String successStatus = "000000000000";
		String messageBeforeAppend = "";
		String messageAfterAppend = "";
		MessageParser messageParser = new P6MessageParser(respCodePath, respDescPath, successStatus, messageBeforeAppend, messageAfterAppend);

		Protocol protocol = new HttpStreamProtocol();
		String sendEncoding = "utf-8";
		String rcvEncoding = "utf-8";

		Sender sender = new DefaultSender(messageParser, secManager, protocol, sendEncoding, rcvEncoding, false);

		return sender;
	}

}
