/*
 * Copyright 2002-2006,2009 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ccb.athena.converter.xwork2.config.providers;

import java.util.ArrayList;
import java.util.List;

import com.ccb.athena.converter.config.entities.FilterConfig;
import com.ccb.athena.converter.config.entities.FilterMapping;
import com.ccb.athena.converter.config.entities.FilterStackConfig;
import com.ccb.athena.converter.config.entities.PackageConfig;
import com.ccb.athena.converter.filter.Filter;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


/**
 * Builds a list of interceptors referenced by the refName in the supplied PackageConfig.
 *
 * @author Mike
 * @author Rainer Hermanns
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FilterBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FilterBuilder.class);


    /**
     * Builds a list of interceptors referenced by the refName in the supplied PackageConfig (InterceptorMapping object).
     *
     * @param interceptorLocator
     * @param refName
     * @param refParams
     * @return list of interceptors referenced by the refName in the supplied PackageConfig (InterceptorMapping object).
     * @throws ConfigurationException
     */
    public static List<FilterMapping> constructFilterReference(PackageConfig.Builder context, String refName, ObjectFactory objectFactory) throws ConfigurationException {
        Object referencedConfig = context.getFilterConfig(refName);
        List<FilterMapping> result = new ArrayList<FilterMapping>();

        if (referencedConfig == null) {
            throw new ConfigurationException("Unable to find interceptor class referenced by ref-name " + refName);
        } else {
            if (referencedConfig instanceof FilterConfig) {
            	FilterConfig config = (FilterConfig) referencedConfig;
                Filter filter = null;
                try {

                    filter = objectFactory.buildFilter(config, null);
                    result.add(new FilterMapping(refName, filter));
                } catch (ConfigurationException ex) {
                    if (LOG.isWarnEnabled()) {
                	    LOG.warn("Unable to load config class #0 at #1 probably due to a missing jar, which might be fine if you never plan to use the #2 interceptor",
                            config.getClassName(), ex.getLocation().toString(), config.getName());
                    }
                    LOG.error("Actual exception", ex);
                }

            } else if (referencedConfig instanceof FilterStackConfig) {
            	FilterStackConfig stackConfig = (FilterStackConfig) referencedConfig;
            	result.addAll(stackConfig.getFilters());
//                if ((refParams != null) && (refParams.size() > 0)) {
//                    result = constructParameterizedInterceptorReferences(interceptorLocator, stackConfig, refParams, objectFactory);
//                } else {
//                }
            } else {
                LOG.error("Got unexpected type for filter " + refName + ". Got " + referencedConfig);
            }
        }

        return result;
    }
    
}
