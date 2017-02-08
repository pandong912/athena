package com.ccb.athena.executor.framework.consumer.task;


public interface IExeTask {
	
	/**
	 * 处理接口
	 * @param caseInfo 案例信息 
	 * @param taskInfo 任务信息
	 * @return
	 */
	public String process(String caseInfo,String taskInfo);
	
	/**
	 * @return 任务是否有后续步骤标志
	 */
	public PostFlag getPostFlag();
	
	public long getWaitTime();
	
	/**
	 * 获取到的标志任务结束的标志型消息时，如何处理
	 */
	public static enum PostFlag {
		//默认值
		//直接删除任务节点
		HasNextStep(0),
		//否则，将标志型消息再放回队列
		NoNextStep(1);
		
		private final int intValue;

		PostFlag(int intValue) {
			this.intValue = intValue;
		}
		
		public int getIntValue() {
			return intValue;
		}
	}
}
