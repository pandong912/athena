package com.ccb.athena.executor.scheduler.message.p6;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import sun.security.util.BitArray;

import com.ccb.athena.executor.scheduler.common.BytePackage;
import com.ccb.athena.executor.scheduler.common.EbcdicUtil;
import com.ccb.athena.executor.scheduler.common.XmlUtil;
import com.ccb.athena.executor.scheduler.manager.SecData;
import com.ccb.athena.executor.scheduler.message.AbstractMessageParser;
import com.ccb.athena.executor.scheduler.message.MessageParserException;
import com.ccb.athena.executor.scheduler.message.p6.field.Field;
import com.ccb.athena.executor.scheduler.message.p6.field.MessageField;
import com.ccb.athena.executor.scheduler.message.p6.field.PublicField;
import com.ccb.athena.executor.scheduler.message.template.MessageTemplateAttr;
import com.ccb.athena.executor.scheduler.message.template.TemplateUtil;

public class P6MessageParser extends AbstractMessageParser {

	private static final String CICS_TRNID = "DFLT";
	private static final MessageTemplateAttr CICS_TRNID_ATTR;
	private static final MessageTemplateAttr SEC_ERROR_CODE_ATTR;
	private static final MessageTemplateAttr SEC_IS_MAC_ATTR;
	private static final MessageTemplateAttr SEC_IS_CONTEXT_ATTR;
	private static final MessageTemplateAttr SEC_IS_ENC_ATTR;
	private static final MessageTemplateAttr SEC_MAC_ATTR;
	private static final MessageTemplateAttr SEC_CONTEXT_ATTR;
	private static final MessageTemplateAttr SEC_ID1_ATTR;
	private static final MessageTemplateAttr SEC_ID2_ATTR;
	private static final MessageTemplateAttr SEC_TRACE_ID_ATTR;
	private static final MessageTemplateAttr SEC_TX_CODE_ATTR;
	private static final MessageTemplateAttr SEC_TX_TYPE_ATTR;
	private static final MessageTemplateAttr SEC_LEN_ATTR;

	private static final Map<String, MessageTemplateAttr> commonAttrMap;

	private static final String STRING_DEFAULT = " ";// 字符串长度不够时,默认的补齐字符

	private static final String NUMBER_DEFAULT = " ";// 数字长度不够时,默认的补齐字符

	private static final byte BYTE_DEFAULT = 0x00;// 二进制字节长度不够时，默认的补齐字节

	private static final String ENCODING = "UTF-8";

	private static int secHeaderLen = 445;

	static {
		commonAttrMap = TemplateUtil.getCommonTemplateAttr();

		Map<String, MessageTemplateAttr> secAttrMap = TemplateUtil.getSecTemplateAttr();

		/*
		 * CICS_TRNID_ATTR = secAttrMap.get(""); SEC_ERROR_CODE_ATTR = secAttrMap.get(0); SEC_IS_MAC_ATTR = secAttrMap.get(1); SEC_IS_CONTEXT_ATTR = secAttrMap.get(2) ; SEC_IS_ENC_ATTR = secAttrMap.get(3); SEC_MAC_ATTR = secAttrMap.get(4); SEC_CONTEXT_ATTR = secAttrMap.get(5); SEC_ID1_ATTR = secAttrMap.get(6); SEC_ID2_ATTR = secAttrMap.get(7); SEC_TRACE_ID_ATTR = secAttrMap.get(8); SEC_TX_CODE_ATTR = secAttrMap.get(9); SEC_TX_TYPE_ATTR = secAttrMap.get(10); SEC_LEN_ATTR = secAttrMap.get(11);
		 */
		CICS_TRNID_ATTR = secAttrMap.get("CICS_TRNID");
		SEC_ERROR_CODE_ATTR = secAttrMap.get("SEC_ERROR_CODE");
		SEC_IS_MAC_ATTR = secAttrMap.get("SEC_IS_MAC");
		SEC_IS_CONTEXT_ATTR = secAttrMap.get("SEC_IS_CONTEXT");
		SEC_IS_ENC_ATTR = secAttrMap.get("SEC_IS_ENC");
		SEC_MAC_ATTR = secAttrMap.get("SEC_MAC");
		SEC_CONTEXT_ATTR = secAttrMap.get("SEC_CONTEXT");
		SEC_ID1_ATTR = secAttrMap.get("SEC_ID1");
		SEC_ID2_ATTR = secAttrMap.get("SEC_ID2");
		SEC_TRACE_ID_ATTR = secAttrMap.get("SEC_TRACE_ID");
		SEC_TX_CODE_ATTR = secAttrMap.get("SEC_TX_CODE");
		SEC_TX_TYPE_ATTR = secAttrMap.get("SEC_TX_TYPE");
		SEC_LEN_ATTR = secAttrMap.get("SEC_LEN");
	}

