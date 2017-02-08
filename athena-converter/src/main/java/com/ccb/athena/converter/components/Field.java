package com.ccb.athena.converter.components;

public class Field extends Entry {
	
	private String identifier;	//数据项编号
	private String length;		//XML/CICS输入长度
	private boolean required;	//必须
	private String note;		//数据项说明
	private String encoding;	//码制
	private String secure;		//安全字段标志
	
	private MapRule mapRule;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getSecure() {
		return secure;
	}

	public void setSecure(String secure) {
		this.secure = secure;
	}
	
	public MapRule getMapRule() {
		return mapRule;
	}

	public void setMapRule(MapRule mapRule) {
		this.mapRule = mapRule;
	}

	public String getMapType() {
		return mapRule.getMapType();
	}
	
	public String getParameter() {
		return mapRule.getParameter();
	}

	public boolean isArray() {
		return mapRule.isArray();
	}

}
