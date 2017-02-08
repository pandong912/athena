package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.PublicField;

public class PublicFieldDirector {
	
	private PublicFieldBuilder builder;
	
	public PublicFieldDirector(PublicFieldBuilder builder){
		this.builder = builder;
	}
	
	public PublicField construct(){
		builder.buildHeader();
		builder.buildFixedCommon();
		builder.buildOptionalCommon();
		return builder.getResult();
	}

}
