package com.ccb.athena.executor.scheduler.protocol.tuxedo;

import com.ccb.athena.executor.scheduler.protocol.Protocol;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.LongByReference;

public class TuxedoProtocol implements Protocol {

	private static final String teller = "";
	private static final byte[] tellerBytes = teller.getBytes();

	@Override
	public byte[] send(byte[] message, String address, Object... params) {
		byte rcvBytes[] = null;
		String txCode = (String) params[0];
		byte[] serviceNameBytes = address.getBytes();
		byte[] txCodeBytes = txCode.getBytes();
		long sendLen = message.length;
		byte[] rcvBuffer = new byte[65536];
		LongByReference rcvLen = new LongByReference();
		TuxedoJna.Instance.sendToP6(serviceNameBytes, txCodeBytes, tellerBytes, message, sendLen, rcvBuffer, rcvLen);
		int length = (int) rcvLen.getValue();
		rcvBytes = new byte[length];
		System.arraycopy(rcvBuffer, 0, rcvBytes, 0, length);
		rcvBuffer = null;
		return rcvBytes;
	}

}

interface TuxedoJna extends Library {

	TuxedoJna Instance = (TuxedoJna) Native.loadLibrary((Platform.isWindows() ? "C:\\Users\\hp\\Desktop\\p6\\tuxdeo\\Debug\\tuxedo" : "simpcl"), TuxedoJna.class);

	void say(byte[] info);

	int sendToP6(byte[] tuxServiceName, byte[] txCode, byte[] strTeller, byte[] strSendBuf, long sendLen, byte[] strRcvBuffer, ByReference rcvLen);

}