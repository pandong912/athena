package com.ccb.athena.converter.config;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;

public class DefaultPropertiesProvider extends PropertiesConfigurationProvider {

    public void destroy() {
    }

    public void init(Configuration configuration) throws ConfigurationException {
    }

    public void register(ContainerBuilder builder, Properties props) throws ConfigurationException {
        try {
            PropertiesSettings defaultSettings = new PropertiesSettings("com/ccb/athena/default");
            loadSettings(props, defaultSettings);
        } catch (Exception e) {
            throw new ConfigurationException("Could not find or error in com/ccb/athena/default.properties", e);
        }
    }

}
