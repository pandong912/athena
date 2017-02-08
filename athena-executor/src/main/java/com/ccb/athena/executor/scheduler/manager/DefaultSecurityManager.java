package com.ccb.athena.executor.scheduler.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.ccb.athena.executor.scheduler.common.HttpClient;
import com.ccb.athena.executor.scheduler.manager.model.SecDecodeRequest;
import com.ccb.athena.executor.scheduler.manager.model.SecDecodeResponse;
import com.ccb.athena.executor.scheduler.manager.model.SecEncodeRequest;
import com.ccb.athena.executor.scheduler.manager.model.SecEncodeResponse;

public class DefaultSecurityManager implements SecurityManager {

	private String encodeURL;
	private String decodeURL;

	private String secNode;

	public DefaultSecurityManager(String secEnv, String secNode) {

		this.encodeURL = SecClientAddr.getEncodeAddr(secEnv);

		this.decodeURL = SecClientAddr.getDecodeAddr(secEnv);

		this.secNode = secNode;

		this.encodeURL = "http://128.192.154.165:7080/sec/SecEncode";
		this.decodeURL = "http://128.192.154.165:7080/sec/SecDecode";
	}

	@Override
	public SecData encode(byte[] message, String traceId) throws SecurityException {
		return encode(message, traceId, false);
	}

	@Override
	public SecData encode(byte[] message, String traceId, boolean isSign) throws SecurityException {
		SecEncodeRequest request = new SecEncodeRequest();
		request.setMessage(message);
		request.setSecNode(secNode);
		request.setSign(isSign);
		request.setTraceId(traceId);
		String requestJson = JSON.toJSONString(request);

		// 对发送的数据进行URLDecode编码
		try {
			requestJson = URLEncoder.encode(requestJson, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String responseJson = HttpClient.sendHttp(requestJson, encodeURL);

		// 对收到的数据进行URLDecode 解码

		try {
			responseJson = URLDecoder.decode(responseJson, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		SecEncodeResponse response = JSON.parseObject(responseJson, SecEncodeResponse.class);

		SecResult result = response.getResult();

		if (!result.getCode().equals(SecCode.SUCCESS)) {
			throw new SecurityException(result.getCode(), result.getMessage());
		}
		return response.getSecData();
	}

	@Override
	public byte[] decode(SecData secData, String traceId) throws SecurityException {
		return decode(secData, traceId, false);
	}

	@Override
	public byte[] decode(SecData secData, String traceId, boolean isVerify) throws SecurityException {
		SecDecodeRequest request = new SecDecodeRequest();
		request.setSecData(secData);
		request.setSecNode(secNode);
		request.setVerify(isVerify);
		request.setTraceId(traceId);

		// 对发送的数据进行URLDecode编码

		String requestJson = JSON.toJSONString(request);
		try {
			requestJson = URLEncoder.encode(requestJson, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String responseJson = HttpClient.sendHttp(requestJson, decodeURL);
		// 对收到的数据进行URLDecode 解码
		try {
			responseJson = URLDecoder.decode(responseJson, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SecDecodeResponse response = JSON.parseObject(responseJson, SecDecodeResponse.class);

		SecResult result = response.getResult();

		if (!result.getCode().equals(SecCode.SUCCESS)) {
			throw new SecurityException(result.getCode(), result.getMessage());
		}

		return response.getMessage();
	}

}
