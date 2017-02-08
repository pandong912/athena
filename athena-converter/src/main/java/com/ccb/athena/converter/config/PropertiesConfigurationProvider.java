package com.ccb.athena.converter.config;

import java.util.Iterator;
import java.util.Properties;

import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.config.ConfigurationProvider;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;


public class PropertiesConfigurationProvider implements ConfigurationProvider {
	
    public void destroy() {
    }

    public void init(Configuration configuration) throws ConfigurationException {
    }

    public void loadPackages() throws ConfigurationException {
    }

    public boolean needsReload() {
        return false;
    }

    public void register(ContainerBuilder builder, Properties props) throws ConfigurationException {
        final DefaultSettings settings = new DefaultSettings();
        loadSettings(props, settings);
    }

    /**
     * @param props
     * @param settings
     */
    protected void loadSettings(Properties props, final Settings settings) {
        for (Iterator i = settings.list(); i.hasNext(); ) {
            String name = (String) i.next();
            props.setProperty(name, settings.get(name));
        }
    }
}
