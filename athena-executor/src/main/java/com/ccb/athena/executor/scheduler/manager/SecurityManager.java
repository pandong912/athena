package com.ccb.athena.executor.scheduler.manager;


public interface SecurityManager {

	SecData encode(byte[] message, String traceId) throws SecurityException;

	SecData encode(byte[] message, String traceId, boolean isSign) throws SecurityException;

	byte[] decode(SecData secData, String traceId) throws SecurityException;

	byte[] decode(SecData secData, String traceId, boolean isVerify) throws SecurityException;

}
