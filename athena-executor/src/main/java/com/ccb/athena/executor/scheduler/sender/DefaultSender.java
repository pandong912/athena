package com.ccb.athena.executor.scheduler.sender;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.ccb.athena.executor.scheduler.action.AutoCase;
import com.ccb.athena.executor.scheduler.common.ByteUtil;
import com.ccb.athena.executor.scheduler.manager.SecData;
import com.ccb.athena.executor.scheduler.manager.SecurityManager;
import com.ccb.athena.executor.scheduler.message.MessageParser;
import com.ccb.athena.executor.scheduler.message.MessageParserException;
import com.ccb.athena.executor.scheduler.message.RespData;
import com.ccb.athena.executor.scheduler.protocol.Protocol;

public class DefaultSender extends Sender {

	public DefaultSender(MessageParser messageParser, SecurityManager securityManager, Protocol protocol, String sndEncoding, String rcvEncoding, boolean checkRule) {
		super(messageParser, securityManager, protocol, sndEncoding, rcvEncoding, checkRule);
	}

	public SenderResult send(AutoCase autoCase) throws SenderException {
		SenderResult senderResult = new SenderResult();
		StringBuilder executeLog = new StringBuilder();
		String reqMessage = autoCase.getReqMessage();
		String execpMessage = autoCase.getResMessage();
		String resMessage = "";
		String traceId = autoCase.getTraceId();
		String txCode = autoCase.getTxCode();
		String txType = autoCase.getTxType();
		String address = autoCase.getAddress();
		boolean isSec = autoCase.isSec();
		boolean isSign = autoCase.isSign();
		boolean isVerify = autoCase.isVerify();
		String caseProperties = autoCase.getCaseProperties();
		// 组装发送的报文
		byte[] sendMessage = null;// 最终需要发送的报文
		byte packMessage[] = null;// 记录组装后的但没有加密的报文
		try {
			packMessage = messageParser.packMessage(reqMessage, txCode);// 组装报文
		} catch (MessageParserException e) {
			throw new SenderException("组装报文出现异常:" + e.getLocalizedMessage(), executeLog.toString());
		}

		if (isSec) {// 对报文进行加密
			/*
			 * try { SecData secData = securityManager.encode(packMessage, traceId, isSign); sendMessage = messageParser.packMessage(secData,txCode,txType,traceId); } catch (SecurityException e) { throw new SenderException("报文加密出现异常("+e.getCode()+":"+e.getMessage()+")",executeLog.toString()); } catch (MessageParserException e) { e.printStackTrace(); }
			 */
		} else {
			sendMessage = packMessage;
		}
		byte[] resultMessage = null;// 记录目标系统返回的原始报文
		try {
			// 开始发送报文
			// resultMessage = protocol.send(sendMessage,txCode, address);
			resultMessage = FileUtils.readFileToByteArray(new File("E:/A02818451_recv.dat"));
			System.out.println(ByteUtil.toHexStringWithSpace(resultMessage));
		} catch (Exception e) {
			throw new SenderException("向目标地址[" + address + "]发送报文出现异常:" + e.getLocalizedMessage(), executeLog.toString());
		}
		byte[] decodeMessage = null;// 记录解密后的报文
		if (isSec) {// 解密返回的报文
			SecData secData = null;
			try {
				secData = messageParser.getSecData(resultMessage);
				decodeMessage = secData.getSecEnc();
				System.out.println("decode:" + ByteUtil.toHexStringWithSpace(decodeMessage));
			} catch (MessageParserException e1) {
				e1.printStackTrace();
			}
			/*
			 * try { decodeMessage = securityManager.decode(secData, traceId, isVerify); } catch (SecurityException e) { throw new SenderException("报文解密出现异常("+e.getCode()+":"+e.getMessage()+")",executeLog.toString()); }
			 */
		} else {
			decodeMessage = resultMessage;
		}
		try {
			// 解析返回的报文
			resMessage = messageParser.parseMessage(decodeMessage, execpMessage, txCode);
		} catch (MessageParserException e) {
			throw new SenderException("解析返回的报文出现异常:" + e.getLocalizedMessage(), executeLog.toString());
		}

		RespData respData = null;
		try {
			respData = messageParser.getRespData(resMessage);
		} catch (MessageParserException e) {
			e.printStackTrace();
		}// 获取响应报文的公共字段
		String resultStatus = "";
		if (checkRule) {// 校验规则
			try {
				boolean isCheck = messageParser.checkResp(respData.getRespCode(), caseProperties);
				if (isCheck) {
					resultStatus = SenderStatus.SUCCESS;
				} else {
					resultStatus = SenderStatus.FAIL;
				}
			} catch (MessageParserException e) {
				e.printStackTrace();
			}

		}
		senderResult.setRespCode(respData.getRespCode());
		senderResult.setRespDesc(respData.getRespDesc());
		senderResult.setRespMessage(resMessage);
		senderResult.setStatus(resultStatus);
		senderResult.setLog(executeLog.toString());
		return senderResult;
	}

}
