package com.ccb.athena.converter.dispatcher;


import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsException;

import com.ccb.athena.converter.config.DefaultBeanSelectionProvider;
import com.ccb.athena.converter.config.DefaultPropertiesProvider;
import com.ccb.athena.converter.config.StrutsXmlConfigurationProvider;
import com.ccb.athena.converter.dispatcher.mapper.ActionMapper;
import com.ccb.athena.converter.xwork2.FileManager;
import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationManager;
import com.ccb.athena.converter.xwork2.config.FileManagerFactoryProvider;
import com.ccb.athena.converter.xwork2.config.FileManagerProvider;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.util.fs.JBossFileManager;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


public class Dispatcher {
	
    /**
     * Provide a logging instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);
    private static final String DEFAULT_CONFIGURATION_PATHS = "athena-default.xml,athena-plugin.xml,athena.xml";

	private ConfigurationManager configurationManager;
    protected Map<String, String> initParams;
    
    /**
     * Create the Dispatcher instance for a given ServletContext and set of initialization parameters.
     *
     * @param servletContext Our servlet context
     * @param initParams The set of initialization parameters
     */
    public Dispatcher() {
    	this.initParams = new HashMap<String, String>();
    }
    
    public Dispatcher(Map<String, String> initParams) {
        this.initParams = initParams;
    }
	
	public void init() {
		if(configurationManager == null) {
			configurationManager = new ConfigurationManager(DefaultBeanSelectionProvider.DEFAULT_BEAN_NAME);
		}
		
		try {
			init_FileManager();
			init_DefaultProperties();	// com.ccb.athena--default.properties
			init_TraditionalXmlConfigurations();	// athena的XML文件配置加载器
            init_AliasStandardObjects() ; // 默认容器内置对象

            Container container = init_PreloadConfiguration();
            container.inject(this);
		} catch (Exception ex) {
            if (LOG.isErrorEnabled())
                LOG.error("Dispatcher initialization failed", ex);
            throw new StrutsException(ex);
        }
	}
	
    private void init_FileManager() throws ClassNotFoundException {
        if (initParams.containsKey(StrutsConstants.STRUTS_FILE_MANAGER)) {
            final String fileManagerClassName = initParams.get(StrutsConstants.STRUTS_FILE_MANAGER);
            final Class<FileManager> fileManagerClass = (Class<FileManager>) Class.forName(fileManagerClassName);
            if (LOG.isInfoEnabled()) {
                LOG.info("Custom FileManager specified: #0", fileManagerClassName);
            }
            configurationManager.addContainerProvider(new FileManagerProvider(fileManagerClass, fileManagerClass.getSimpleName()));
        } else {
            // add any other Struts 2 provided implementations of FileManager
            configurationManager.addContainerProvider(new FileManagerProvider(JBossFileManager.class, "jboss"));
        }
        if (initParams.containsKey(StrutsConstants.STRUTS_FILE_MANAGER_FACTORY)) {
            final String fileManagerFactoryClassName = initParams.get(StrutsConstants.STRUTS_FILE_MANAGER_FACTORY);
            final Class<FileManagerFactory> fileManagerFactoryClass = (Class<FileManagerFactory>) Class.forName(fileManagerFactoryClassName);
            if (LOG.isInfoEnabled()) {
                LOG.info("Custom FileManagerFactory specified: #0", fileManagerFactoryClassName);
            }
            configurationManager.addContainerProvider(new FileManagerFactoryProvider(fileManagerFactoryClass));
        }
    }
	
    private void init_DefaultProperties() {
        configurationManager.addContainerProvider(new DefaultPropertiesProvider());
    }
    
    private void init_TraditionalXmlConfigurations() {
        String configPaths = initParams.get("config");
        if (configPaths == null) {
            configPaths = DEFAULT_CONFIGURATION_PATHS;
        }
        String[] files = configPaths.split("\\s*[,]\\s*");
        for (String file : files) {
            if (file.endsWith(".xml")) {
                configurationManager.addContainerProvider(new StrutsXmlConfigurationProvider(file, false));
            } else {
                throw new IllegalArgumentException("Invalid configuration file name");
            }
        }
    }
    
    private void init_AliasStandardObjects() {
        configurationManager.addContainerProvider(new DefaultBeanSelectionProvider());
    }

    private Container init_PreloadConfiguration() {
        Container container = getContainer();
        return container;
    }
    
    /**
     * Expose the ConfigurationManager instance.
     *
     * @return The instance
     */
    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    /**
     * Expose the dependency injection container.
     * @return Our dependency injection container
     */
    public Container getContainer() {
        if (ContainerHolder.get() != null) {
            return ContainerHolder.get();
        }
        ConfigurationManager mgr = getConfigurationManager();
        if (mgr == null) {
            throw new IllegalStateException("The configuration manager shouldn't be null");
        } else {
            Configuration config = mgr.getConfiguration();
            if (config == null) {
                throw new IllegalStateException("Unable to load configuration");
            } else {
                Container container = config.getContainer();
                ContainerHolder.store(container);
                return container;
            }
        }
    }
    
    public static void main(String[] args) {
         Dispatcher dispatcher = new Dispatcher();
         dispatcher.init();
         
         
         
         ActionMapper actionMapper = dispatcher.getContainer().getInstance(ActionMapper.class);
         System.out.println(actionMapper.getMapping("123", null).getDestCode());
	}
}
