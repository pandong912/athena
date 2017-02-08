package com.ccb.athena.converter.components;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.tool.JsonParseTool;
/**
 * 
  * 类名：Response
  * 创建人：li_dk
  * 修改人：li_dk
  * 创建时间：2017年1月6日 下午3:41:12
  * 修改时间：2017年1月6日 下午3:41:12
  * 文件版本：@version 1.0.0
  *
 */
public class Response {
	private boolean convert;
	private LinkedList<Segment> segments;
	private Document response;
	public boolean isConvert() {
		return convert;
	}

	public void setIsconvert(boolean convert) {
		this.convert = convert;
	}

	public LinkedList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(LinkedList<Segment> segments) {
		this.segments = segments;
	}

	public Document getResDocument() {
		return response;
	}

	public void setResDocument(Document response) {
		this.response = response;
	}
	
	public String getResponse(JSONObject jsonObject) {
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
		return response.asXML();
	}
}
