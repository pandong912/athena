package com.ccb.athena.executor.framework.consumer;

import com.ccb.athena.executor.framework.SpringUtil;


public class Main {
	public static void main(String[] args) {
		
//		String conf = "applicationContext.xml";
		String conf = "localApplicationContext.xml";
		
		
		SpringUtil util = new SpringUtil(conf);
		ConsumerMain consumer = (ConsumerMain)util.getObject("consumerMain");
		consumer.start();	
		
	}

}
