package com.ccb.athena.security;

public class SecurityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	public SecurityException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
