package com.ccb.athena.executor.scheduler.message;

import com.ccb.athena.executor.scheduler.manager.SecData;


public interface MessageParser {
	
	/**
	 * 将原始的报文转换成最终需要发送的报文
	 * @param message 原始报文
	 * @return 組裝后的报文
	 */
	public byte [] packMessage(String message,String txCode) throws MessageParserException;
	
	/**
	 * 将安全报文头和报文体组装最终需要发送的报文
	 * @param  加密后的secData ,流水号,txCode,txType
	 * @return 組裝后的报文
	 */
	public byte [] packMessage(SecData seData,String traceId, String txCode, String txType)throws MessageParserException;
	
	
	/**
	 * 把目标系统返回的加密报文转换成SecData
	 * @param message
	 * @return SecData 对象
	 * @throws MessageParserException 
	 */
	public SecData getSecData(byte [] message) throws MessageParserException;
	
	
	/**
	 * 解析目标系统返回的报文
	 * @param message 目标系统实际返回的报文
	 * resXml  期望返回的报文
	 * @return 方便页面展示的报文
	 */
	public String parseMessage(byte[] message,String execpXml,String txCode)throws MessageParserException;
	
	
	/**
	 * 取出响应报文中一些字段，封装成RespData对象
	 * @param message
	 * @return RespData 对象
	 */
	public RespData getRespData(String message)throws MessageParserException;
	
	/**
	 * 根据响应码判断案例是否成
	 * @param respCode
	 * @return
	 */
	public boolean checkResp(String respCode,String caseProperties)throws MessageParserException;

}
