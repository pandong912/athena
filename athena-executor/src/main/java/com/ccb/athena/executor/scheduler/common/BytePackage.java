package com.ccb.athena.executor.scheduler.common;

/**
 * 基于字节数组的字节包类，封装了字节操作<br>
 * <br>
 * 提供追加一个字节<br>
 * 提供追加字节数组<br>
 * 提供追加BytePackage<br>
 * 提供返回字节数组<br>
 * 提供返回长度<br>
 * 提供清空<br>
 * 
 * 
 * 
 */
public class BytePackage {
	private byte[] m_bytes = new byte[128];
	private int m_length;

	public BytePackage() {
	}

	public BytePackage(byte[] bytes) {
		this.append(bytes);
	}

	public BytePackage append(byte b) {
		this.ensureLength(1);
		this.m_bytes[this.m_length++] = b;
		return this;
	}

	public void append(byte[] bytes) {
		this.append(bytes, 0, bytes.length);
	}

	public void append(BytePackage pack) {
		if (pack == null) {
			return;
		}
		byte[] bytes = pack.getBytes();
		this.append(bytes, 0, bytes.length);
	}

	public void append(byte[] bytes, int start, int length) {
		if (bytes == null)
			return;

		this.ensureLength(this.m_length + length);

		System.arraycopy(bytes, start, this.m_bytes, this.m_length, length);
		this.m_length += length;
	}

	public void insert(byte[] bytes, int pos) {
		this.insert(bytes, 0, bytes.length, pos);
	}

	public void insert(byte[] bytes, int start, int length, int pos) {
		if (bytes == null)
			return;

		this.ensureLength(this.m_length + length);

		System.arraycopy(this.m_bytes, pos, this.m_bytes, pos + length, this.m_length - pos);
		System.arraycopy(bytes, start, this.m_bytes, pos, length);

		this.m_length += length;
	}

	public byte[] getBytes() {
		return this.getBytes(0, this.m_length);
	}

	public byte[] getBytes(int start, int len) {
		byte[] bytes = new byte[len];
		System.arraycopy(this.m_bytes, start, bytes, 0, len);
		return bytes;
	}

	public int getLength() {
		return this.m_length;
	}

	public void backspace(int backspace) {
		this.m_length -= backspace;
	}

	public void delete(int pos) {
		this.delete(pos, 1);
	}

	public void delete(int start, int length) {
		if (length > this.m_length - start)
			length = this.m_length - start;

		System.arraycopy(this.m_bytes, start + length, this.m_bytes, start, this.m_length - length);

		this.m_length -= length;
	}

	public void truncationAt(int pos) {
		if (pos < this.m_length)
			this.m_length = pos;
	}

	private void ensureLength(int len) {
		if (this.m_bytes.length < len) {
			byte[] bytes = new byte[len * 2];
			System.arraycopy(this.m_bytes, 0, bytes, 0, this.m_length);
			this.m_bytes = bytes;
		}
	}
}
