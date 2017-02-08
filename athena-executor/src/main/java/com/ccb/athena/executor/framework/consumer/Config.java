package com.ccb.athena.executor.framework.consumer;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.ccb.athena.executor.framework.Constants;
import com.ccb.athena.executor.framework.consumer.thread.ConsumerThread;

/**
 * 用于消费者组件的参数配置
 * 并通过JMX实现参数运行时修改
 * @author flytoice
 *
 */
public class Config implements ConfigMBean{
	
	//线程数
	private int threadNum=1;
	
	//ip线程所在机器ip
	private String ip;
	
	//根目录:/ltr
	private String proRootPath;
	private String threadPath;
	private String taskPath;
	//key:zk节点名
	//cts的put操作是在各线程中执行的
	private ConcurrentHashMap<String,ConsumerThread> ConsumerThreads;
	//各线程状态
	private ConcurrentHashMap<String,String> ConsumerThreadStatus;
	//各线程对应通知等待对象锁
	private ConcurrentHashMap<String,Object> ConsumerThreadLocks;
	
	
	public Config(){
		registerMBean();
	}
	
	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public ConcurrentHashMap<String,ConsumerThread> getConsumerThreads() {
		return ConsumerThreads;
	}

	public ConcurrentHashMap<String,String> getConsumerThreadStatus() {
		return ConsumerThreadStatus;
	}
	
	public ConcurrentHashMap<String,Object> getConsumerThreadLocks() {
		return ConsumerThreadLocks;
	}

	public String getProRootPath() {
		return proRootPath;
	}

	public void setProRootPath(String proRootPath) {
		this.proRootPath = proRootPath;
		this.taskPath=this.proRootPath+Constants.NODE_PRODUCER;
		this.threadPath=this.proRootPath+Constants.NODE_CONSUMER;
	}
	
	public void init(int threadNum){
		ConsumerThreads = new ConcurrentHashMap<String,ConsumerThread>(threadNum);
		ConsumerThreadStatus = new ConcurrentHashMap<String,String>(threadNum);
		ConsumerThreadLocks = new ConcurrentHashMap<String,Object>(threadNum);
	}

	public String getThreadPath() {
		return threadPath;
	}

	public String getTaskPath() {
		return taskPath;
	}
	
	private void registerMBean(){
		//jconsole可以连接
		//通过工厂类获取MBeanServer，用来做MBean的容器
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		
		//ObjectName中的取名是有一定规范的，格式为：“域名：name=MBean名称”
		//其中域名和MBean的名称可以任意取。这样定义后，就可以唯一标识我们定义的这个MBean的实现类了
		ObjectName ConfigName;
		try {
			ConfigName = new ObjectName("jmxBean:name=Config");
			//将Hello这个类注入到MBeanServer中，注入需要创建一个ObjectName类
			server.registerMBean(this, ConfigName);
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getThreadStatus(String threadName) {
		return ConsumerThreadStatus.get(threadName);
		
	}

	public void setThreadStatus(String threadName, String status) {
		ConsumerThreads.get(threadName).setThreadStatus(status);;
	}
	
	
}
