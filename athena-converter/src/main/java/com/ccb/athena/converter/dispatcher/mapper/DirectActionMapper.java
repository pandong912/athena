/**
 * 
 */
package com.ccb.athena.converter.dispatcher.mapper;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.AthenaConstants;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author congyang
 *
 */
public class DirectActionMapper implements ActionMapper {
	
	private static final Logger LOG = LoggerFactory.getLogger(DirectActionMapper.class);

	private Map<String, ActionMapping> actionMappings = new HashMap<String, ActionMapping>();

	protected Container container;

	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	@Inject(AthenaConstants.ATHENA_MAPPER_DIRECT)
	public void setActionMappings(String list) {
		if (list != null) {
			String[] mappers = list.split(",");
			for (String mapper : mappers) {
				ActionMapping actionMapping = new ActionMapping(mapper, mapper);
				actionMappings.put(mapper, actionMapping);
			}
		}
	}

	@Override
	public ActionMapping getMapping(String code, JSONObject data) {
		return actionMappings.get(code);
	}
}
