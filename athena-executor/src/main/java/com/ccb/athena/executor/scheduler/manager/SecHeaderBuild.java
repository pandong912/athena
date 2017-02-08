package com.ccb.athena.executor.scheduler.manager;



public interface SecHeaderBuild {
	
	public byte[] buildSecHeader(SecData secData,String txCode,String txType,String traceId);
	
	public SecData buildSecData(byte[] header);

}
