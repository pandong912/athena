package com.ccb.athena.converter.xwork2.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ccb.athena.converter.xwork2.config.impl.DefaultConfiguration;
import com.ccb.athena.converter.xwork2.config.providers.XWorkConfigurationProvider;
import com.ccb.athena.converter.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


public class ConfigurationManager {
    protected static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);   
    protected Configuration configuration;
    protected Lock providerLock = new ReentrantLock();
    private List<ContainerProvider> containerProviders = new CopyOnWriteArrayList<ContainerProvider>();
    protected String defaultFrameworkBeanName;
    private boolean providersChanged = false;
    private boolean reloadConfigs = true; // for the first time
    
    public ConfigurationManager() {
        this("xwork");
    }
    
    public ConfigurationManager(String name) {
        this.defaultFrameworkBeanName = name;
    }
    
    public synchronized Configuration getConfiguration() {
        if (configuration == null) {
            setConfiguration(new DefaultConfiguration(defaultFrameworkBeanName));
            try {
                configuration.reloadContainer(getContainerProviders());
            } catch (ConfigurationException e) {
                setConfiguration(null);
                throw new ConfigurationException("Unable to load configuration.", e);
            }
        } else {
//            conditionalReload();
        }

        return configuration;
    }

    public synchronized void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    public List<ContainerProvider> getContainerProviders() {
        providerLock.lock();
        try {
            if (containerProviders.size() == 0) {
                containerProviders.add(new XWorkConfigurationProvider());
                containerProviders.add(new XmlConfigurationProvider("xwork.xml", false));
            }

            return containerProviders;
        } finally {
            providerLock.unlock();
        }
    }
    
    /**
     * adds a configuration provider to the List of ConfigurationProviders.  a given ConfigurationProvider may be added
     * more than once
     *
     * @param provider the ConfigurationProvider to register
     */
    public void addContainerProvider(ContainerProvider provider) {
        if (!containerProviders.contains(provider)) {
            containerProviders.add(provider);
            providersChanged = true;
        }
    }
}
