package com.ccb.athena.executor.scheduler.message.p6.field;

import java.util.ArrayList;
import java.util.List;

public class PublicField {

	private List<Field> header = new ArrayList<Field>();

	private List<Field> fixedCommon = new ArrayList<Field>();

	private List<Field> optionalCommon = new ArrayList<Field>();

	public List<Field> getHeader() {
		return header;
	}

	public List<Field> getFixedCommon() {
		return fixedCommon;
	}

	public List<Field> getOptionalCommon() {
		return optionalCommon;
	}

	public void addHeader(Field field) {
		this.header.add(field);
	}

	public void addFixedCommon(Field field) {
		this.fixedCommon.add(field);
	}

	public void addOptionalCommon(Field field) {
		this.optionalCommon.add(field);
	}

}
