package com.ccb.athena.executor.scheduler.action;

public class AutoCase {
	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 项目id
	 */
	private String projectId;

	/**
	 * 测试任务id
	 */
	private String setId;

	/**
	 * 案例描述
	 */
	private String caseNo;

	/**
	 * 案例名称
	 */
	private String caseName;

	/**
	 * 交易码
	 */
	private String txCode;

	/**
	 * 交易类型
	 */
	private String txType;

	/**
	 * 交易流水号
	 */
	private String traceId;

	/**
	 * 请求报文
	 */
	private String reqMessage;

	/**
	 * 响应报文
	 */
	private String resMessage;

	/**
	 * 案例性质
	 */
	private String caseProperties;

	/**
	 * 案例描述
	 */
	private String caseDesc;

	/**
	 * 所属渠道
	 */
	private String channel;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 是否启用安全
	 */
	private boolean isSec;

	/**
	 * 安全环境
	 */
	private String secEnv;

	/**
	 * 安全节点
	 */
	private String secNode;

	/**
	 * 发送报文是否签名
	 */
	private boolean isSign;

	/**
	 * 接收报文是否验签
	 */
	private boolean isVerify;

	/**
	 * 案例状态
	 */
	private String caseStatus;

	/**
	 * 执行人
	 */
	private String executeUser;

	/**
	 * 执行时间
	 */
	private String executeTime;

	/**
	 * 分组id
	 */
	private String groupbyId;

	/**
	 * 排序
	 */
	private long orders;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public String getCaseProperties() {
		return caseProperties;
	}

	public void setCaseProperties(String caseProperties) {
		this.caseProperties = caseProperties;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isSec() {
		return isSec;
	}

	public void setSec(boolean isSec) {
		this.isSec = isSec;
	}

	public String getSecEnv() {
		return secEnv;
	}

	public void setSecEnv(String secEnv) {
		this.secEnv = secEnv;
	}

	public String getSecNode() {
		return secNode;
	}

	public void setSecNode(String secNode) {
		this.secNode = secNode;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getExecuteUser() {
		return executeUser;
	}

	public void setExecuteUser(String executeUser) {
		this.executeUser = executeUser;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getGroupbyId() {
		return groupbyId;
	}

	public void setGroupbyId(String groupbyId) {
		this.groupbyId = groupbyId;
	}

	public long getOrders() {
		return orders;
	}

	public void setOrders(long orders) {
		this.orders = orders;
	}

}
