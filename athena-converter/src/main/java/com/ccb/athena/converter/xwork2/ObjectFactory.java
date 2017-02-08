package com.ccb.athena.converter.xwork2;

import java.io.Serializable;
import java.util.Map;

import com.ccb.athena.converter.components.Mapping;
import com.ccb.athena.converter.components.Request;
import com.ccb.athena.converter.components.Response;
import com.ccb.athena.converter.components.Segment;
import com.ccb.athena.converter.config.entities.FilterConfig;
import com.ccb.athena.converter.config.entities.MappingConfig;
import com.ccb.athena.converter.config.entities.RequestConfig;
import com.ccb.athena.converter.config.entities.ResponseConfig;
import com.ccb.athena.converter.config.entities.SegmentConfig;
import com.ccb.athena.converter.factory.FilterFactory;
import com.ccb.athena.converter.factory.MappingFactory;
import com.ccb.athena.converter.factory.RequestFactory;
import com.ccb.athena.converter.factory.ResponseFactory;
import com.ccb.athena.converter.factory.SegmentFactory;
import com.ccb.athena.converter.filter.Filter;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.Inject;
import com.ccb.athena.converter.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.factory.ActionFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class ObjectFactory implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(ObjectFactory.class);

	private transient ClassLoader ccl;
	private Container container;

	private ActionFactory actionFactory;
	private FilterFactory filterFactory;
	private SegmentFactory segmentFactory;
	private MappingFactory mappingFactory;
	private RequestFactory requestFactory;
	private ResponseFactory responseFactory;

	@Inject(value = "objectFactory.classloader", required = false)
	public void setClassLoader(ClassLoader cl) {
		this.ccl = cl;
	}

	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	@Inject
	public void setActionFactory(ActionFactory actionFactory) {
		this.actionFactory = actionFactory;
	}

	@Inject
	public void setFilterFactory(FilterFactory filterFactory) {
		this.filterFactory = filterFactory;
	}

	@Inject
	public void setSegmentFactory(SegmentFactory segmentFactory) {
		this.segmentFactory = segmentFactory;
	}

	@Inject
	public void setRequestFactory(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Inject
	public void setResponseFactory(ResponseFactory responseFactory) {
		this.responseFactory = responseFactory;
	}

	@Inject
	public void setMappingFactory(MappingFactory mappingFactory) {
		this.mappingFactory = mappingFactory;
	}

	/**
	 * Allows for ObjectFactory implementations that support Actions without
	 * no-arg constructors.
	 *
	 * @return true if no-arg constructor is required, false otherwise
	 */
	public boolean isNoArgConstructorRequired() {
		return true;
	}

	/**
	 * Utility method to obtain the class matched to className. Caches look ups
	 * so that subsequent lookups will be faster.
	 *
	 * @param className
	 *            The fully qualified name of the class to return
	 * @return The class itself
	 * @throws ClassNotFoundException
	 */
	public Class getClassInstance(String className) throws ClassNotFoundException {
		if (ccl != null) {
			return ccl.loadClass(className);
		}

		return ClassLoaderUtil.loadClass(className, this.getClass());
	}

	/**
	 * Build an instance of the action class to handle a particular request (eg.
	 * web request)
	 * 
	 * @param actionName
	 *            the name the action configuration is set up with in the
	 *            configuration
	 * @param namespace
	 *            the namespace the action is configured in
	 * @param config
	 *            the action configuration found in the config for the
	 *            actionName / namespace
	 * @param extraContext
	 *            a Map of extra context which uses the same keys as the
	 *            {@link com.opensymphony.xwork2.ActionContext}
	 * @return instance of the action class to handle a web request
	 * @throws Exception
	 */
	public Object buildAction(String actionName, String namespace, ActionConfig config,
			Map<String, Object> extraContext) throws Exception {
		return actionFactory.buildAction(actionName, namespace, config, extraContext);
	}

	/**
	 * Build a generic Java object of the given type.
	 *
	 * @param clazz
	 *            the type of Object to build
	 * @param extraContext
	 *            a Map of extra context which uses the same keys as the
	 *            {@link com.opensymphony.xwork2.ActionContext}
	 */
	public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
		return clazz.newInstance();
	}

	/**
	 * @param obj
	 */
	protected Object injectInternalBeans(Object obj) {
		if (obj != null && container != null) {
			container.inject(obj);
		}
		return obj;
	}

	/**
	 * Build a generic Java object of the given type.
	 *
	 * @param className
	 *            the type of Object to build
	 * @param extraContext
	 *            a Map of extra context which uses the same keys as the
	 *            {@link com.opensymphony.xwork2.ActionContext}
	 */
	public Object buildBean(String className, Map<String, Object> extraContext) throws Exception {
		return buildBean(className, extraContext, true);
	}

	/**
	 * Build a generic Java object of the given type.
	 *
	 * @param className
	 *            the type of Object to build
	 * @param extraContext
	 *            a Map of extra context which uses the same keys as the
	 *            {@link com.opensymphony.xwork2.ActionContext}
	 */
	public Object buildBean(String className, Map<String, Object> extraContext, boolean injectInternal)
			throws Exception {
		Class clazz = getClassInstance(className);
		Object obj = buildBean(clazz, extraContext);
		if (injectInternal) {
			injectInternalBeans(obj);
		}
		return obj;
	}

	public Filter buildFilter(FilterConfig filterConfig, Map<String, String> filterRefParams)
			throws ConfigurationException {
		return filterFactory.buildFilter(filterConfig);
	}

	public Segment buildSegment(SegmentConfig segmentConfig, Map<String, String> segmentRefParams)
			throws ConfigurationException {
		return segmentFactory.buildSegment(segmentConfig);
	}

	public Request buildRequest(RequestConfig requestConfig, Map<String, Object> extraContext) throws Exception {
		return requestFactory.buildRequest(requestConfig);
	}

	public Response buildResponse(ResponseConfig responseConfig, Map<String, Object> extraContext) throws Exception {
		return responseFactory.buildResponse(responseConfig);
	}

	public Mapping buildMapping(MappingConfig mappingConfig, Map<String, Object> extraContext) throws Exception {
		return mappingFactory.buildMapping(mappingConfig);
	}
}
