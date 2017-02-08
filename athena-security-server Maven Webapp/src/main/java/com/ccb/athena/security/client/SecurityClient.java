package com.ccb.athena.security.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;

import com.ccb.athena.security.SecCode;
import com.ccb.athena.security.SecurityException;
import com.ccb.athena.security.SecurityParam;
import com.ccb.athena.security.model.SecData;
import com.ccb.athena.security.model.SecDecodeRequest;
import com.ccb.athena.security.model.SecEncodeRequest;
import com.ccb.athena.security.model.SecPolicy;

public class SecurityClient {

	private static final Logger log = LogManager.getLogger(SecurityClient.class);
	
	public static Map<String, SecPolicy> securityPolicies = new ConcurrentHashMap<String, SecPolicy>();
	/**
	 * 加密报文
	 * 
	 * @param encodeRequest
	 */
	public static SecData encrypt(SecEncodeRequest encodeRequest) throws SecurityException {
		byte message[] = encodeRequest.getMessage();// 加密前的报文
		String traceId = encodeRequest.getTraceId();
		boolean isSign = encodeRequest.isSign();// 是否需要签名
		
		String localSecNode = SecurityParam.LOCAL_SEC_NODE;		
		String remoteSecNode = encodeRequest.getSecNode();// 远程安全节点
		
		SecData secData = new SecData();
		if (isSign) {// 报文签名
			try {
				secData.setSign(true);
				secData.setSecSign(SecAPI.genSign(localSecNode, message));
			} catch (Exception e) {
				throw new SecurityException(SecCode.SIGN_FAIL, SecAPI.getError(12));
			}
		}
		
		SecPolicy securityPolicy = securityPolicies.get(remoteSecNode);
		if (securityPolicy == null) {
			try {
				byte[] policy = SecAPI.getSecPolicy(localSecNode, remoteSecNode);
				securityPolicy = new SecPolicy(policy);
				
				securityPolicies.put(remoteSecNode, securityPolicy);
			} catch (SecException e) {
				throw new SecurityException(SecCode.GET_SEC_POLICY_FAIL, SecAPI.getError(12));
			}
		}

		if (securityPolicy.isEnc()) {
			try {
				secData.setSecIsEnc(1);
				secData.setSign(isSign);
				secData.setSecEnc(SecAPI.pkgEncrypt(localSecNode, remoteSecNode, message)); // enc加密
			} catch (SecException e) {
				throw new SecurityException(SecCode.ENC_ENCODE_FAIL, SecAPI.getError(12));
			}
		} else {
			secData.setSecIsEnc(0);
			secData.setSecEnc(message);
		}

		if (securityPolicy.isMac()) {
			try {
				secData.setSecIsMac(1);
				secData.setSecMac(SecAPI.mac(localSecNode, remoteSecNode, message)); // 获取mac
			} catch (SecException e) {
				throw new SecurityException(SecCode.MAC_ENCODE_FAIL, SecAPI.getError(12));
			}
		} else {
			secData.setSecIsMac(0);
		}

		if (securityPolicy.isContext()) {
			try {
				secData.setSecIsContext(1);
				secData.setSecContext(SecAPI.initSecContext(localSecNode, remoteSecNode, traceId, SecurityParam.USER));
			} catch (SecException e) {
				throw new SecurityException(SecCode.CONTEXT_ENCODE_FAIL, SecAPI.getError(12));
			}
		} else {
			secData.setSecIsContext(0);
		}

		secData.setLocalSecNode(localSecNode);
		secData.setRemoteSecNode(remoteSecNode);		
		secData.setSecErrorCode(SecAPI.getError(12));
		
		return secData;
	}

	/**
	 * 解密报文
	 * 
	 * @param request
	 * @param response
	 */
	public static byte[] decrypt(SecDecodeRequest decodeRequest) throws SecurityException {
		SecData secData = decodeRequest.getSecData();
		String traceId = decodeRequest.getTraceId();
		boolean isVerify = decodeRequest.isVerify();//是否需要验签
		
		String localSecNode = secData.getLocalSecNode();
		String remoteSecNode = secData.getRemoteSecNode();// 远程安全节点
		
		if (secData.getSecIsContext() == 1) {// 新标准不校验context
			try {
				SecAPI.checkSecContext(remoteSecNode, localSecNode, traceId, secData.getSecContext());
			} catch (SecException e) {
				throw new SecurityException(SecCode.CONTEXT_DECODE_FAIL, SecAPI.getError(12));
			}
		}

		if (secData.getSecIsMac() == 1) {
			try {
				SecAPI.macVerify(remoteSecNode, localSecNode, secData.getSecEnc(), secData.getSecMac());
			} catch (SecException e) {
				throw new SecurityException(SecCode.MAC_DECODE_FAIL, SecAPI.getError(12));
			}
		}

		byte[] message = null;
		if (secData.getSecIsEnc() == 1) {
			try {
				message = SecAPI.pkgDecrypt(remoteSecNode, localSecNode, secData.getSecEnc());
			} catch (SecException e) {
				throw new SecurityException(SecCode.ENC_DECODE_FAIL, SecAPI.getError(12));
			}
		} else {
			message = secData.getSecEnc();
		}

		if (isVerify) {
			try {
				SecAPI.signVerify(remoteSecNode, message, secData.getSecSign());
			} catch (SecException e) {
				throw new SecurityException(SecCode.VERFY_FAIL, SecAPI.getError(12));
			}
		}
		
		return message;
	}

}
