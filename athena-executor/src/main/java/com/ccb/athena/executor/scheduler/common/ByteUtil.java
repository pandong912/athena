package com.ccb.athena.executor.scheduler.common;

public class ByteUtil {
	
	public static String toHexStringWithSpace(byte[] data){
    	String hexStr = "";
    	if(data==null||data.length==0){
    		return "";
    	}
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < data.length; i++) {
    		String s = String.format("%02x ", data[i]);
    		sb.append(s.toUpperCase());
		}
    	hexStr = sb.toString();
    	return hexStr;
    }

}
