package com.ccb.athena.executor.scheduler.message;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * 响应报文中对应响应码和响应描述
 * @author zhouxuke
 *
 */
public class RespData {
	
	private String respCode;
	
	private String respDesc;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		 DocumentHelper.makeElement(doc, "/TX/COM5/AHN_INFO/AHN_STFF_ID");
		System.out.println(doc.asXML());
	}
	

}
