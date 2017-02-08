package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.MessageField;

public class MessageFieldDirector {
	
	private MessageFieldBuilder builder;
	
	public MessageFieldDirector(MessageFieldBuilder builder){
		this.builder = builder;
	}
	
	public MessageField construct(){
		builder.buildPublic();
		builder.buildEntity();
		return builder.getResult();
	}

}
