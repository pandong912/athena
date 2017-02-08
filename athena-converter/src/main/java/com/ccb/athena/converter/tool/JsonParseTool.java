/**
 * 
 */
package com.ccb.athena.converter.tool;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;

/**
 * @author congyang
 *
 */

public class JsonParseTool {

	private static final Logger logger = LogManager.getLogger(JsonParseTool.class);

	public static String getDataFromRelativePath(JSONObject jsonObject, String key) throws Exception  {

		List<String> lists = JsonPath.read(jsonObject, "." + key);
		if (lists.size() != 1) {
			logger.info("查询" + key + "返回的字段个数不是一个！");
			throw new Exception();
		}
		return lists.get(0);

	}

	public static String getDataFromRelativePath(JSONObject jsonObject, String key, int index) throws Exception {

		List<String> list = JsonPath.read(jsonObject, "." + key);
		if (list.size() == 0) {
			throw new Exception();
		}
		if (index >= list.size()) {
			logger.info("返回的字段数组" + list.toString() + ",查询的下标" + index + "越界！");
			throw new IndexOutOfBoundsException();
		}
		return list.get(index);
	}

}
