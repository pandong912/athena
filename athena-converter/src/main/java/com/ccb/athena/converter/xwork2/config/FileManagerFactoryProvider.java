package com.ccb.athena.converter.xwork2.config;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Scope;

public class FileManagerFactoryProvider implements ContainerProvider {

    private Class<? extends FileManagerFactory> factoryClass;

    public FileManagerFactoryProvider(Class<? extends FileManagerFactory> factoryClass) {
        this.factoryClass = factoryClass;
    }

    public void destroy() {
    }

    public void init(Configuration configuration) throws ConfigurationException {
    }

    public boolean needsReload() {
        return false;
    }

    public void register(ContainerBuilder builder, Properties props) throws ConfigurationException {
        builder.factory(FileManagerFactory.class, factoryClass.getSimpleName(), factoryClass, Scope.SINGLETON);
    }

}