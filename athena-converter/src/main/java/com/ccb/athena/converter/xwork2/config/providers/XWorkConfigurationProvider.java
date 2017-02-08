package com.ccb.athena.converter.xwork2.config.providers;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.FileManager;
import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.config.ConfigurationProvider;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.ccb.athena.converter.xwork2.util.fs.DefaultFileManager;
import com.ccb.athena.converter.xwork2.util.fs.DefaultFileManagerFactory;
import com.opensymphony.xwork2.factory.ActionFactory;
import com.opensymphony.xwork2.factory.ConverterFactory;
import com.opensymphony.xwork2.factory.DefaultActionFactory;
import com.opensymphony.xwork2.factory.DefaultConverterFactory;
import com.opensymphony.xwork2.factory.DefaultInterceptorFactory;
import com.opensymphony.xwork2.factory.DefaultResultFactory;
import com.opensymphony.xwork2.factory.DefaultUnknownHandlerFactory;
import com.opensymphony.xwork2.factory.InterceptorFactory;
import com.opensymphony.xwork2.factory.ResultFactory;
import com.opensymphony.xwork2.factory.UnknownHandlerFactory;

public class XWorkConfigurationProvider implements ConfigurationProvider {

    public void destroy() {
    }

    public void init(Configuration configuration) throws ConfigurationException {
    }

    public void loadPackages() throws ConfigurationException {
    }

    public boolean needsReload() {
        return false;
    }

    public void register(ContainerBuilder builder, Properties props)
            throws ConfigurationException {

        builder
                .factory(ObjectFactory.class)
                .factory(ActionFactory.class, DefaultActionFactory.class)
                .factory(ResultFactory.class, DefaultResultFactory.class)
                .factory(InterceptorFactory.class, DefaultInterceptorFactory.class)
                .factory(com.opensymphony.xwork2.factory.ValidatorFactory.class, com.opensymphony.xwork2.factory.DefaultValidatorFactory.class)
                .factory(ConverterFactory.class, DefaultConverterFactory.class)
                .factory(UnknownHandlerFactory.class, DefaultUnknownHandlerFactory.class)
                .factory(FileManager.class, "system", DefaultFileManager.class, Scope.SINGLETON)
                .factory(FileManagerFactory.class, DefaultFileManagerFactory.class, Scope.SINGLETON)
        ;
    }

}