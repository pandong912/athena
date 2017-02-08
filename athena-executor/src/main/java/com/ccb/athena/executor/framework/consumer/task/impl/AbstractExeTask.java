package com.ccb.athena.executor.framework.consumer.task.impl;

import com.ccb.athena.executor.framework.consumer.task.IExeTask;

public abstract class AbstractExeTask implements IExeTask {
	//处理超时时间：单位秒
	private long timeout;

	//用于控制向相关队列投递{}前的等待时间
	private int waitTime;
	//
	private float factor = 1.5f;
	
	private PostFlag nextStepFlag;
	
	
	public AbstractExeTask(){}
	
	public AbstractExeTask(PostFlag nextStepFlag,int timeout){
		setNextStepFlag(nextStepFlag);
		setTimeout(timeout);
		
	}
	
	public AbstractExeTask(PostFlag nextStepFlag,int timeout,float factor){
		this.factor = factor;
		setNextStepFlag(nextStepFlag);
		setTimeout(timeout);
	}
	
	public PostFlag getPostFlag() {
		return this.nextStepFlag;
	}

	public long getWaitTime() {
		return this.waitTime;
	}
	
	public long getTimeout() {
		return timeout;
	}

	public float getFactor() {
		return factor;
	}

	public PostFlag getNextStepFlag() {
		return nextStepFlag;
	}
	public void setNextStepFlag(PostFlag nextStepFlag) {
		this.nextStepFlag = nextStepFlag;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
		this.waitTime = (int)Math.ceil(timeout*factor);
	}

	public void setFactor(float factor) {
		this.factor = factor;
		this.waitTime = (int)Math.ceil(timeout*factor);
	}
}
