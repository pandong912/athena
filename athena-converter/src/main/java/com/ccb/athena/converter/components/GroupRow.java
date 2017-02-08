package com.ccb.athena.converter.components;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

public class GroupRow implements Cloneable {

	private Row parent;
	private List<GroupRow> children;
	private String converterType;
	private String param;

	public String getConverterType() {
		return converterType;
	}

	public void setConverterType(String converterType) {
		this.converterType = converterType;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public GroupRow(Row row) {
		this.parent = row;
	}

	public Row getRow() {
		return parent;
	}

	public void setRow(Row row) {
		this.parent = row;
	}

	public int getNum() {
		return parent.getRowNum();
	}

	public int getDblTimes() {
		String firstCellValue = ExcelHelper.getCellValue(parent.getCell(3));
		if (StringUtils.isNotEmpty(firstCellValue) && firstCellValue.startsWith("*")) {
			String s = firstCellValue.substring(1);
			return Integer.valueOf(s) - 1;
		}
		return 0;
	}

	public int getPointNum() {
		int n = getPointNum(parent);
		return n;
	}

	private int getPointNum(Row row) {
		String firstCellValue = ExcelHelper.getCellValue(row.getCell(0));
		int num = firstCellValue.lastIndexOf(".");
		return num + 1;
	}

	public List<GroupRow> getChildren() {
		return children;
	}

	public void setChildren(List<GroupRow> children) {
		this.children = children;
	}

	public GroupRow deepClone() {
		return (GroupRow) clone();
	}

	public Object clone() {
		GroupRow row = null;
		try {
			row = (GroupRow) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return row;
	}
}
