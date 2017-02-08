package com.ccb.athena.executor.scheduler.message.template;

public class MessageTemplateAttr implements Cloneable {
	private int level;// 级别
	private int seq;// 序号
	private String id;// 主键
	private String reqFlag;// 请求报文还是应答报文
	private String templateId;// 模板id
	private String proId;// 所属产品id
	private String nodeName;// 标签名
	private String nameCn;// 中文名称
	private String dataNo;// 数据项编号
	private String inLength;// 输入长度
	private String barPro;// 栏位属性
	private String necessary;// 必须
	private String flagVal;// 标志
	private String desc;// 描述
	private String sec;// 安全描述
	private String fatherNodeId;// 父节点
	private String cics;// cics长度
	private int encoding;// 编码格式

	private int rowNo;

	public String getReqFlag() {
		return reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFatherNodeId() {
		return fatherNodeId;
	}

	public void setFatherNodeId(String fatherNodeId) {
		this.fatherNodeId = fatherNodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getDataNo() {
		return dataNo;
	}

	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}

	public String getInLength() {
		return inLength;
	}

	public void setInLength(String inLength) {
		this.inLength = inLength;
	}

	public String getBarPro() {
		return barPro;
	}

	public void setBarPro(String barPro) {
		this.barPro = barPro;
	}

	public String getNecessary() {
		return necessary;
	}

	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}

	public String getFlagVal() {
		return flagVal;
	}

	public void setFlagVal(String flagVal) {
		this.flagVal = flagVal;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getCics() {
		return cics;
	}

	public void setCics(String cics) {
		this.cics = cics;
	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

}
