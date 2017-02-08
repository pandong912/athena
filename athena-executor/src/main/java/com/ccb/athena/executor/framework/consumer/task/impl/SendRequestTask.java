package com.ccb.athena.executor.framework.consumer.task.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 * @author flytoice
 *
 */
public class SendRequestTask extends AbstractExeTask {
	private static final Logger logger = LogManager.getLogger();
	
	public String process(String task,String nodeValue) {
		logger.info(nodeValue.toString() + "msg:" + task);
		return task;
	}
}
