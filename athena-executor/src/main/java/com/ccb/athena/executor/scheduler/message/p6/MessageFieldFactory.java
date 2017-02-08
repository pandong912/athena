package com.ccb.athena.executor.scheduler.message.p6;

import com.ccb.athena.executor.scheduler.message.p6.builder.MessageFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.builder.MessageFieldDirector;
import com.ccb.athena.executor.scheduler.message.p6.builder.ReqMessageFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.builder.ResMessageFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.field.MessageField;

public class MessageFieldFactory {
	
	public static MessageField getReq(String txCode){
		MessageFieldBuilder builder = new ReqMessageFieldBuilder();
		MessageFieldDirector director = new MessageFieldDirector(builder);
		return director.construct();
	}
	
	public static MessageField getRes(String txCode){
		MessageFieldBuilder builder = new ResMessageFieldBuilder();
		MessageFieldDirector director = new MessageFieldDirector(builder);
		return director.construct();
	}

}
