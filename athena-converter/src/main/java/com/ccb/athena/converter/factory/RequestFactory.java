package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.Request;
import com.ccb.athena.converter.config.entities.RequestConfig;

/**
 * 
 * 类名：RequestFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 下午5:55:47 修改时间：2017年1月6日
 * 下午5:55:47 文件版本：@version 1.0.0
 *
 */
public interface RequestFactory {
	Request buildRequest(RequestConfig requestConfig);
}
