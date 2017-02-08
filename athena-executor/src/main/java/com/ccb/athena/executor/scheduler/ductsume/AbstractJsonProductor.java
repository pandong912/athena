package com.ccb.athena.executor.scheduler.ductsume;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractJsonProductor implements Productor {
	
	private String topic;
	
	public AbstractJsonProductor(String topic) {
		super();
		this.topic = topic;
	}

	public void store(Object object) {
		String message = toJsonString(object);
		store(topic, message);
	}
	
	abstract void store(String topic, String message);

	private String toJsonString(Object object) {
		if (object instanceof String) {
			return (String)object;
		}
		return JSONObject.toJSONString(object);
	}
}
