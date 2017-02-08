package com.ccb.athena.converter.components;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.dispatcher.mapper.ActionMapping;
import com.ccb.athena.converter.filter.Filter;

public class Mapping {

	private ActionMapping actionMapping;
	private List<Filter> filters;
	private Request request;
	private Response response;

	public ActionMapping getActionMapping() {
		return actionMapping;
	}

	public void setActionMapping(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public boolean doFilters(JSONObject jsonObject) throws Exception {
		for (Filter filter : filters) {
			if (filter.doFilter(jsonObject)) {
				return false;
			}
		}
		return true;
	}
}
