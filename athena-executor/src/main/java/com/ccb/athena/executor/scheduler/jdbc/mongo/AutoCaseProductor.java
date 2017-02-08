package com.ccb.athena.executor.scheduler.jdbc.mongo;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class AutoCaseProductor {

	public static void main(String[] args) {
		MongoConnector connector = new MongoConnector();
		connector.connect("127.0.0.1", 27017, "ltr");
		MongoOperator operator = connector.getOperator("autoCase");
		JSONObject obj = new JSONObject();
		obj.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
		obj.put("res_xml", "<TX><TX_HEADER><RESP_CODE>00</RESP_CODE></TX_HEADER></TX>");
		obj.put("service_id", "A03812101");
		for (int i = 0; i < 100000; i++) {
			operator.save(obj);
		}
	}
}
