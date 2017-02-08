package com.ccb.athena.executor.scheduler.message.p6.field;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Document;

import com.ccb.athena.executor.scheduler.common.BytePackage;

public class LoopField extends Field {

	private List<Field> loops = new ArrayList<Field>();

	public boolean isEmpty(Document doc) {

		if (loops == null || loops.size() == 0) {
			return true;
		}
		for (Field field : loops) {
			if (!field.isEmpty(doc)) {// 循环字段有一个字段不为空，整个字段都必须有值
				return false;
			}
		}
		return true;
	}

	public int length() {
		int length = 0;
		for (Field field : loops) {
			length += field.length();
		}
		return length;
	}

	public byte[] convert(Document doc) {

		if (loops == null || loops.size() == 0) {
			return null;
		}
		BytePackage pack = new BytePackage();
		for (Field field : loops) {
			pack.append(field.convert(doc));
		}
		return pack.getBytes();
	}

	public LinkedHashMap<String, String> deconvert(byte[] value, int startPos) {
		if (loops == null || loops.size() == 0) {
			return null;
		}
		int pos = startPos;
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		for (Field field : loops) {
			LinkedHashMap<String, String> result = field.deconvert(value, pos);
			pos = Integer.valueOf(result.remove(INDEX_KEY));
			data.putAll(result);
		}
		data.put(INDEX_KEY, String.valueOf(pos));
		return data;
	}

	public void add(Field field) {
		this.loops.add(field);
	}

	public List<Field> getLoops() {
		return loops;
	}

}
