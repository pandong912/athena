package com.ccb.athena.executor.scheduler.message;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public abstract class AbstractMessageParser implements MessageParser {

	protected String respCodePath;
	protected String respDescPath;
	protected String successStatus;
	protected String messageBeforeAppend;
	protected String messageAfterAppend;

	public AbstractMessageParser(String respCodePath, String respDescPath, String successStatus, String messageBeforeAppend, String messageAfterAppend) {
		this.respCodePath = respCodePath;
		this.respDescPath = respDescPath;
		this.successStatus = successStatus;
		this.messageBeforeAppend = messageBeforeAppend;
		this.messageAfterAppend = messageAfterAppend;
	}

	@Override
	public RespData getRespData(String message) {
		RespData respData = new RespData();
		try {
			Document doc = DocumentHelper.parseText(message);
			String respCode = "";
			String respDesc = "";
			Node respCodeNode = doc.selectSingleNode(respCodePath);
			if (respCodeNode != null) {
				respCode = respCodeNode.getText();
			}
			Node respDescNode = doc.selectSingleNode(respDescPath);
			if (respCodeNode != null) {
				respDesc = respDescNode.getText();
			}
			respData.setRespCode(respCode);
			respData.setRespDesc(respDesc);
		} catch (DocumentException e) {
			return respData;
		}
		return respData;
	}

	public boolean checkResp(String respCode, String caseProperties) {
		boolean result = false;
		if (caseProperties.equals(CaseProperties.POSITIVE_CASE)) {// 正案例
			if (respCode.equals(successStatus)) {
				result = true;
			} else {
				result = false;
			}
		} else if (caseProperties.equals(CaseProperties.POSITIVE_CASE)) {// 反案例

		}
		return result;
	}

	public String getRespCodePath() {
		return respCodePath;
	}

	public void setRespCodePath(String respCodePath) {
		this.respCodePath = respCodePath;
	}

	public String getRespDescPath() {
		return respDescPath;
	}

	public void setRespDescPath(String respDescPath) {
		this.respDescPath = respDescPath;
	}

}
