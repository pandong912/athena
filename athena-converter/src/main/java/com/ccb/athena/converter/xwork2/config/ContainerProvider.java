package com.ccb.athena.converter.xwork2.config;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;

public interface ContainerProvider {

    /**
     * Called before removed from the configuration manager
     */
    public void destroy();
    
    /**
     * Initializes with the configuration
     * @param configuration The configuration
     * @throws ConfigurationException If anything goes wrong
     */
    public void init(Configuration configuration) throws ConfigurationException;
    
    /**
     * Registers beans and properties for the Container
     * 
     * @param builder The builder to register beans with
     * @param props The properties to register constants with
     * @throws ConfigurationException If anything goes wrong
     */
    public void register(ContainerBuilder builder, Properties props) throws ConfigurationException;
    
}
