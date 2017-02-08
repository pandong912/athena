package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.config.entities.FilterConfig;
import com.ccb.athena.converter.filter.Filter;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.inject.Inject;

public class DefaultFilterFactory implements FilterFactory {

    private ObjectFactory objectFactory;

    @Inject
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
	
	public Filter buildFilter(FilterConfig filterConfig) throws ConfigurationException {		
		String filterClassName = filterConfig.getClassName();
		
        String message;
        Throwable cause;

        try {
            // interceptor instances are long-lived and used across user sessions, so don't try to pass in any extra context
            Filter filter = (Filter) objectFactory.buildBean(filterClassName, null);
//          reflectionProvider.setProperties(params, interceptor);
            filter.init();

            return filter;
        } catch (InstantiationException e) {
            cause = e;
            message = "Unable to instantiate an instance of Interceptor class [" + filterClassName + "].";
        } catch (IllegalAccessException e) {
            cause = e;
            message = "IllegalAccessException while attempting to instantiate an instance of Interceptor class [" + filterClassName + "].";
        } catch (ClassCastException e) {
            cause = e;
            message = "Class [" + filterClassName + "] does not implement com.opensymphony.xwork2.interceptor.Interceptor";
        } catch (Exception e) {
            cause = e;
            message = "Caught Exception while registering Interceptor class " + filterClassName;
        } catch (NoClassDefFoundError e) {
            cause = e;
            message = "Could not load class " + filterClassName + ". Perhaps it exists but certain dependencies are not available?";
        }

        throw new ConfigurationException(message, cause, filterConfig);
	}

}
