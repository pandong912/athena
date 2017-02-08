package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.Response;
import com.ccb.athena.converter.config.entities.ResponseConfig;

/**
 * 
 * 类名：ResponseFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月8日 下午5:56:52
 * 修改时间：2017年1月8日 下午5:56:52 文件版本：@version 1.0.0
 *
 */
public interface ResponseFactory {
	Response buildResponse(ResponseConfig responseConfig);
}
