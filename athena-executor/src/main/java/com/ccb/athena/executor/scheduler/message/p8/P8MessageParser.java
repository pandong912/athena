package com.ccb.athena.executor.scheduler.message.p8;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.ccb.athena.executor.scheduler.common.BytePackage;
import com.ccb.athena.executor.scheduler.common.Constants;
import com.ccb.athena.executor.scheduler.manager.SecData;
import com.ccb.athena.executor.scheduler.message.AbstractMessageParser;

public class P8MessageParser extends AbstractMessageParser {

	private String encoding = "UTF-8";

	private static final String SEC_ERROR_CODE = "SEC_ERROR_CODE:";
	private static final String SEC_IS_MAC = "SEC_IS_MAC:";
	private static final String SEC_IS_CONTEXT = "SEC_IS_CONTEXT:";
	private static final String SEC_IS_ENC = "SEC_IS_ENC:";
	private static final String SEC_MAC = "SEC_MAC:";
	private static final String SEC_CONTEXT = "SEC_CONTEXT:";
	private static final String SEC_ID1 = "SEC_ID1:";
	private static final String SEC_ID2 = "SEC_ID2:";
	private static final String SEC_TRACE_ID = "SEC_TRACE_ID:";
	private static final String SEC_TX_CODE = "SEC_TX_CODE:";
	private static final String SEC_TX_TYPE = "SEC_TX_TYPE:";
	private static final String SEC_SIGN = "SEC_SIGN:";
	private static final String SEC_LEN = "SEC_LEN:";

	private static final String splitStr = Constants.LINE_SEPARATOR + Constants.LINE_SEPARATOR;

	public P8MessageParser(String respCodePath, String respDescPath, String successStatus, String messageBeforeAppend, String messageAfterAppend)  {
		super(respCodePath, respDescPath, successStatus, messageBeforeAppend, messageAfterAppend);
	}

//	public byte[] packMessage(String message) {
//		return message.getBytes(encoding);
//	}

	public byte[] packMessage(SecData secData, String traceId, String txCode, String txType) {
		StringBuilder sb = new StringBuilder();
		sb.append(SEC_ERROR_CODE).append(secData.getSecErrorCode()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_IS_MAC).append(secData.getSecIsMac()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_IS_CONTEXT).append(secData.getSecIsContext()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_IS_ENC).append(secData.getSecIsEnc()).append(Constants.LINE_SEPARATOR);
		try {
			sb.append(SEC_MAC).append(new String(secData.getSecMac(), encoding)).append(Constants.LINE_SEPARATOR);
		} catch (UnsupportedEncodingException e) {
			
		}
		sb.append(SEC_CONTEXT).append(secData.getSecContext()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_ID1).append(secData.getLocalSecNode()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_ID2).append(secData.getRemoteSecNode()).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_TRACE_ID).append(traceId).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_TX_CODE).append(txCode).append(Constants.LINE_SEPARATOR);
		sb.append(SEC_TX_TYPE).append(txType).append(Constants.LINE_SEPARATOR);
		if (secData.isSign()) {
			try {
				sb.append(SEC_SIGN).append(new String(secData.getSecSign(), encoding)).append(Constants.LINE_SEPARATOR);
			} catch (UnsupportedEncodingException e) {
				
			}
		}
		sb.append(SEC_LEN).append(String.format("%010d", secData.getSecEnc().length)).append(Constants.LINE_SEPARATOR);
		sb.append(Constants.LINE_SEPARATOR);

		BytePackage pack = new BytePackage();
		try {
			pack.append(sb.toString().getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
		}
		pack.append(secData.getSecEnc());

		return pack.getBytes();
	}

	public String parseMessage(byte[] message,String execpXml,String txCode) {
		String resultMessage = "";
		try {
			resultMessage = new String(message, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultMessage;
	}

	@Override
	public SecData getSecData(byte[] message) {
		String strMessage = "";
		try {
			strMessage = new String(message, encoding);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String header = StringUtils.substringBefore(strMessage, splitStr);
		String body = StringUtils.substringAfter(strMessage, splitStr);

		StringReader reader =null;
			reader = new StringReader(header);
		
		Properties pro = new Properties();
		try {
			pro.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SecData data = new SecData();

		String secErrorCode = pro.getProperty("SEC_ERROR_CODE");
		if (secErrorCode != null && secErrorCode.trim().length() > 0) {
			data.setSecErrorCode(secErrorCode);
		}
		String secIsMac = pro.getProperty("SEC_IS_MAC");
		if (secIsMac != null && secIsMac.trim().length() > 0) {
			data.setSecIsMac(Integer.parseInt(secIsMac));
		}

		String secIsContext = pro.getProperty("SEC_IS_CONTEXT");
		if (secIsContext != null && secIsContext.trim().length() > 0) {
			data.setSecIsContext(Integer.parseInt(secIsContext));
		}

		String secIsEnc = pro.getProperty("SEC_IS_ENC");
		if (secIsEnc != null && secIsEnc.trim().length() > 0) {
			data.setSecIsContext(Integer.parseInt(secIsEnc));
		}

		String secMac = pro.getProperty("SEC_MAC");
		if (secMac != null && secMac.trim().length() > 0) {
			try {
				data.setSecMac(secMac.getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String secContext = pro.getProperty("SEC_CONTEXT");
		if (secContext != null && secContext.trim().length() > 0) {
			data.setSecContext(secContext);
		}

		String secId1 = pro.getProperty("SEC_ID1");
		if (secId1 != null && secId1.trim().length() > 0) {
			data.setLocalSecNode(secId1);
		}

		String secId2 = pro.getProperty("SEC_ID2");
		if (secId2 != null && secId2.trim().length() > 0) {
			data.setRemoteSecNode(secId2);
		}

		String secSign = pro.getProperty("SEC_SIGN");
		if (secSign != null && secSign.trim().length() > 0) {
			data.setSecSign(secSign.getBytes());
		}

		String secRespCode = pro.getProperty("SEC_RESP_CODE");
		if (secRespCode != null && secRespCode.trim().length() > 0) {
			data.setSecRespCode(secRespCode);
		}

		String secLen = pro.getProperty("SEC_LEN");
		if (secLen != null && secLen.trim().length() > 0) {
			data.setSecLength(secLen);
		}
        try {
			data.setSecEnc(body.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return data;

	}

	@Override
	public byte[] packMessage(String message,String txCode) {
		try {
			return message.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
