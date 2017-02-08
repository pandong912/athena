package com.ccb.athena.executor.scheduler.common;

import java.io.File;

public class Constants {
	
	public static final String ENCODING = "UTF-8";
	
	public static final String PATH_SEPARATOR  = File.pathSeparator;
	
	public static final String LINE_SEPARATOR  = System.getProperty("line.separator");
	
	public static final byte[] LINE_SEPARATOR_BYTES  = LINE_SEPARATOR.getBytes();
}
