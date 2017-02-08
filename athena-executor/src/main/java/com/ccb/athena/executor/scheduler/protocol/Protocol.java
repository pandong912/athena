package com.ccb.athena.executor.scheduler.protocol;

public interface Protocol {

	byte[] send(byte[] message, String address, Object... params);

}
