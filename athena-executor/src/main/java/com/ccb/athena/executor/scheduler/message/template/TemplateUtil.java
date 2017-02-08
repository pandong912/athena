package com.ccb.athena.executor.scheduler.message.template;

import java.util.LinkedHashMap;

public class TemplateUtil {

	public static MessageTemplate getTemplate(String txCode) {
		MessageTemplate template = new MessageTemplate();

		return template;
	}

	public static LinkedHashMap<String, MessageTemplateAttr> getHeaderTemplateAttr() {
		LinkedHashMap<String, MessageTemplateAttr> headerRegionMap = new LinkedHashMap<String, MessageTemplateAttr>();

		return headerRegionMap;
	}

	public static LinkedHashMap<String, MessageTemplateAttr> getCommonTemplateAttr() {
		LinkedHashMap<String, MessageTemplateAttr> commonRegionMap = new LinkedHashMap<String, MessageTemplateAttr>();

		return commonRegionMap;
	}

	public static LinkedHashMap<String, MessageTemplateAttr> getSecTemplateAttr() {
		LinkedHashMap<String, MessageTemplateAttr> secTemplateMap = new LinkedHashMap<String, MessageTemplateAttr>();
		MessageTemplateAttr cicsTrnidAttr = new MessageTemplateAttr();
		cicsTrnidAttr.setNodeName("CICS_TRNID");
		cicsTrnidAttr.setBarPro("C");
		cicsTrnidAttr.setInLength("4");
		cicsTrnidAttr.setEncoding(1);

		MessageTemplateAttr secErrorCodeAttr = new MessageTemplateAttr();
		secErrorCodeAttr.setNodeName("SEC_ERROR_CODE");
		secErrorCodeAttr.setBarPro("C");
		secErrorCodeAttr.setInLength("12");
		secErrorCodeAttr.setEncoding(1);

		MessageTemplateAttr secIsMacAttr = new MessageTemplateAttr();
		secIsMacAttr.setNodeName("SEC_IS_MAC");
		secIsMacAttr.setBarPro("C");
		secIsMacAttr.setInLength("1");
		secIsMacAttr.setEncoding(1);

		MessageTemplateAttr secIsContextAttr = new MessageTemplateAttr();
		secIsContextAttr.setNodeName("SEC_IS_CONTEXT");
		secIsContextAttr.setBarPro("C");
		secIsContextAttr.setInLength("1");
		secIsContextAttr.setEncoding(1);

		MessageTemplateAttr secIsEncAttr = new MessageTemplateAttr();
		secIsEncAttr.setNodeName("SEC_IS_ENC");
		secIsEncAttr.setBarPro("C");
		secIsEncAttr.setInLength("1");
		secIsEncAttr.setEncoding(1);

		MessageTemplateAttr secMacAttr = new MessageTemplateAttr();
		secMacAttr.setNodeName("SEC_MAC");
		secMacAttr.setBarPro("C");
		secMacAttr.setInLength("64");
		secMacAttr.setEncoding(2);

		MessageTemplateAttr secContextAttr = new MessageTemplateAttr();
		secContextAttr.setNodeName("SEC_CONTEXT");
		secContextAttr.setBarPro("C");
		secContextAttr.setInLength("300");
		secContextAttr.setEncoding(2);

		MessageTemplateAttr secId1Attr = new MessageTemplateAttr();
		secId1Attr.setNodeName("SEC_ID1");
		secId1Attr.setBarPro("C");
		secId1Attr.setInLength("6");
		secId1Attr.setEncoding(1);

		MessageTemplateAttr secId2Attr = new MessageTemplateAttr();
		secId2Attr.setNodeName("SEC_ID2");
		secId2Attr.setBarPro("C");
		secId2Attr.setInLength("6");
		secId2Attr.setEncoding(1);

		MessageTemplateAttr secTraceIdAttr = new MessageTemplateAttr();
		secTraceIdAttr.setNodeName("SEC_TRACE_ID");
		secTraceIdAttr.setBarPro("C");
		secTraceIdAttr.setInLength("25");
		secTraceIdAttr.setEncoding(1);

		MessageTemplateAttr secTxCodeAttr = new MessageTemplateAttr();
		secTxCodeAttr.setNodeName("SEC_TX_CODE");
		secTxCodeAttr.setBarPro("C");
		secTxCodeAttr.setInLength("9");
		secTxCodeAttr.setEncoding(1);

		MessageTemplateAttr secTxTypeAttr = new MessageTemplateAttr();
		secTxTypeAttr.setNodeName("SEC_TX_TYPE");
		secTxTypeAttr.setBarPro("C");
		secTxTypeAttr.setInLength("6");
		secTxTypeAttr.setEncoding(1);

		MessageTemplateAttr secLenAattr = new MessageTemplateAttr();
		secLenAattr.setNodeName("SEC_LEN");
		secLenAattr.setBarPro("C");
		secLenAattr.setInLength("10");
		secLenAattr.setEncoding(1);

		secTemplateMap.put("CICS_TRNID", cicsTrnidAttr);
		secTemplateMap.put("SEC_ERROR_CODE", secErrorCodeAttr);
		secTemplateMap.put("SEC_IS_MAC", secIsMacAttr);
		secTemplateMap.put("SEC_IS_CONTEXT", secIsContextAttr);
		secTemplateMap.put("SEC_IS_ENC", secIsEncAttr);
		secTemplateMap.put("SEC_MAC", secMacAttr);
		secTemplateMap.put("SEC_CONTEXT", secContextAttr);
		secTemplateMap.put("SEC_ID1", secId1Attr);
		secTemplateMap.put("SEC_ID2", secId2Attr);
		secTemplateMap.put("SEC_TRACE_ID", secTraceIdAttr);
		secTemplateMap.put("SEC_TX_CODE", secTxCodeAttr);
		secTemplateMap.put("SEC_TX_TYPE", secTxTypeAttr);
		secTemplateMap.put("SEC_LEN", secLenAattr);
		return secTemplateMap;
	}

}
