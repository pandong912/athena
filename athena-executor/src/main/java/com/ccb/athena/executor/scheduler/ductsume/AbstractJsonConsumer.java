package com.ccb.athena.executor.scheduler.ductsume;

import com.alibaba.fastjson.JSONObject;


public abstract class AbstractJsonConsumer implements Consumer {
	
	private String topic;

	public AbstractJsonConsumer(String topic) {
		super();
		this.topic = topic;
	}

	public <T> T fetch(Class<T> clazz) {
		String message = fetch(topic);
		if (message == null) {
			return null;
		}
		return JSONObject.parseObject(message, clazz);
	}
	
	public JSONObject fetchJson() {
		String message = fetch(topic);
		if (message == null) {
			return null;
		}
		return JSONObject.parseObject(message);
	}

	abstract String fetch(String topic);
}
