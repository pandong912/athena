package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.config.entities.FilterConfig;
import com.ccb.athena.converter.filter.Filter;
import com.opensymphony.xwork2.config.ConfigurationException;

public interface FilterFactory {
	
	Filter buildFilter(FilterConfig filterConfig) throws ConfigurationException;

}
