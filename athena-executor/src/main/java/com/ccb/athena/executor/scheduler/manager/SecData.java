package com.ccb.athena.executor.scheduler.manager;

public class SecData {
public static final String SUCCESS = "000000000000";
	
	private String secErrorCode;
	private String localSecNode;
	private String remoteSecNode;
	
	private int secIsEnc = 0;
	private int secIsMac = 1;
	private int secIsContext = 1;

	private byte[] secEnc;
	private byte[] secMac;
	private String secContext;
	
	private String secRespCode;
	
	private boolean isSign;

	private byte[] secSign;
	
	private String secLength;
	
	public int getSecIsEnc() {
		return secIsEnc;
	}

	public void setSecIsEnc(int secIsEnc) {
		this.secIsEnc = secIsEnc;
	}

	public int getSecIsMac() {
		return secIsMac;
	}

	public void setSecIsMac(int secIsMac) {
		this.secIsMac = secIsMac;
	}

	public int getSecIsContext() {
		return secIsContext;
	}

	public void setSecIsContext(int secIsContext) {
		this.secIsContext = secIsContext;
	}

	public byte[] getSecEnc() {
		return secEnc;
	}

	public void setSecEnc(byte[] secEnc) {
		this.secEnc = secEnc;
	}

	public byte[] getSecMac() {
		return secMac;
	}

	public void setSecMac(byte[] secMac) {
		this.secMac = secMac;
	}

	public String getSecContext() {
		return secContext;
	}

	public void setSecContext(String secContext) {
		this.secContext = secContext;
	}

	public byte[] getSecSign() {
		return secSign;
	}

	public void setSecSign(byte[] secSign) {
		this.secSign = secSign;
	}

	public String getSecRespCode() {
		return secRespCode;
	}

	public void setSecRespCode(String secRespCode) {
		this.secRespCode = secRespCode;
	}

	public String getLocalSecNode() {
		return localSecNode;
	}

	public void setLocalSecNode(String localSecNode) {
		this.localSecNode = localSecNode;
	}

	public String getRemoteSecNode() {
		return remoteSecNode;
	}

	public void setRemoteSecNode(String remoteSecNode) {
		this.remoteSecNode = remoteSecNode;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public String getSecErrorCode() {
		return secErrorCode;
	}

	public void setSecErrorCode(String secErrorCode) {
		this.secErrorCode = secErrorCode;
	}

	public String getSecLength() {
		return secLength;
	}

	public void setSecLength(String secLength) {
		this.secLength = secLength;
	}
}
