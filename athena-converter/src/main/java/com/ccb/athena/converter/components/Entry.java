package com.ccb.athena.converter.components;

public abstract class Entry {
	
	protected String tagName;		//栏位项目名称
	protected String nameZh;		//中文名称
	protected DataType dataType;	//栏位属性(C/N/GROUP)
	
	protected int level; // 层级

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getNameZh() {
		return nameZh;
	}

	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
