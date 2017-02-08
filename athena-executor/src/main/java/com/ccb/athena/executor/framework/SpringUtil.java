package com.ccb.athena.executor.framework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringUtil {
	private ApplicationContext context= null;
	
	public SpringUtil(String contextFile) {
		context= new ClassPathXmlApplicationContext(contextFile);
	}
	
	public Object getObject(String beanId){
		return context.getBean(beanId);
	}
}
