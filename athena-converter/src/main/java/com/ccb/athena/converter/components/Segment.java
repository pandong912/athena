package com.ccb.athena.converter.components;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.ccb.testcenter.configuration.Configuration;

/**
 * 
 * 类名：segment 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 下午2:30:50 修改时间：2017年1月6日下午2:30:50 文件版本：@version 1.0.0
 *
 */
public class Segment {
	
	private String root;
	private Element rootElement;
	private Map<FieldElement, String> directRule = new HashMap<FieldElement, String>();
	private Map<FieldElement, String> constantRule = new HashMap<FieldElement, String>();
	private Map<FieldElement, IConverter> transitionRule = new HashMap<FieldElement, IConverter>();

	public Element getRootElement() {
		return rootElement;
	}

	public Map<FieldElement, String> getDirectRule() {
		return directRule;
	}

	public void setDirectRule(Map<FieldElement, String> directRule) {
		this.directRule = directRule;
	}

	public Map<FieldElement, String> getConstantRule() {
		return constantRule;
	}

	public void setConstantRule(Map<FieldElement, String> constantRule) {
		this.constantRule = constantRule;
	}

	public Map<FieldElement, IConverter> getTransitionRule() {
		return transitionRule;
	}

	public void setTransitionRule(Map<FieldElement, IConverter> transitionRule) {
		this.transitionRule = transitionRule;
	}

	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public void addDirectRule(FieldElement fieldElement, String parameter) {
		directRule.put(fieldElement, parameter);
	}

	public void addConstantRule(FieldElement fieldElement, String parameter) {
		constantRule.put(fieldElement, parameter);
	}

	public void addTransitionRule(FieldElement fieldElement, IConverter iConverter) {
		transitionRule.put(fieldElement, iConverter);
	}

	public Segment(Group rootgroup, String root, Configuration configuration) {
		this.root = root;
		this.rootElement = DocumentHelper.createElement(root);
		Map<String, Integer> indexMap = new HashMap<>();
		for (Entry entry : rootgroup.getEntries()) {
			indexMap = new HashMap<>();
			bulidElement(entry, rootElement, indexMap, configuration);
		}
	}

	private void bulidElement(Entry entry, Element parentElement, Map<String, Integer> indexMap, Configuration configuration) {
		if (entry instanceof Group) {
			Group group = (Group) entry;
			for (int i = 0; i < group.getTimes(); i++) {
				Element element = new BaseElement(group.getTagName());
				parentElement.add(element);
				for (Entry childEntry : group.getEntries()) {
					bulidElement(childEntry, element, indexMap, configuration);
				}
			}
		} else {
			Field field = (Field) entry;
			int index = 0;

			FieldElement fieldElement = new FieldElement();

			Element element = new BaseElement(entry.getTagName());
			parentElement.add(element);
			if (indexMap.containsKey(element.getPath())) {
				index = indexMap.get(element.getPath());
				indexMap.put(element.getPath(), index++);
			} else {
				indexMap.put(element.getPath(), 0);
			}

			fieldElement.setElement(element);
			fieldElement.setField(field);
			fieldElement.setIndex(index);

			String parameter = field.getParameter();
			switch (field.getMapType()) {
			case "A":
				addDirectRule(fieldElement, parameter);
				break;
			case "B":
				addConstantRule(fieldElement, parameter);
				break;
			case "C":
				break;
			case "D":
				CustomRule customRule = configuration.getCustomRule(parameter);
				addTransitionRule(fieldElement, customRule);
				break;
			}
		}
	}
}
