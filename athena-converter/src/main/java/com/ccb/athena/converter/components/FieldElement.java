package com.ccb.athena.converter.components;

import org.dom4j.Element;

public class FieldElement {
	
	private Element element;
	private Field field;

	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public boolean isArray() {
		return field.isArray();
	}
}
