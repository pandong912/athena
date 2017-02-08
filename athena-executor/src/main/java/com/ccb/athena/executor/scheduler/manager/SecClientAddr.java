package com.ccb.athena.executor.scheduler.manager;

import java.util.HashMap;
import java.util.Map;

public class SecClientAddr {

	private static Map<String, String> encodAddrMap = null;

	private static Map<String, String> decodeAddrMap = null;

	public static String getEncodeAddr(String secEnv) {
		String clientAddr = "";
		if (encodAddrMap == null) {
			initAddr();
		}
		clientAddr = encodAddrMap.get(secEnv);
		if (clientAddr == null || clientAddr.trim().length() == 0) {
			initAddr();
			clientAddr = encodAddrMap.get(secEnv);
		}
		return clientAddr;
	}

	public static String getDecodeAddr(String secEnv) {
		String clientAddr = "";
		if (decodeAddrMap == null) {
			initAddr();
		}
		clientAddr = decodeAddrMap.get(secEnv);
		if (clientAddr == null || clientAddr.trim().length() == 0) {
			initAddr();
			clientAddr = decodeAddrMap.get(secEnv);
		}
		return clientAddr;
	}

	public static void initAddr() {
		encodAddrMap = new HashMap<String, String>();
		decodeAddrMap = new HashMap<String, String>();
	}

}
