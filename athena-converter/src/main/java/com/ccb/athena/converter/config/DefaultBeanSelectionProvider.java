package com.ccb.athena.converter.config;

import java.util.Properties;

import com.ccb.athena.converter.AthenaConstants;
import com.ccb.athena.converter.dispatcher.mapper.ActionMapper;
import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.opensymphony.xwork2.factory.ActionFactory;
import com.opensymphony.xwork2.factory.ConverterFactory;
import com.opensymphony.xwork2.factory.InterceptorFactory;
import com.opensymphony.xwork2.factory.ResultFactory;
import com.opensymphony.xwork2.factory.UnknownHandlerFactory;
import com.opensymphony.xwork2.factory.ValidatorFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class DefaultBeanSelectionProvider extends AbstractBeanSelectionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBeanSelectionProvider.class);

    public void register(ContainerBuilder builder, Properties props) {
        alias(ObjectFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY, builder, props);
        alias(ActionFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_ACTIONFACTORY, builder, props);
        alias(ResultFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_RESULTFACTORY, builder, props);
        alias(ConverterFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_CONVERTERFACTORY, builder, props);
        alias(InterceptorFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_INTERCEPTORFACTORY, builder, props);
        alias(ValidatorFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_VALIDATORFACTORY, builder, props);
        alias(UnknownHandlerFactory.class, AthenaConstants.ATHENA_OBJECTFACTORY_UNKNOWNHANDLERFACTORY, builder, props);
        
        alias(FileManagerFactory.class, AthenaConstants.ATHENA_FILE_MANAGER_FACTORY, builder, props, Scope.SINGLETON);

        alias(ActionMapper.class, AthenaConstants.ATHENA_MAPPER_CLASS, builder, props);
    }
}
