package com.ccb.athena.executor.scheduler.protocol.http;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import com.ccb.athena.executor.scheduler.protocol.Protocol;

public class HttpStreamProtocol extends HttpBasicProtocol implements Protocol {

	public HttpStreamProtocol() {
		super();
	}

	@Override
	public byte[] send(byte[] message, String address, Object... params) {
		HttpEntity entity = new ByteArrayEntity(message);
		return this.send(address, entity);
	}

}
