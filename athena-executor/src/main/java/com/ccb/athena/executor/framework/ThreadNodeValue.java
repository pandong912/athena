package com.ccb.athena.executor.framework;

/**
 * zookeeper中表示线程的节点中的值
 * @author flytoice
 * {taskName:xx,taskType:send|save,redisKey:xx}
 */
public class ThreadNodeValue {
	private volatile String taskName;
	private volatile String taskType;
	private volatile String redisKey;
	private volatile String pushKey;
	
	StringBuilder sb = new StringBuilder();
	
	public String getTaskName() {
		return taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public String getRedisKey() {
		return redisKey;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}
	
	@Override
	public String toString() {
		sb.setLength(0);
		if (null!=taskName){
			sb.append("taskName:"+taskName+",");
		}
		if (null!=taskType){
			sb.append("taskType:"+taskType+",");
		}
		if (null!=redisKey){
			sb.append("redisKey:"+redisKey+",");
		}
		if (null!=pushKey){
			sb.append("pushKey:"+pushKey+",");
		}
		
		return sb.toString();
	}
	public String getPushKey() {
		return pushKey;
	}
	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}
	
	@Override
	public ThreadNodeValue clone() throws CloneNotSupportedException {
		ThreadNodeValue dumy = new ThreadNodeValue();
		dumy.setTaskName(taskName);
		dumy.setTaskType(taskType);
		dumy.setRedisKey(redisKey);
		dumy.setPushKey(pushKey);
		return dumy;
	}
	
	
}
