package com.ccb.athena.executor.framework;

public class Constants {
	//消费者线程状态
	public static final String THREAD_STATUS_START = "START";	//线程启动
	public static final String THREAD_STATUS_PAUSE = "PAUSE";	//线程暂停
	public static final String THREAD_STATUS_RUNNINIG = "RUNNING";		//运行中
	public static final String THREAD_STATUS_STOP = "STOP";		//结束线程
	
	//ZK节点
	//完整路径为/projectNode/thread/子节点
	//完整路径为/projectNode/task/子节点
	public static final String NODE_CONSUMER="/thread/";
	public static final String NODE_PRODUCER="/task/";
	
	//标识型消息，该消息表示任务已经全部处理
	public static final String FLAG_MESSAGE="{}";
	
	//在redis的key后面拼该字符串，即该key表示生产者生成的消息的数量
	public static final String TASK_COUNT_PRODUCER_KEYNAME_SUFFIX="ProducerCount";
	
	//在redis的key后面拼该字符串，即该key表示消费者消费的消息的数量
	public static final String TASK_COUNT_CONSUMER_KEYNAME_SUFFIX="ConsumerCount";
	
	public static final String TASK_NODE_KEY="nodeValue";
	public static final String TASK_NAME_KEY="taskName";
	public static final String TASK_TYPE_KEY="taskType";
	public static final String TASK_PULL_KEY="pullKey";
	public static final String TASK_PUSH_KEY="pushKey";
	public static final String TASK_INFO_KEY="taskInfo";
	
}