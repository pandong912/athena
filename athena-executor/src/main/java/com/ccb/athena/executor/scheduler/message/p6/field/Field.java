package com.ccb.athena.executor.scheduler.message.p6.field;

import java.util.LinkedHashMap;

import org.dom4j.Document;

public abstract class Field {
	public static String INDEX_KEY = "index";
	
	protected String name;

	protected String path;// 节点路径

	public abstract boolean isEmpty(Document doc);// 判断字段值是否为空

	public abstract int length();// 获取字段的长度

	public abstract byte[] convert(Document doc);

	public abstract LinkedHashMap<String, String> deconvert(byte[] value, int startPos);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
