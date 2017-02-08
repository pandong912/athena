package com.ccb.athena.executor.scheduler.common;

import com.ibm.as400.access.AS400Text;

public class EbcdicUtil {
	
	/**
	 * ascii字符串转换ebcdic编码
	 * @param message
	 * @return
	 */
	public static byte[] ascii2ebcdic(String message){
		byte data[] = null;
		//1388标识对中文的编码
		int length = 0;
		int messageLen = message.length();
		char pre = 'a';
		for (int i = 0; i < messageLen; i++) {
			char c = message.charAt(i);
			if(pre>=128){//上一个字符是中文
				if(c>128){
					if(i==(messageLen-1)){
						length = length +3;
					}
					else{
						length = length +2;
					}
					
				}
				else {
					length = length +2;
				}
			}
			else{//上一个字符是英文
				if(c>128){
					length = length +3;
				}
				else {
					length++;
				}
			}
			pre = c;
			
			if(i==(messageLen-1)){
				if(pre>=128){
					length = length + 1;
				}
			}
		}
		 AS400Text convert = new AS400Text(length, 1388);
		 data = convert.toBytes(message);
		return data;
	}
	
	public static String ebcdic2ascii(byte[] data){
		String message = "";
		if(data==null||data.length==0){
			return message;
		}
		//1388标识对中文的编码
		 AS400Text convert = new AS400Text(data.length, 1388);
		 message = (String) convert.toObject(data);
	    return message;
	
	} 

}
