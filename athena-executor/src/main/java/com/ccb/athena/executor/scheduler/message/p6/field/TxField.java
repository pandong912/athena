package com.ccb.athena.executor.scheduler.message.p6.field;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ccb.athena.executor.scheduler.common.EbcdicUtil;
import com.ccb.athena.executor.scheduler.message.p6.Code;

public class TxField extends Field {

	private static String STRING_DEFAULT = " ";

	private static String NUMBER_DEFAULT = "0";

	private String barPro;// 栏位属性

	private int encoding;// 编码方式

	private String encodeType;// 1.根据字段的长度进行补齐，然后进行编码 2.根据编码类型进行编码，不进行补齐操作 ,用两个字节的长度表示数据的长度

	private int length; // 字段长度

	private int decimalLen;// 如果是数字，标示小数部分长度，字符或者整数，该字段为0

	private boolean polishing;// 是否需要补齐

	public int length() {
		return this.length;
	}

	public boolean isEmpty(Document doc) {
		Node node = doc.selectSingleNode(this.getPath());
		if (node == null) {
			return true;
		}
		if (node.getText().equals("")) {
			return true;
		}
		return false;
	}

	@Override
	public byte[] convert(Document doc) {
		byte[] encodeValue = null;
		String decodeValue = "";
		Node node = doc.selectSingleNode(this.getPath());
		if (node != null) {
			decodeValue = node.getText();
		}
		if (this.barPro.equals("C")) {// 字符串
			encodeValue = encodeString(decodeValue);
		} else if (this.barPro.equals("N")) {// 数字
			encodeValue = encodeNumber(decodeValue);
		}
		return encodeValue;
	}

	private byte[] encodeString(String value) {
		byte[] result = null;
		if (polishing) {
			int valueLen = value.length();
			int differ = length - valueLen;
			if (differ > 0) {
				value += copyString(differ, STRING_DEFAULT);
			} else if (differ < 0) {
				value = value.substring(0, length);
			}
		}
		if (encoding == 0) {// unicode编码
			result = value.getBytes();
		} else if (encoding == 1) {// ebcdic编码
			result = EbcdicUtil.ascii2ebcdic(value);

		}
		return result;
	}

	private byte[] encodeNumber(String value) {
		byte[] result = null;

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
		if (decimalPartLen < decimalLen) {
			int differ = decimalLen - decimalPartLen;
			decimalPart += copyString(differ, NUMBER_DEFAULT);
		}

		String encodeValue = intPart + decimalPart;
		int encodeLen = encodeValue.length();
		if (polishing) {
			int differ = length - encodeLen;
			if (differ > 0) {
				encodeValue = copyString(differ, NUMBER_DEFAULT) + encodeValue;// 在前面补齐
			} else {
				encodeValue = StringUtils.substring(encodeValue, 0, length);// 从前截取
			}
		}
		if (encoding == 0) {// unicode编码

			result = encodeValue.getBytes();
		} else if (encoding == 1) {// ebcdic编码
			result = EbcdicUtil.ascii2ebcdic(encodeValue);
		}

		if (signFlag != 0) {
			result[result.length - 1] = (byte) ((result[result.length - 1] & 0x0F) | signFlag);

		}
		return result;
	}

	public LinkedHashMap<String, String> deconvert(byte[] value, int startPos) {
		String decodeValue = "";
		int pos = startPos;
		byte[] dataBytes = null;
		if (this.polishing) {// 固定长度,根据字段的长度取值
			dataBytes = new byte[this.length];
			System.arraycopy(value, pos, dataBytes, 0, this.length);
			pos += this.length;
		} else {// 非固定长度，前面有两个字节表示长度
			byte[] lengthByte = new byte[2];
			System.arraycopy(value, pos, lengthByte, 0, 2);
			pos += 2;
			// 把长度byte解码 获取长度
			lengthByte = Code.EBCDICToASCII(lengthByte);

			int length = lengthByte[0] & 0xff << 8 + lengthByte[1];

			dataBytes = new byte[length];
			System.arraycopy(value, pos, lengthByte, 0, length);
		}
		if (this.getBarPro().equals("C")) {
			decodeValue = decodeString(dataBytes);
		} else if (this.getBarPro().equals("N")) {
			decodeValue = decodeNumber(dataBytes);
		}
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		result.put(this.path, decodeValue);
		result.put(INDEX_KEY, String.valueOf(pos));
		return result;
	}

	private String decodeString(byte[] value) {
		String data = "";
		if (encoding == 0) {
			data = new String(value);
		} else if (encoding == 1) {
			data = EbcdicUtil.ebcdic2ascii(value);
			data = new String(value);
		}
		return data;
	}

	private String decodeNumber(byte[] value) {
		// 数字还有小数位需要替换
		String data = "";
		if (encoding == 0) {
			data = new String(value);
		} else if (encoding == 1) {
			data = EbcdicUtil.ebcdic2ascii(value);
			data = new String(value);
		}
		return data;
	}

	private static String copyString(int count, String s) {
		StringBuilder sb = new StringBuilder();
		if (count <= 0) {
			return sb.toString();
		}
		for (int i = 0; i < count; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public String getBarPro() {
		return barPro;
	}

	public void setBarPro(String barPro) {
		this.barPro = barPro;
	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDecimalLen() {
		return decimalLen;
	}

	public void setDecimalLen(int decimalLen) {
		this.decimalLen = decimalLen;
	}

	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}

	public boolean isPolishing() {
		return polishing;
	}

	public void setPolishing(boolean polishing) {
		this.polishing = polishing;
	}

}
