package com.ccb.athena.executor.scheduler.message.template;

import java.util.Map;

public class MessageTemplate {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 服务类型
	 */
	private String serviceType;

	/**
	 * 服务id
	 */
	private String serviceId;

	/**
	 * 功能描述
	 */
	private String functionDesc;

	/**
	 * 请求报文
	 */
	private String reqXml;

	/**
	 * 响应报文
	 */
	private String resXml;

	/**
	 * 通用域引用范围
	 */
	private String generalDomain;

	/**
	 * 产品id
	 */
	private String projectId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 行号
	 */
	private int rowNo;

	/**
	 * 更新次数
	 */
	private int updateTimes;

	/**
	 * 产品名称
	 */
	private String proName;

	/**
	 * 项目分类
	 */
	private String category;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * 模板所属文件名称
	 */
	private String fileName;

	public int getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(int updateTimes) {
		this.updateTimes = updateTimes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private Map<String, MessageTemplateAttr> reqTemplateAttrMap;

	private Map<String, MessageTemplateAttr> resTemplateAttrMap;

	public Map<String, MessageTemplateAttr> getReqTemplateAttrMap() {
		return reqTemplateAttrMap;
	}

	public void setReqTemplateAttrMap(Map<String, MessageTemplateAttr> reqTemplateAttrMap) {
		this.reqTemplateAttrMap = reqTemplateAttrMap;
	}

	public Map<String, MessageTemplateAttr> getResTemplateAttrMap() {
		return resTemplateAttrMap;
	}

	public void setResTemplateAttrMap(Map<String, MessageTemplateAttr> resTemplateAttrMap) {
		this.resTemplateAttrMap = resTemplateAttrMap;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getReqXml() {
		return reqXml;
	}

	public void setReqXml(String reqXml) {
		this.reqXml = reqXml;
	}

	public String getResXml() {
		return resXml;
	}

	public void setResXml(String resXml) {
		this.resXml = resXml;
	}

	public String getGeneralDomain() {
		return generalDomain;
	}

	public void setGeneralDomain(String generalDomain) {
		this.generalDomain = generalDomain;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
