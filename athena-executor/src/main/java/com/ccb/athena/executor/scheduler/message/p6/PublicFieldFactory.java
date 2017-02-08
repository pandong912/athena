package com.ccb.athena.executor.scheduler.message.p6;

import java.util.HashMap;
import java.util.Map;

import com.ccb.athena.executor.scheduler.message.p6.builder.PublicFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.builder.PublicFieldDirector;
import com.ccb.athena.executor.scheduler.message.p6.builder.ReqPublicFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.builder.ResPublicFieldBuilder;
import com.ccb.athena.executor.scheduler.message.p6.field.PublicField;

public class PublicFieldFactory {
	
	private static Map<String, PublicField> cache = new HashMap<String, PublicField>();
	
	public static PublicField getReqPublic(){
		PublicField publicField = cache.get("req");
		if(publicField==null){
			PublicFieldBuilder builder = new ReqPublicFieldBuilder();
			PublicFieldDirector director = new PublicFieldDirector(builder);
			publicField = director.construct();
			 cache.put("req", publicField);
		}
		
		return publicField;
	}
	
	public static PublicField getResPublic(){
		PublicField publicField = cache.get("res");
		if(publicField==null){
			PublicFieldBuilder builder = new ResPublicFieldBuilder();
			PublicFieldDirector director = new PublicFieldDirector(builder);
			publicField = director.construct();
			cache.put("res", publicField);
		}
		
		return publicField;
	}
}