	public P6MessageParser(String respCodePath, String respDescPath, String successStatus, String messageBeforeAppend, String messageAfterAppend) {
		super(respCodePath, respDescPath, successStatus, messageBeforeAppend, messageAfterAppend);

	}

	@Override
	public byte[] packMessage(String message, String txCode) throws MessageParserException {

		MessageField nessageField = MessageFieldFactory.getReq(txCode);
		PublicField publicField = nessageField.getPublicField();
		List<Field> headerFields = publicField.getHeader();// 头结构
		List<Field> fixedCommons = publicField.getFixedCommon();// 固定通用域结构
		List<Field> optionalCommons = publicField.getOptionalCommon();// 可选通用域结构
		List<Field> entityFields = nessageField.getEntityField();// 实体域结构
		Map<String, String> xmlData = XmlUtil.xml2Map(message);// 把xml报文转换成map

		Document doc = null;
		try {
			doc = DocumentHelper.parseText(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		byte[] entityBytes = packEntity(entityFields, xmlData, doc);// 组装实体域
		int entityLen = entityBytes.length;

		Map<String, byte[]> opResult = packOptionalCommon(optionalCommons, xmlData, doc);// 组装可选通用域
		byte[] opComBytes = opResult.get("data");
		byte[] opBiteMap = opResult.get("bitMap");
		int opComLen = opComBytes.length;

		byte[] fixComBytes = packFixedCommon(fixedCommons, entityLen, opComLen, opBiteMap, xmlData, doc);// 组装固定通用域
		int fixComLen = fixComBytes.length;

		byte[] headerBytes = packHeader(headerFields, entityLen, fixComLen, opComLen, xmlData, doc);// 组装头

		BytePackage pack = new BytePackage();
		pack.append(headerBytes);// 先放入头
		pack.append(fixComBytes);// 放入固定通用域
		// pack.append(opBiteMap);//放入可选通用域位图
		pack.append(entityBytes);// 放入实体域
		return pack.getBytes();
	}

	private byte[] packEntity(final List<Field> entityFields, final Map<String, String> xmlData, Document doc) {
		BytePackage pack = new BytePackage();
		for (Field field : entityFields) {
			byte[] data = field.convert(doc);
			int length = data.length;
			// 把长度封装成两个字节放在数据前
			byte[] lengthByte = new byte[2];
			lengthByte[1] = (byte) (length & 0xff);
			lengthByte[0] = (byte) (length >> 8 & 0xff);
			pack.append(lengthByte);
			pack.append(data);
		}
		return pack.getBytes();
	}

	private Map<String, byte[]> packOptionalCommon(final List<Field> optionalCommon, final Map<String, String> xmlData, Document doc) {
		Map<String, byte[]> result = new HashMap<String, byte[]>();
		BitArray bitArray = new BitArray(128);
		;// 可选域占128位的位图
		int pos = 0;
		BytePackage dataPack = new BytePackage();
		for (Field field : optionalCommon) {
			if (field.isEmpty(doc)) {// 如果字段值是空，跳过
				pos++;
				if (pos == 7 || pos == 9) {// 第八第十个位图为主机预留，不使用
					pos++;
				}
				continue;
			}
			byte[] data = field.convert(doc);
			dataPack.append(data);
			if (pos == 7 || pos == 9) {// 第八第十个位图为主机预留，不使用
				pos++;
			}

			if (data != null) {
				bitArray.set(pos, true);
			}
			pos++;
		}
		byte[] bitMap = bitArray.toByteArray();
		result.put("bitMap", bitMap);
		result.put("data", dataPack.getBytes());
		return result;
	}

	private byte[] packFixedCommon(final List<Field> fixedCommons, final int entityLen, final int OptionalLen, byte[] bitMap, Map<String, String> xmlData, Document doc) {
		BytePackage dataPack = new BytePackage();
		for (Field field : fixedCommons) {
			if (field.getName().equals("OPTIONAL_LL")) {// 设置可选通用域长度
				xmlData.put(field.getPath(), OptionalLen + "");
			} else if (field.getName().equals("ENTITY_LL")) {
				xmlData.put(field.getPath(), entityLen + "");
			}
			dataPack.append(field.convert(doc));
		}
		dataPack.append(bitMap);// 放入可选通用域位图
		return dataPack.getBytes();
	}

	private static byte[] packHeader(final List<Field> headerFields, final int entityLen, final int fixLen, final int optionalLen, final Map<String, String> xmlData, Document doc) {
		BytePackage dataPack = new BytePackage();

		int headLen = 0;// 计算头的长度，
		for (Field field : headerFields) {
			headLen += field.length();
		}
		for (Field field : headerFields) {

			if (field.getName().equals("SYS_HDR_LEN")) {// 可选系统头长度+协议报文头长度
				Node node = doc.selectSingleNode(field.getPath());
				if (node != null) {
					node.setText((secHeaderLen + headLen) + "");
				}
				xmlData.put(field.getPath(), (headLen + optionalLen) + "");
			} else if (field.getName().equals("SYS_TTL_LEN")) {// 所有报文域总长
				Node node = doc.selectSingleNode(field.getPath());
				if (node != null) {
					node.setText((secHeaderLen + headLen + fixLen + optionalLen + entityLen) + "");
				}
				xmlData.put(field.getPath(), (headLen + fixLen + optionalLen + entityLen) + "");
			} else if (field.getName().equals("SYS_MSG_LEN")) {// 应用报文固定通用域长度+可选通用域长度+实体域长度,
				Node node = doc.selectSingleNode(field.getPath());
				if (node != null) {
					node.setText((entityLen + fixLen + optionalLen) + "");
				}
				xmlData.put(field.getPath(), (entityLen + fixLen + optionalLen) + "");

			}

			dataPack.append(field.convert(doc));
		}
		return dataPack.getBytes();
	}

	@Override
	public byte[] packMessage(SecData secData, String traceId, String txCode, String txType) throws MessageParserException {
		BytePackage pack = new BytePackage();
		// 组装安全头
		// 组装 CICS_TRNID
		pack.append(packField(CICS_TRNID, CICS_TRNID_ATTR, true));
		// 组装 SEC_ERROR_CODE;
		pack.append(packField(secData.getSecErrorCode(), SEC_ERROR_CODE_ATTR, true));
		// 组装 SEC_IS_MAC;
		pack.append(packField(secData.getSecIsMac() + "", SEC_IS_MAC_ATTR, true));
		// 组装 SEC_IS_CONTEXT;
		pack.append(packField(secData.getSecIsContext() + "", SEC_IS_CONTEXT_ATTR, true));
		// 组装 SEC_IS_ENC;
		pack.append(packField(secData.getSecIsEnc() + "", SEC_IS_ENC_ATTR, true));
		// 组装 SEC_MAC;
		pack.append(packField(secData.getSecMac(), SEC_MAC_ATTR, true));
		// 组装 SEC_CONTEXT;
		pack.append(packField(secData.getSecContext(), SEC_CONTEXT_ATTR, true));
		// 组装 SEC_ID1;
		pack.append(packField(secData.getLocalSecNode() + "", SEC_ID1_ATTR, true));
		// 组装 SEC_ID2;
		pack.append(packField(secData.getRemoteSecNode() + "", SEC_ID2_ATTR, true));
		// 组装 SEC_TRACE_ID;
		pack.append(packField(traceId, SEC_TRACE_ID_ATTR, true));
		// 组装 SEC_TX_CODE;
		pack.append(packField(txCode, SEC_TX_CODE_ATTR, true));
		// 组装 SEC_TX_TYPE;
		pack.append(packField(txType, SEC_TX_TYPE_ATTR, true));
		// 组装 SEC_LEN;
		byte[] messageBody = secData.getSecEnc();
		int length = messageBody.length;
		int realLen = Integer.valueOf(SEC_LEN_ATTR.getInLength());
		String secLenStr = formatLen(length, realLen);
		pack.append(string2ebcdic(secLenStr));
		// 组装报文内容
		pack.append(messageBody);

		return pack.getBytes();
	}

	@Override
	public SecData getSecData(byte[] message) throws MessageParserException {
		SecData secData = new SecData();
		int pos = 0;
		// CICS_TRNID
		pos = +Integer.valueOf(CICS_TRNID_ATTR.getInLength());

		// 解析 SEC_ERROR_CODE;
		int length = Integer.valueOf(SEC_ERROR_CODE_ATTR.getInLength());
		byte[] errorCodeBytes = new byte[length];
		System.arraycopy(message, pos, errorCodeBytes, 0, length);
		pos += length;
		secData.setSecErrorCode(ebcdic2string(errorCodeBytes));

		// 解析 SEC_IS_MAC;
		length = Integer.valueOf(SEC_IS_MAC_ATTR.getInLength());
		byte[] isMacBytes = new byte[length];
		System.arraycopy(message, pos, isMacBytes, 0, length);
		pos += length;
		secData.setSecIsMac(Integer.valueOf(ebcdic2string(isMacBytes)));

		// 解析 SEC_IS_CONTEXT;
		length = Integer.valueOf(SEC_IS_CONTEXT_ATTR.getInLength());
		byte[] secIsContext = new byte[length];
		System.arraycopy(message, pos, secIsContext, 0, length);
		pos += length;
		secData.setSecIsContext(Integer.valueOf(ebcdic2string(secIsContext)));
		// 解析 SEC_IS_ENC;
		length = Integer.valueOf(SEC_IS_ENC_ATTR.getInLength());
		byte[] isEncBytes = new byte[length];
		System.arraycopy(message, pos, isEncBytes, 0, length);
		pos += length;
		secData.setSecIsMac(Integer.valueOf(ebcdic2string(isEncBytes)));
		// 解析 SEC_MAC;
		length = Integer.valueOf(SEC_MAC_ATTR.getInLength());
		byte[] secMacBytes = new byte[length];
		System.arraycopy(message, pos, secMacBytes, 0, length);
		pos += length;
		secData.setSecMac(ebcdic2byte(secMacBytes));
		// 解析 SEC_CONTEXT;
		length = Integer.valueOf(SEC_CONTEXT_ATTR.getInLength());
		byte[] secContextBytes = new byte[length];
		System.arraycopy(message, pos, secContextBytes, 0, length);
		pos += length;
		secData.setSecContext(ebcdic2string(secContextBytes));
		// 解析 SEC_ID1;
		length = Integer.valueOf(SEC_ID1_ATTR.getInLength());
		byte[] secId1Bytes = new byte[length];
		System.arraycopy(message, pos, secId1Bytes, 0, length);
		pos += length;
		secData.setLocalSecNode(ebcdic2string(secId1Bytes));
		// 解析 SEC_ID2;
		length = Integer.valueOf(SEC_ID2_ATTR.getInLength());
		byte[] secId2Bytes = new byte[length];
		System.arraycopy(message, pos, secId2Bytes, 0, length);
		pos += length;
		secData.setRemoteSecNode(ebcdic2string(secId2Bytes));
		// 解析 SEC_TRACE_ID;
		length = Integer.valueOf(SEC_TRACE_ID_ATTR.getInLength());
		// byte [] secTraceIdBytes = new byte[length];
		// System.arraycopy(message, pos, secTraceIdBytes, 0, length);
		pos += length;
		// 解析 SEC_TX_CODE;
		length = Integer.valueOf(SEC_TX_CODE_ATTR.getInLength());
		// byte [] secTxCodeBytes = new byte[length];
		// System.arraycopy(message, pos, secTxCodeBytes, 0, length);
		pos += length;
		// 解析 SEC_TX_TYPE;
		length = Integer.valueOf(SEC_TX_TYPE_ATTR.getInLength());
		// byte [] secTxTypeBytes = new byte[length];
		// System.arraycopy(message, pos, secTxTypeBytes, 0, length);
		pos += length;
		// 解析 SEC_LEN;

		length = Integer.valueOf(SEC_LEN_ATTR.getInLength());
		// byte [] secLenBytes = new byte[length];
		// System.arraycopy(message, pos, secLenBytes, 0, length);
		pos += length;
		int messageBodyLen = message.length - pos;
		byte[] messageBody = new byte[messageBodyLen];
		System.arraycopy(message, pos, messageBody, 0, length);
		secData.setSecEnc(messageBody);
		return secData;
	}

	@Override
	public String parseMessage(byte[] message, String execpXml, String txCode) throws MessageParserException {
		MessageField nessageField = MessageFieldFactory.getRes(txCode);
		PublicField publicField = nessageField.getPublicField();
		List<Field> header = publicField.getHeader();// 头结构
		List<Field> fixedCommon = publicField.getFixedCommon();// 固定通用域结构
		List<Field> optionalCommon = publicField.getOptionalCommon();// 可选通用域结构
		List<Field> entityField = nessageField.getEntityField();// 实体域结构

		int startPos = 0;
		LinkedHashMap<String, String> headerMap = parseHeader(header, message, startPos);
		startPos = Integer.valueOf(headerMap.get(Field.INDEX_KEY));

		LinkedHashMap<String, String> fixedMap = parseFixed(fixedCommon, message, startPos);
		startPos = Integer.valueOf(fixedMap.get(Field.INDEX_KEY));

		// 获取16个字节的位图
		byte[] bitMap = new byte[16];
		System.arraycopy(message, startPos, bitMap, 0, 16);
		startPos = startPos + 16;

		LinkedHashMap<String, String> optionalMap = parseOptional(optionalCommon, message, startPos, bitMap);
		startPos = Integer.valueOf(optionalMap.get(Field.INDEX_KEY));

		LinkedHashMap<String, String> entityMap = parseEntity(entityField, message, startPos);

		Document doc = DocumentHelper.createDocument();

		writeXml(headerMap, doc);
		writeXml(fixedMap, doc);
		writeXml(optionalMap, doc);
		writeXml(entityMap, doc);
		String xml = doc.asXML();
		xml = xml.replaceAll("\\[\\d*\\]", "");
		// System.out.println(xml);
		return xml;
	}

	private void writeXml(LinkedHashMap<String, String> dataMap, Document doc) {
		Iterator<Entry<String, String>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			Element ele = DocumentHelper.makeElement(doc, key);
			ele.setText(value);
		}
	}

	private LinkedHashMap<String, String> parseHeader(List<Field> fieldList, byte[] data, int startPos) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		int pos = startPos;
		for (Field field : fieldList) {
			LinkedHashMap<String, String> result = field.deconvert(data, pos);
			pos = Integer.valueOf(result.remove(Field.INDEX_KEY));
			dataMap.putAll(result);
		}
		dataMap.put(Field.INDEX_KEY, pos + "");
		return dataMap;
	}

