/**
 * 
 */
package com.ccb.athena.converter.dispatcher.mapper;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.AthenaConstants;
import com.ccb.athena.converter.tool.JsonParseTool;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author congyang
 *
 */
public class CustomActionMapper implements ActionMapper {

	private static final Logger LOG = LoggerFactory.getLogger(CustomActionMapper.class);
	
	private static Set<String> set = null;
	protected Container container;

	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	/*
	 * @Inject public CompositeActionMapper(Container container, @Inject(value = AthenaConstants.ATHENA_MAPPER_CUSTOM) String list) { if (list != null) { String[] arr = list.split(","); for (String name : arr) { Object obj = container.getInstance(ActionMapper.class, name); if (obj != null) { actionMappers.add((ActionMapper) obj); } } } }
	 */

	@Inject(AthenaConstants.ATHENA_MAPPER_CUSTOM)
	public void setActionMappings(String list) {
		if (list != null) {
			String[] arr = list.split(",");
			for (String name : arr) {
				set = new HashSet<String>();
				set.add(name);
			}
		}
	}

	@Override
	public ActionMapping getMapping(String serviceId, JSONObject data) {
		String destinationCode = null;
		if (set.contains(serviceId)) {
			switch (serviceId) {
			case "S01832100":
				String menoyF;
				try {
					menoyF = JsonParseTool.getDataFromRelativePath(data, "HT_MONEY");
					int mon = Integer.parseInt(menoyF);
					if (mon < 0) {
						destinationCode = "A03112901";
					} else {
						destinationCode = "A03112902";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ActionMapping actionMapping = new ActionMapping(serviceId, destinationCode);
			return actionMapping;
		} else {
			return null;
		}
	}

}
