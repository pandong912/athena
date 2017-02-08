package com.ccb.athena.converter.components;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.tool.JsonParseTool;

public class CustomRule implements IConverter {
	private String id;
	private Class<?> clazz;
	private Method method;
	private List<MappingParam> params;

	public List<MappingParam> getParams() {
		return params;
	}

	public void setParams(List<MappingParam> params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String invoke(JSONObject jsonObject, int index) {
		String result = "";
		try {
			List<String> values = new ArrayList<String>(params.size());
			for (MappingParam param : params) {
				if (param.isArray()) {
					values.add(JsonParseTool.getDataFromRelativePath(jsonObject, param.getName(), index));
				} else {
					values.add(JsonParseTool.getDataFromRelativePath(jsonObject, param.getName()));
				}
			}
			result = (String) this.getMethod().invoke(this.getClazz().newInstance(), values.toArray());
			if (null == result) {
				return "";
			}
		} catch (Exception e) {
			Logger.getLogger("name").log(Level.WARNING, "类方法处理异常", e);
			return "";
		}
		return result;

	}

	@Override
	public String getConverterResult(JSONObject jsonObject, int index) {
		return invoke(jsonObject, index);
	}
}
