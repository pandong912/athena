package com.ccb.athena.converter.xwork2.config.providers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ccb.athena.converter.config.entities.FilterConfig;
import com.ccb.athena.converter.config.entities.FilterMapping;
import com.ccb.athena.converter.config.entities.FilterStackConfig;
import com.ccb.athena.converter.config.entities.MappingConfig;
import com.ccb.athena.converter.config.entities.PackageConfig;
import com.ccb.athena.converter.config.entities.RequestConfig;
import com.ccb.athena.converter.config.entities.ResponseConfig;
import com.ccb.athena.converter.config.entities.SegmentConfig;
import com.ccb.athena.converter.config.entities.SegmentMapping;
import com.ccb.athena.converter.config.entities.SegmentStackConfig;
import com.ccb.athena.converter.xwork2.FileManager;
import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.config.ConfigurationProvider;
import com.ccb.athena.converter.xwork2.config.impl.LocatableFactory;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Inject;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.ccb.athena.converter.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.providers.XmlHelper;
import com.opensymphony.xwork2.util.ClassPathFinder;
import com.opensymphony.xwork2.util.DomHelper;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.location.Location;
import com.opensymphony.xwork2.util.location.LocationUtils;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class XmlConfigurationProvider implements ConfigurationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(XmlConfigurationProvider.class);

	private List<Document> documents;
	private Set<String> includedFileNames;
	private String configFileName;
	private ObjectFactory objectFactory;

	private Set<String> loadedFileUrls = new HashSet<String>();
	private boolean errorIfMissing;
	private Map<String, String> dtdMappings;
	private Configuration configuration;
	private boolean throwExceptionOnDuplicateBeans = true;
	private Map<String, Element> declaredPackages = new HashMap<String, Element>();

	private FileManager fileManager;

	public XmlConfigurationProvider() {
		this("xwork.xml", true);
	}

	public XmlConfigurationProvider(String filename) {
		this(filename, true);
	}

	public XmlConfigurationProvider(String filename, boolean errorIfMissing) {
		this.configFileName = filename;
		this.errorIfMissing = errorIfMissing;

		Map<String, String> mappings = new HashMap<String, String>();
		mappings.put("-//Apache Struts//XWork 2.3//EN", "xwork-2.3.dtd");
		setDtdMappings(mappings);
	}

	public void setThrowExceptionOnDuplicateBeans(boolean val) {
		this.throwExceptionOnDuplicateBeans = val;
	}

	public void setDtdMappings(Map<String, String> mappings) {
		this.dtdMappings = Collections.unmodifiableMap(mappings);
	}

	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	@Inject
	public void setFileManagerFactory(FileManagerFactory fileManagerFactory) {
		this.fileManager = fileManagerFactory.getFileManager();
	}

	/**
	 * Returns an unmodifiable map of DTD mappings
	 */
	public Map<String, String> getDtdMappings() {
		return dtdMappings;
	}

	public void init(Configuration configuration) {
		this.configuration = configuration;
		this.includedFileNames = configuration.getLoadedFileNames();
		loadDocuments(configFileName);
	}

	public void destroy() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof XmlConfigurationProvider)) {
			return false;
		}

		final XmlConfigurationProvider xmlConfigurationProvider = (XmlConfigurationProvider) o;

		if ((configFileName != null) ? (!configFileName.equals(xmlConfigurationProvider.configFileName)) : (xmlConfigurationProvider.configFileName != null)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((configFileName != null) ? configFileName.hashCode() : 0);
	}

	private void loadDocuments(String configFileName) {
		try {
			loadedFileUrls.clear();
			documents = loadConfigurationFiles(configFileName, null);
		} catch (ConfigurationException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationException("Error loading configuration file " + configFileName, e);
		}
	}

	public void register(ContainerBuilder containerBuilder, Properties props) throws ConfigurationException {
		if (LOG.isInfoEnabled()) {
			LOG.info("Parsing configuration file [" + configFileName + "]");
		}
		Map<String, Node> loadedBeans = new HashMap<String, Node>();
		for (Document doc : documents) {
			Element rootElement = doc.getDocumentElement();
			NodeList children = rootElement.getChildNodes();
			int childSize = children.getLength();

			for (int i = 0; i < childSize; i++) {
				Node childNode = children.item(i);

				if (childNode instanceof Element) {
					Element child = (Element) childNode;

					final String nodeName = child.getNodeName();

					if ("bean".equals(nodeName)) {
						String type = child.getAttribute("type");
						String name = child.getAttribute("name");
						String impl = child.getAttribute("class");
						String onlyStatic = child.getAttribute("static");
						String scopeStr = child.getAttribute("scope");
						boolean optional = "true".equals(child.getAttribute("optional"));
						Scope scope = Scope.SINGLETON;
						if ("default".equals(scopeStr)) {
							scope = Scope.DEFAULT;
						} else if ("request".equals(scopeStr)) {
							scope = Scope.REQUEST;
						} else if ("session".equals(scopeStr)) {
							scope = Scope.SESSION;
						} else if ("singleton".equals(scopeStr)) {
							scope = Scope.SINGLETON;
						} else if ("thread".equals(scopeStr)) {
							scope = Scope.THREAD;
						}

						if (StringUtils.isEmpty(name)) {
							name = Container.DEFAULT_NAME;
						}

						try {
							Class cimpl = ClassLoaderUtil.loadClass(impl, getClass());
							Class ctype = cimpl;
							if (StringUtils.isNotEmpty(type)) {
								ctype = ClassLoaderUtil.loadClass(type, getClass());
							}
							if ("true".equals(onlyStatic)) {
								// Force loading of class to detect no class def found exceptions
								cimpl.getDeclaredClasses();
								containerBuilder.injectStatics(cimpl);
							} else {
								if (containerBuilder.contains(ctype, name)) {
									Location loc = LocationUtils.getLocation(loadedBeans.get(ctype.getName() + name));
									if (throwExceptionOnDuplicateBeans) {
										throw new ConfigurationException("Bean type " + ctype + " with the name " + name + " has already been loaded by " + loc, child);
									}
								}

								// Force loading of class to detect no class def found exceptions
								cimpl.getDeclaredConstructors();

								if (LOG.isDebugEnabled()) {
									LOG.debug("Loaded type:" + type + " name:" + name + " impl:" + impl);
								}
								containerBuilder.factory(ctype, name, new LocatableFactory(name, ctype, cimpl, scope, childNode), scope);
							}
							loadedBeans.put(ctype.getName() + name, child);
						} catch (Throwable ex) {
							if (!optional) {
								throw new ConfigurationException("Unable to load bean: type:" + type + " class:" + impl, ex, childNode);
							} else {
								if (LOG.isDebugEnabled()) {
									LOG.debug("Unable to load optional class: #0", impl);
								}
							}
						}
					} else if ("constant".equals(nodeName)) {
						String name = child.getAttribute("name");
						String value = child.getAttribute("value");
						props.setProperty(name, value);
					}
				}
			}
		}
	}

	public void loadPackages() throws ConfigurationException {
		List<Element> reloads = new ArrayList<Element>();

		for (Document doc : documents) {
			Element rootElement = doc.getDocumentElement();
			NodeList children = rootElement.getChildNodes();
			int childSize = children.getLength();

			for (int i = 0; i < childSize; i++) {
				Node childNode = children.item(i);

				if (childNode instanceof Element) {
					Element child = (Element) childNode;

					final String nodeName = child.getNodeName();

					if ("package".equals(nodeName)) {
						PackageConfig cfg = addPackage(child);
						if (cfg.isNeedsRefresh()) {
							reloads.add(child);
						}
					}
				}
			}
		}

		documents.clear();
		declaredPackages.clear();
		configuration = null;
	}

	/**
	 * Tells whether the ConfigurationProvider should reload its configuration. This method should only be called if ConfigurationManager.isReloadingConfigs() is true.
	 *
	 * @return true if the file has been changed since the last time we read it
	 */
	public boolean needsReload() {

		for (String url : loadedFileUrls) {
			if (fileManager.fileNeedsReloading(url)) {
				return true;
			}
		}
		return false;
	}

	protected void addAction(Element actionElement, PackageConfig.Builder packageContext) throws ConfigurationException {
		String name = actionElement.getAttribute("name");
		String className = actionElement.getAttribute("class");
		String methodName = actionElement.getAttribute("method");
		Location location = DomHelper.getLocationObject(actionElement);

		if (location == null) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("location null for " + className);
			}
		}
		// methodName should be null if it's not set
		methodName = (methodName.trim().length() > 0) ? methodName.trim() : null;

		// if there isnt a class name specified for an <action/> then try to
		// use the default-class-ref from the <package/>
		if (StringUtils.isEmpty(className)) {
			// if there is a package default-class-ref use that, otherwise use action support
			/*
			 * if (StringUtils.isNotEmpty(packageContext.getDefaultClassRef())) { className = packageContext.getDefaultClassRef(); } else { className = ActionSupport.class.getName(); }
			 */

		} else {
			if (!verifyAction(className, name, location)) {
				if (LOG.isErrorEnabled()) {
					LOG.error("Unable to verify action [#0] with class [#1], from [#2]", name, className, location);
				}
				return;
			}
		}

		Set<String> allowedMethods = buildAllowedMethods(actionElement, packageContext);

		ActionConfig actionConfig = new ActionConfig.Builder(packageContext.getName(), name, className).methodName(methodName).addParams(XmlHelper.getParams(actionElement)).addAllowedMethod(allowedMethods).location(location).build();
		packageContext.addActionConfig(name, actionConfig);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Loaded " + (StringUtils.isNotEmpty(packageContext.getNamespace()) ? (packageContext.getNamespace() + "/") : "") + name + " in '" + packageContext.getName() + "' package:" + actionConfig);
		}
	}

	protected boolean verifyAction(String className, String name, Location loc) {
		if (className.indexOf('{') > -1) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action class [" + className + "] contains a wildcard " + "replacement value, so it can't be verified");
			}
			return true;
		}
		try {
			if (objectFactory.isNoArgConstructorRequired()) {
				Class clazz = objectFactory.getClassInstance(className);
				if (!Modifier.isPublic(clazz.getModifiers())) {
					throw new ConfigurationException("Action class [" + className + "] is not public", loc);
				}
				clazz.getConstructor(new Class[] {});
			}
		} catch (ClassNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Class not found for action [#0]", e, className);
			}
			throw new ConfigurationException("Action class [" + className + "] not found", loc);
		} catch (NoSuchMethodException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("No constructor found for action [#0]", e, className);
			}
			throw new ConfigurationException("Action class [" + className + "] does not have a public no-arg constructor", e, loc);
		} catch (RuntimeException ex) {
			// Probably not a big deal, like request or session-scoped Spring 2 beans that need a real request
			if (LOG.isInfoEnabled()) {
				LOG.info("Unable to verify action class [#0] exists at initialization", className);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action verification cause", ex);
			}
		} catch (Exception ex) {
			// Default to failing fast
			if (LOG.isDebugEnabled()) {
				LOG.debug("Unable to verify action class [#0]", ex, className);
			}
			throw new ConfigurationException(ex, loc);
		}
		return true;
	}

	/**
	 * Create a PackageConfig from an XML element representing it.
	 */
	protected PackageConfig addPackage(Element packageElement) throws ConfigurationException {
		String packageName = packageElement.getAttribute("name");
		PackageConfig packageConfig = configuration.getPackageConfig(packageName);
		if (packageConfig != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Package [#0] already loaded, skipping re-loading it and using existing PackageConfig [#1]", packageName, packageConfig);
			}
			return packageConfig;
		}

		PackageConfig.Builder newPackage = buildPackageContext(packageElement);

		if (newPackage.isNeedsRefresh()) {
			return newPackage.build();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Loaded " + newPackage);
		}

		// load the filter for this package
		loadFilters(newPackage, packageElement);
		// load the segment for this package
		loadSegments(newPackage, packageElement);
		// load the mapping for this package
		// loadMappings(newPackage, packageElement);
		// get actions
		NodeList mappingList = packageElement.getElementsByTagName("mapping");

		for (int i = 0; i < mappingList.getLength(); i++) {
			Element mappingElement = (Element) mappingList.item(i);
			addMapping(mappingElement, newPackage);
		}

		// get actions
		NodeList actionList = packageElement.getElementsByTagName("action");

		for (int i = 0; i < actionList.getLength(); i++) {
			Element actionElement = (Element) actionList.item(i);
			addAction(actionElement, newPackage);
		}

		PackageConfig cfg = newPackage.build();
		configuration.addPackageConfig(cfg.getName(), cfg);
		return cfg;
	}

	/**
	 * This method builds a package context by looking for the parents of this new package.
	 * <p/>
	 * If no parents are found, it will return a root package.
	 */
	protected PackageConfig.Builder buildPackageContext(Element packageElement) {
		String abstractVal = packageElement.getAttribute("abstract");
		boolean isAbstract = Boolean.parseBoolean(abstractVal);
		String name = StringUtils.defaultString(packageElement.getAttribute("name"));
		String namespace = StringUtils.defaultString(packageElement.getAttribute("namespace"));
		String strictDMIVal = StringUtils.defaultString(packageElement.getAttribute("strict-method-invocation"));
		boolean strictDMI = Boolean.parseBoolean(strictDMIVal);

		if (StringUtils.isNotEmpty(packageElement.getAttribute("externalReferenceResolver"))) {
			throw new ConfigurationException("The 'externalReferenceResolver' attribute has been removed.  Please use " + "a custom ObjectFactory or Interceptor.", packageElement);
		}

		PackageConfig.Builder cfg = new PackageConfig.Builder(name).namespace(namespace).isAbstract(isAbstract).strictMethodInvocation(strictDMI).location(DomHelper.getLocationObject(packageElement));

		return cfg;
	}

	protected void loadFilters(PackageConfig.Builder context, Element element) throws ConfigurationException {
		NodeList filterList = element.getElementsByTagName("filter");

		for (int i = 0; i < filterList.getLength(); i++) {
			Element filterElement = (Element) filterList.item(i);
			String name = filterElement.getAttribute("name");
			String className = filterElement.getAttribute("class");

			// Map<String, String> params = XmlHelper.getParams(filterElement);
			FilterConfig config = new FilterConfig.Builder(name, className).build();

			context.addFilterConfig(config);
		}

		loadFilterStacks(element, context);
	}

	protected void loadFilterStacks(Element element, PackageConfig.Builder context) throws ConfigurationException {
		NodeList filterStackList = element.getElementsByTagName("filter-stack");

		for (int i = 0; i < filterStackList.getLength(); i++) {
			Element filterStackElement = (Element) filterStackList.item(i);

			FilterStackConfig config = loadFilterStack(filterStackElement, context);

			context.addFilterStackConfig(config);
		}
	}

	protected FilterStackConfig loadFilterStack(Element element, PackageConfig.Builder context) throws ConfigurationException {
		String name = element.getAttribute("name");

		FilterStackConfig.Builder config = new FilterStackConfig.Builder(name);
		NodeList filterRefList = element.getElementsByTagName("filter-ref");

		for (int j = 0; j < filterRefList.getLength(); j++) {
			Element filterRefElement = (Element) filterRefList.item(j);
			List<FilterMapping> filters = lookupFilterReference(context, filterRefElement);
			config.addFilters(filters);
		}

		return config.build();
	}

	protected void loadSegments(PackageConfig.Builder context, Element element) throws ConfigurationException {
		NodeList segmentList = element.getElementsByTagName("segment");

		for (int i = 0; i < segmentList.getLength(); i++) {
			Element segmentElement = (Element) segmentList.item(i);
			String name = segmentElement.getAttribute("name");
			String root = segmentElement.getAttribute("root");
			String fileName = segmentElement.getAttribute("file-name");
			String sheetName = segmentElement.getAttribute("sheet-name");

			SegmentConfig config = new SegmentConfig.Builder(name, root, fileName, sheetName).build();

			context.addSegmentConfig(config);
		}

		loadSegmentStacks(element, context);
	}

	protected void loadSegmentStacks(Element element, PackageConfig.Builder context) throws ConfigurationException {
		NodeList segmentStackList = element.getElementsByTagName("segment-stack");

		for (int i = 0; i < segmentStackList.getLength(); i++) {
			Element segmentStackElement = (Element) segmentStackList.item(i);

			SegmentStackConfig config = loadSegmentStack(segmentStackElement, context);

			context.addSegmentStackConfig(config);
		}
	}

	protected SegmentStackConfig loadSegmentStack(Element element, PackageConfig.Builder context) throws ConfigurationException {
		String name = element.getAttribute("name");

		SegmentStackConfig.Builder config = new SegmentStackConfig.Builder(name);
		NodeList segmentRefList = element.getElementsByTagName("segment-ref");

		for (int j = 0; j < segmentRefList.getLength(); j++) {
			Element segmentRefElement = (Element) segmentRefList.item(j);
			List<SegmentMapping> segments = lookupSegmentReference(context, segmentRefElement);
			config.addSegments(segments);
		}

		return config.build();
	}

	protected void addMapping(Element mappingElement, PackageConfig.Builder packageContext) throws ConfigurationException {
		String name = mappingElement.getAttribute("name");
		String source = mappingElement.getAttribute("source");

		List<FilterMapping> filterList = buildFilterList(mappingElement, packageContext);

		RequestConfig requestConfig = buildRequestConfig(mappingElement, packageContext);
		ResponseConfig responseConfig = buildResponseConfig(mappingElement, packageContext);

		MappingConfig mappingConfig = new MappingConfig.Builder(name, source).addFilters(filterList).addRequestConfig(requestConfig).addResponseConfig(responseConfig).build();
		packageContext.addMappingConfig(name, mappingConfig);
	}

	protected List<FilterMapping> buildFilterList(Element element, PackageConfig.Builder context) throws ConfigurationException {
		List<FilterMapping> filterList = new ArrayList<FilterMapping>();
		NodeList filterRefList = element.getElementsByTagName("filter-ref");

		for (int i = 0; i < filterRefList.getLength(); i++) {
			Element filterRefElement = (Element) filterRefList.item(i);

			if (filterRefElement.getParentNode().equals(element) || filterRefElement.getParentNode().getNodeName().equals(element.getNodeName())) {
				List<FilterMapping> filters = lookupFilterReference(context, filterRefElement);
				filterList.addAll(filters);
			}
		}

		return filterList;
	}

	private List<FilterMapping> lookupFilterReference(PackageConfig.Builder context, Element filterRefElement) throws ConfigurationException {
		String refName = filterRefElement.getAttribute("name");
		// Map<String, String> refParams = XmlHelper.getParams(filterRefElement);

		// Location loc = LocationUtils.getLocation(interceptorRefElement);
		return FilterBuilder.constructFilterReference(context, refName, objectFactory);
	}
	
    private RequestConfig buildRequestConfig(Element element, PackageConfig.Builder packageContext) {
        NodeList requestList = element.getElementsByTagName("request");
        
        boolean isConvert = true;       
        List<SegmentMapping> segmentList = new ArrayList<SegmentMapping>();
        if (requestList.getLength() > 0) {
            Element requestElement = (Element) requestList.item(0);
//          packageContext.defaultClassRef(defaultClassRefElement.getAttribute("class"));
            String convert = requestElement.getAttribute("convert");
            isConvert = Boolean.parseBoolean(convert);
    		NodeList segmentRefList = requestElement.getElementsByTagName("segment-ref");

    		for (int i = 0; i < segmentRefList.getLength(); i++) {
    			Element segmentRefElement = (Element) segmentRefList.item(i);

    			if (segmentRefElement.getParentNode().equals(element) || segmentRefElement.getParentNode().getNodeName().equals(element.getNodeName())) {
    				List<SegmentMapping> segments = lookupSegmentReference(packageContext, segmentRefElement);
    				segmentList.addAll(segments);
    			}
    		}
        }
        
        return new RequestConfig.Builder().convert(isConvert).addSegments(segmentList).build();
    }
    
    private ResponseConfig buildResponseConfig(Element element, PackageConfig.Builder packageContext) {
        NodeList responseList = element.getElementsByTagName("response");
        
        boolean isConvert = true;
        List<SegmentMapping> segmentList = new ArrayList<SegmentMapping>();
        if (responseList.getLength() > 0) {
            Element responseElement = (Element) responseList.item(0);
            String convert = responseElement.getAttribute("convert");
            isConvert = Boolean.parseBoolean(convert);
    		NodeList segmentRefList = responseElement.getElementsByTagName("segment-ref");

    		for (int i = 0; i < segmentRefList.getLength(); i++) {
    			Element segmentRefElement = (Element) segmentRefList.item(i);

    			if (segmentRefElement.getParentNode().equals(element) || segmentRefElement.getParentNode().getNodeName().equals(element.getNodeName())) {
    				List<SegmentMapping> segments = lookupSegmentReference(packageContext, segmentRefElement);
    				segmentList.addAll(segments);
    			}
    		}
        }
        
        return new ResponseConfig.Builder().convert(isConvert).addSegments(segmentList).build();
    }
    
	private List<SegmentMapping> lookupSegmentReference(PackageConfig.Builder context, Element segmentRefElement) throws ConfigurationException {
		String refName = segmentRefElement.getAttribute("name");
		// Map<String, String> refParams = XmlHelper.getParams(filterRefElement);

		// Location loc = LocationUtils.getLocation(interceptorRefElement);
		return SegmentBuilder.constructSegmentReference(context, refName, objectFactory);
	}

	protected Set<String> buildAllowedMethods(Element element, PackageConfig.Builder packageContext) {
		NodeList allowedMethodsEls = element.getElementsByTagName("allowed-methods");

		Set<String> allowedMethods = null;

		if (allowedMethodsEls.getLength() > 0) {
			allowedMethods = new HashSet<String>();
			Node n = allowedMethodsEls.item(0).getFirstChild();
			if (n != null) {
				String s = n.getNodeValue().trim();
				if (s.length() > 0) {
					allowedMethods = TextParseUtil.commaDelimitedStringToSet(s);
				}
			}
		} else if (packageContext.isStrictMethodInvocation()) {
			allowedMethods = new HashSet<String>();
		}

		return allowedMethods;
	}

	private List<Document> loadConfigurationFiles(String fileName, Element includeElement) {
		List<Document> docs = new ArrayList<Document>();
		List<Document> finalDocs = new ArrayList<Document>();
		if (!includedFileNames.contains(fileName)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Loading action configurations from: " + fileName);
			}

			includedFileNames.add(fileName);

			Iterator<URL> urls = null;
			InputStream is = null;

			IOException ioException = null;
			try {
				urls = getConfigurationUrls(fileName);
			} catch (IOException ex) {
				ioException = ex;
			}

			if (urls == null || !urls.hasNext()) {
				if (errorIfMissing) {
					throw new ConfigurationException("Could not open files of the name " + fileName, ioException);
				} else {
					if (LOG.isInfoEnabled()) {
						LOG.info("Unable to locate configuration files of the name " + fileName + ", skipping");
					}
					return docs;
				}
			}

			URL url = null;
			while (urls.hasNext()) {
				try {
					url = urls.next();
					is = fileManager.loadFile(url);

					InputSource in = new InputSource(is);

					in.setSystemId(url.toString());

					docs.add(DomHelper.parse(in, dtdMappings));
					loadedFileUrls.add(url.toString());
				} catch (XWorkException e) {
					if (includeElement != null) {
						throw new ConfigurationException("Unable to load " + url, e, includeElement);
					} else {
						throw new ConfigurationException("Unable to load " + url, e);
					}
				} catch (Exception e) {
					throw new ConfigurationException("Caught exception while loading file " + fileName, e, includeElement);
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							LOG.error("Unable to close input stream", e);
						}
					}
				}
			}

			// sort the documents, according to the "order" attribute
			Collections.sort(docs, new Comparator<Document>() {
				public int compare(Document doc1, Document doc2) {
					return XmlHelper.getLoadOrder(doc1).compareTo(XmlHelper.getLoadOrder(doc2));
				}
			});

			for (Document doc : docs) {
				Element rootElement = doc.getDocumentElement();
				NodeList children = rootElement.getChildNodes();
				int childSize = children.getLength();

				for (int i = 0; i < childSize; i++) {
					Node childNode = children.item(i);

					if (childNode instanceof Element) {
						Element child = (Element) childNode;

						final String nodeName = child.getNodeName();

						if ("include".equals(nodeName)) {
							String includeFileName = child.getAttribute("file");
							if (includeFileName.indexOf('*') != -1) {
								// handleWildCardIncludes(includeFileName, docs, child);
								ClassPathFinder wildcardFinder = new ClassPathFinder();
								wildcardFinder.setPattern(includeFileName);
								Vector<String> wildcardMatches = wildcardFinder.findMatches();
								for (String match : wildcardMatches) {
									finalDocs.addAll(loadConfigurationFiles(match, child));
								}
							} else {
								finalDocs.addAll(loadConfigurationFiles(includeFileName, child));
							}
						}
					}
				}
				finalDocs.add(doc);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("Loaded action configuration from: " + fileName);
			}
		}
		return finalDocs;
	}

	protected Iterator<URL> getConfigurationUrls(String fileName) throws IOException {
		return ClassLoaderUtil.getResources(fileName, XmlConfigurationProvider.class, false);
	}

	List<Document> getDocuments() {
		return documents;
	}

	@Override
	public String toString() {
		return "XmlConfigurationProvider{" + "configFileName='" + configFileName + '\'' + '}';
	}

}
