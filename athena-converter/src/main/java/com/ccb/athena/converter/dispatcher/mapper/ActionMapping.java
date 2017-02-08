package com.ccb.athena.converter.dispatcher.mapper;

public class ActionMapping {

	private String srcCode;
	private String destCode;

	/**
	 * @param srcCode
	 * @param destCode
	 */
	public ActionMapping(String srcCode, String destCode) {
		this.srcCode = srcCode;
		this.destCode = destCode;
	}
	public ActionMapping() {
		
	}

	public String getSrcCode() {
		return srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destCode == null) ? 0 : destCode.hashCode());
		result = prime * result + ((srcCode == null) ? 0 : srcCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionMapping other = (ActionMapping) obj;
		if (destCode == null) {
			if (other.destCode != null)
				return false;
		} else if (!destCode.equals(other.destCode))
			return false;
		if (srcCode == null) {
			if (other.srcCode != null)
				return false;
		} else if (!srcCode.equals(other.srcCode))
			return false;
		return true;
	}
}
