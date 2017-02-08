package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.MessageField;

public abstract class MessageFieldBuilder {

	protected MessageField messageField = new MessageField();

	public abstract void buildPublic();

	public abstract void buildEntity();

	public MessageField getResult() {
		return messageField;
	}

}
