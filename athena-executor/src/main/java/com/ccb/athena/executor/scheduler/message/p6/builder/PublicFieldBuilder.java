package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.PublicField;

public abstract class PublicFieldBuilder {
	protected PublicField publicField = new PublicField();

	/**
	 * 构建头
	 */
	public abstract void buildHeader();

	/**
	 * 构建固定通用域
	 */
	public abstract void buildFixedCommon();

	/**
	 * 构建可选通用域
	 */
	public abstract void buildOptionalCommon();

	/**
	 * 返回构建结果
	 * 
	 * @return
	 */
	public PublicField getResult() {
		return publicField;
	}

}
