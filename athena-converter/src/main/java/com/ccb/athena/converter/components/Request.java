package com.ccb.athena.converter.components;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.tool.JsonParseTool;
/**
 * 
 * 类名：Request 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 下午3:35:18
 * 修改时间：2017年1月6日下午3:35:18 文件版本：@version 1.0.0
 *
 */
public class Request {
	//
	private boolean isconvert;
	private LinkedList<Segment> segments;
	private Document request;

	public boolean isIsconvert() {
		return isconvert;
	}

	public void setIsconvert(boolean isconvert) {
		this.isconvert = isconvert;
	}

	public LinkedList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(LinkedList<Segment> segments) {
		this.segments = segments;
	}

	public Document getReqDocument() {
		return request;
	}

	public void setReqDocument(Document request) {
		this.request = request;
	}

	public String getRequest(JSONObject jsonObject) {
		for (int i = 0; i < this.segments.size(); i++) {
			Segment segment = segments.get(i);
			Map<FieldElement, String> directRules = segment.getDirectRule();
			for (Entry<FieldElement, String> directRule : directRules.entrySet()) {
				FieldElement fieldElement = directRule.getKey();
				int index = fieldElement.getIndex();
				String param = directRule.getValue();
				String value = null;
				try {
					if (fieldElement.isArray()) {
						value = JsonParseTool.getDataFromRelativePath(jsonObject, param, index);
					} else {
						value = JsonParseTool.getDataFromRelativePath(jsonObject, param);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				fieldElement.getElement().clearContent();
				fieldElement.getElement().addCDATA(value);
			}

			Map<FieldElement, String> constantRules = segment.getConstantRule();
			for (Entry<FieldElement, String> constantRule : constantRules.entrySet()) {
				FieldElement fieldElement = constantRule.getKey();
				String value = constantRule.getValue();
				fieldElement.getElement().clearContent();
				fieldElement.getElement().addCDATA(value);
			}

			Map<FieldElement, IConverter> transitionRules = segment.getTransitionRule();
			for (Entry<FieldElement, IConverter> transitionRule : transitionRules.entrySet()) {
				FieldElement fieldElement = transitionRule.getKey();
				IConverter iConverter = transitionRule.getValue();
				int index = fieldElement.getIndex();
				String value = iConverter.getConverterResult(jsonObject, index);
				fieldElement.getElement().clearContent();
				fieldElement.getElement().addCDATA(value);
			}
		}
		return request.asXML();
	}
}
