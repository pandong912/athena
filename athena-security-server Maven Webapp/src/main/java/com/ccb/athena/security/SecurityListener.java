package com.ccb.athena.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;

public class SecurityListener implements ServletContextListener {

	private static final Logger log = LogManager.getLogger(SecurityListener.class);
	
	private static final String LOCAL_SEC_NODE_NAME = "secNode";
	private static final String USER_NAME = "user";
	private static final String DEFAULT_USER = "Athena";
	
	/**
	 * 服务器启动事件
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		
		String user = sc.getInitParameter(USER_NAME);		
		if(user == null) {
			user = DEFAULT_USER;
		}		
		String secNode = sc.getInitParameter(LOCAL_SEC_NODE_NAME);
		if (StringUtils.isEmpty(secNode)) {
			log.error("没有配置本地安全节点");
			throw new SecurityAgentException("没有配置本地安全节点号");
		}		
		try {
			SecAPI.nodeInit(secNode);
		} catch (SecException e) {
			log.error("初始化安全环境失败", e);
			throw new SecurityAgentException("初始化安全环境失败");
		}
		
		sc.setAttribute(LOCAL_SEC_NODE_NAME, secNode);
		
		SecurityParam.USER = user;
		SecurityParam.LOCAL_SEC_NODE = secNode;		
	}

	/**
	 * 服务器销毁事件
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		String secNode = (String) event.getServletContext().getAttribute(LOCAL_SEC_NODE_NAME);
		
		try {
			SecAPI.nodeFinal(secNode);
		} catch (SecException e) {
			log.error("销毁安全环境失败", e);
			throw new SecurityAgentException("销毁安全环境失败");
		}
	}

}