	private LinkedHashMap<String, String> parseFixed(List<Field> fieldList, byte[] data, int startPos) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		int pos = startPos;
		for (Field field : fieldList) {
			LinkedHashMap<String, String> result = field.deconvert(data, pos);
			pos = Integer.valueOf(result.remove(Field.INDEX_KEY));
			dataMap.putAll(result);
		}
		dataMap.put(Field.INDEX_KEY, pos + "");
		return dataMap;
	}

	private LinkedHashMap<String, String> parseOptional(List<Field> fieldList, byte[] data, int startPos, byte[] bitMap) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		BitArray bitArray = new BitArray(128, bitMap);
		int pos = startPos;
		int bitIndex = 1;
		for (Field field : fieldList) {
			if (pos == 7 || pos == 9) {// 第八第十个位图为主机预留，不使用
				bitIndex++;
			}
			if (bitArray.get(bitIndex)) {// 如果该栏位为true表示有值
				LinkedHashMap<String, String> result = field.deconvert(data, pos);
				pos = Integer.valueOf(result.remove(Field.INDEX_KEY));
				dataMap.putAll(result);
			}
		}
		dataMap.put(Field.INDEX_KEY, pos + "");
		return dataMap;
	}

	private LinkedHashMap<String, String> parseEntity(List<Field> fieldList, byte[] data, int startPos) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		int pos = startPos;
		if (fieldList != null) {
			for (Field field : fieldList) {
				LinkedHashMap<String, String> result = field.deconvert(data, pos);
				pos = Integer.valueOf(result.remove(Field.INDEX_KEY));
				dataMap.putAll(result);
			}
		}

		dataMap.put(Field.INDEX_KEY, pos + "");
		return dataMap;
	}

	private byte[] packField(byte[] value, MessageTemplateAttr attr, boolean polishing) {
		byte[] data = null;

		BytePackage pack = new BytePackage();
		if (polishing) {
			int length = value.length;
			int realLength = Integer.valueOf(attr.getInLength());
			int differ = realLength - length;
			if (differ > 0) {
				pack.append(value);
				pack.append(copyByte(differ, BYTE_DEFAULT));

			} else if (differ < 0) {
				pack.append(value, 0, length);
			}
		} else {
			pack.append(value);
		}
		data = byte2ebcdic(pack.getBytes());

		return data;
	}

	private byte[] packField(String value, MessageTemplateAttr attr, boolean polishing) {
		byte[] data = null;
		String barPro = attr.getBarPro();
		String lengthStr = attr.getInLength();
		int encoding = attr.getEncoding();
		if (barPro.equals("C")) {
			if (polishing) {
				int realLen = Integer.valueOf(lengthStr);
				int valueLen = value.length();
				int differ = realLen - valueLen;
				if (differ > 0) {
					value += copyString(differ, STRING_DEFAULT);
				} else if (differ < 0) {
					value = value.substring(0, realLen);
				}
				if (encoding == 1) {
					data = string2ebcdic(value);
				} else if (encoding == 2) {

					data = string2Bytes(value);
				}

			}
		} else if (barPro.equals("N")) {
			// int intLength = 0;
			int decimalLength = 0;
			if (lengthStr.indexOf(",") >= 0) {// 带有小数点的长度
				lengthStr = lengthStr.replace("(", "").replace(")", "");
				String[] lengthArray = lengthStr.split(",");
				// intLength = Integer.valueOf(lengthArray[0]);
				decimalLength = Integer.valueOf(lengthArray[1]);

			} else {
				// intLength = Integer.valueOf(lengthStr);
				decimalLength = 0;
			}
			String intPart = "";
			String decimalPart = "";
			int decimalIndex = value.indexOf(".");
			if (decimalIndex > 0) {
				intPart = StringUtils.substring(value, 0, decimalIndex);
				decimalPart = StringUtils.substring(value, decimalIndex + 1);
			} else {
				intPart = value;
			}

			byte signFlag = 0;
			if (intPart.startsWith("+")) {
				intPart = StringUtils.substring(intPart, 1);
				signFlag = (byte) 0xC0;
			} else if (intPart.startsWith("-")) {
				intPart = StringUtils.substring(intPart, 1);
				signFlag = (byte) 0xD0;
			}
			int decimalPartLen = decimalPart.length();
			if (decimalPartLen < decimalLength) {
				int differ = decimalLength - decimalPartLen;
				decimalPart += copyString(differ, NUMBER_DEFAULT);
			}
			if (encoding == 1) {
				data = string2ebcdic(intPart + decimalPart);
			} else if (encoding == 2) {

				data = string2Bytes(intPart + decimalPart);
			}
			if (signFlag != 0) {
				data[data.length - 1] = (byte) ((data[data.length - 1] & 0x0F) | signFlag);

			}
		}

		return data;
	}

	private byte[] string2Bytes(String value) {
		try {
			return value.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	private byte[] copyByte(int count, byte b) {
		byte[] data = null;
		if (count <= 0) {
			return data;
		}
		data = new byte[count];
		for (int i = 0; i < count; i++) {
			data[i] = b;
		}
		return data;
	}

	private String copyString(int count, String s) {
		StringBuilder sb = new StringBuilder();
		if (count <= 0) {
			return sb.toString();
		}
		for (int i = 0; i < count; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	private byte[] string2ebcdic(String value) {
		byte[] utf8Bytes = null;
		try {
			utf8Bytes = value.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		return byte2ebcdic(utf8Bytes);
	}

	private byte[] byte2ebcdic(byte[] data) {
		return Code.ASCIIToEBCDIC(data);
	}

	private String ebcdic2string(byte[] ebcBytes) {
		String value = "";
		byte utf8Bytes[] = Code.EBCDICToASCII(ebcBytes);
		try {
			value = new String(utf8Bytes, ENCODING);
		} catch (UnsupportedEncodingException e) {

		}
		return value;
	}

	private byte[] ebcdic2byte(byte[] ebcBytes) {
		return Code.EBCDICToASCII(ebcBytes);
	}

	private static String formatLen(int value, int needLen) {

		String tmpValue = new Integer(value).toString();
		int curLen = tmpValue.length();
		int cos = needLen - curLen;

		for (int i = 0; i < cos; i++) {
			tmpValue = "0" + tmpValue;
		}
		return tmpValue.substring(0, needLen);
	}

	public static void test(Integer integer) {
		integer++;
	}

	public static void main(String[] args) {

		byte b3[] = { 0x20, 0x40 };

		byte[] b4 = Code.EBCDICToASCII(b3);
		// System.out.println(toHexStringWithSpace(b4));
		System.out.println(EbcdicUtil.ebcdic2ascii(b3));
	}

}
