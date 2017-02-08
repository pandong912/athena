package com.ccb.athena.executor.scheduler.protocol.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.ccb.athena.executor.scheduler.protocol.Protocol;

public class HttpPostProtocol extends HttpBasicProtocol implements Protocol {

	private String encoding;

	public HttpPostProtocol(String encoding) {
		super();
		this.encoding = encoding;
	}

	@Override
	public byte[] send(byte[] message, String address, Object... params) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("xml", new String(message)));
		pairs.add(new BasicNameValuePair("ip", ""));

		HttpEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(pairs, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return this.send(address, entity);
	}

}
