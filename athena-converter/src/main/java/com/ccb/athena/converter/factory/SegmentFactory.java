package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.Segment;
import com.ccb.athena.converter.config.entities.SegmentConfig;
import com.opensymphony.xwork2.config.ConfigurationException;

/**
 * 
 * 类名：SegmentFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 下午2:25:44 修改时间：2017年1月6日
 * 下午2:25:44 文件版本：@version 1.0.0
 *
 */
public interface SegmentFactory {
	
	Segment buildSegment(SegmentConfig segmentConfig) throws ConfigurationException;

}
