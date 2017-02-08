/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.ccb.athena.converter.dispatcher.mapper;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.converter.AthenaConstants;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class CompositeActionMapper implements ActionMapper {

	private static final Logger LOG = LoggerFactory.getLogger(CompositeActionMapper.class);

	protected List<ActionMapper> actionMappers = new LinkedList<ActionMapper>();

	@Inject
	public CompositeActionMapper(Container container, @Inject(value = AthenaConstants.ATHENA_MAPPER_COMPOSITE) String list) {
		if (list != null) {
			String[] arr = list.split(",");
			for (String name : arr) {
				Object obj = container.getInstance(ActionMapper.class, name);
				if (obj != null) {
					actionMappers.add((ActionMapper) obj);
				}
			}
		}
	}

	public ActionMapping getMapping(String serviceId, JSONObject data) {

		for (ActionMapper actionMapper : actionMappers) {
			ActionMapping actionMapping = actionMapper.getMapping(serviceId, data);
			if (actionMapping != null) {
				return actionMapping;
			}
		}
		LOG.info("not fount actionMapping!");
		return null;
	}

}
