package com.ccb.athena.converter.dispatcher.mapper;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.AthenaConstants;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.Inject;

public class DefaultActionMapper implements ActionMapper {

	private Map<String, ActionMapping> actionMappings = new HashMap<String, ActionMapping>();

	protected Container container;

	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	@Inject(AthenaConstants.DEFAULT_MAPPER_CONFIGURATION)
	public void setActionMappings(String list) {
		if (list != null) {
			String[] mappers = list.split(",");
			for (String mapper : mappers) {
				String[] thisMapper = mapper.split(":");
				if ((thisMapper != null) && (thisMapper.length == 2)) {
					String source = thisMapper[0].trim();
					String destination = thisMapper[1].trim();
					ActionMapping actionMapping = new ActionMapping(source, destination);
					actionMappings.put(source, actionMapping);
				}
			}
		}
	}

	@Override
	public ActionMapping getMapping(String code, JSONObject data) {
		return actionMappings.get(code);
	}
}
