package com.ccb.athena.converter.factory;

import java.util.LinkedList;
import java.util.List;

import com.ccb.athena.converter.components.Mapping;
import com.ccb.athena.converter.components.Request;
import com.ccb.athena.converter.components.Response;
import com.ccb.athena.converter.config.entities.FilterMapping;
import com.ccb.athena.converter.config.entities.MappingConfig;
import com.ccb.athena.converter.config.entities.RequestConfig;
import com.ccb.athena.converter.config.entities.ResponseConfig;
import com.ccb.athena.converter.dispatcher.mapper.ActionMapping;
import com.ccb.athena.converter.filter.Filter;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 
 * 类名：DefaultMappingFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月8日 下午9:09:24
 * 修改时间：2017年1月8日 下午9:09:24 文件版本：@version 1.0.0
 *
 */
public class DefaultMappingFactory implements MappingFactory {

	private ObjectFactory objectFactory;

	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	@Override
	public Mapping buildMapping(MappingConfig mappingConfig) {
		Mapping mapping = new Mapping();
		ActionMapping actionMapping = new ActionMapping();
		String source = mappingConfig.getSource();
		String destination = mappingConfig.getName();
		actionMapping.setSrcCode(source);
		actionMapping.setDestCode(destination);

		Request request = new Request();
		Response response = new Response();

		mapping.setActionMapping(actionMapping);
		List<FilterMapping> filters = mappingConfig.getFilters();
		List<Filter> iFilters = new LinkedList<Filter>();
		for (int i = 0; i < filters.size(); i++) {
			Filter filter = filters.get(i).getFilter();
			iFilters.add(filter);
		}
		mapping.setFilters(iFilters);
		RequestConfig requestConfig = mappingConfig.getRequestConfig();
		ResponseConfig responseConfig = mappingConfig.getResponseConfig();
		try {
			// 调用ResponseConfig 转Response 方法
			request = objectFactory.buildRequest(requestConfig, null);
			response = objectFactory.buildResponse(responseConfig, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapping.setRequest(request);
		mapping.setResponse(response);

		// this.configuration.addMapping(actionMapping, mapping);

		return mapping;
	}

}
