package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.Group;
import com.ccb.athena.converter.components.Segment;
import com.ccb.athena.converter.config.entities.SegmentConfig;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.inject.Inject;

/**
 * 
 * 类名：DefaultFilterFacctory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 上午10:28:31
 * 修改时间：2017年1月6日 上午10:28:31 文件版本：@version 1.0.0
 * 
 */
public class DefaultSegmentFactory implements SegmentFactory {
	
    private ObjectFactory objectFactory;

    @Inject
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

	@SuppressWarnings("resource")
	@Override
	public Segment buildSegment(SegmentConfig segmentConfig) throws ConfigurationException {
		String name = segmentConfig.getName();
		String root = segmentConfig.getRoot();
		Group group = segmentConfig.getGroup();
		
        String message = "";
        Throwable cause;

		try {
			Segment segment = new Segment(name, root, group, configuration);			
			return segment;
		} catch (Exception e) {	       
			throw new ConfigurationException(message, cause, segmentConfig);
		}
    }

}
