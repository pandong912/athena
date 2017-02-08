package com.ccb.athena.converter.xwork2.config;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;

public interface PackageProvider {
    
    /**
     * Initializes with the configuration
     * @param configuration The configuration
     * @throws ConfigurationException If anything goes wrong
     */
    public void init(Configuration configuration) throws ConfigurationException;
    
    /**
     * Tells whether the PackageProvider should reload its configuration
     *
     * @return <tt>true</tt>, whether the PackageProvider should reload its configuration, <tt>false</tt>otherwise.
     */
    public boolean needsReload();

    /**
     * Loads the packages for the configuration.
     * @throws ConfigurationException
     */
    public void loadPackages() throws ConfigurationException;
    
}
