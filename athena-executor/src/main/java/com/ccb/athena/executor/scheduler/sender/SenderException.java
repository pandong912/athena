package com.ccb.athena.executor.scheduler.sender;

public class SenderException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private String log ;//执行日志
	
	public SenderException(String message,String log){
	  this.message = message;	
	  this.log = log;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getLog(){
		return log;
	}

}
