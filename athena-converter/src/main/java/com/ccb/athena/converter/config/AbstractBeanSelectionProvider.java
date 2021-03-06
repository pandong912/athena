package com.ccb.athena.converter.config;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.BeanSelectionProvider;
import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Context;
import com.ccb.athena.converter.xwork2.inject.Factory;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.ccb.athena.converter.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


public abstract class AbstractBeanSelectionProvider implements BeanSelectionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractBeanSelectionProvider.class);

    public static final String DEFAULT_BEAN_NAME = "athena";

    public void destroy() {
        // NO-OP
    }

    public void loadPackages() throws ConfigurationException {
        // NO-OP
    }

    public void init(Configuration configuration) throws ConfigurationException {
        // NO-OP
    }

    public boolean needsReload() {
        return false;
    }

    protected void alias(Class type, String key, ContainerBuilder builder, Properties props) {
        alias(type, key, builder, props, Scope.SINGLETON);
    }

    protected void alias(Class type, String key, ContainerBuilder builder, Properties props, Scope scope) {
        if (!builder.contains(type)) {
            String foundName = props.getProperty(key, DEFAULT_BEAN_NAME);
            if (builder.contains(type, foundName)) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Choosing bean (#0) for (#1)", foundName, type.getName());
                }
                builder.alias(type, foundName, Container.DEFAULT_NAME);
            } else {
                try {
                    Class cls = ClassLoaderUtil.loadClass(foundName, this.getClass());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Choosing bean (#0) for (#1)", cls.getName(), type.getName());
                    }
                    builder.factory(type, cls, scope);
                } catch (ClassNotFoundException ex) {
                    // Perhaps a spring bean id, so we'll delegate to the object factory at runtime
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Choosing bean (#0) for (#1) to be loaded from the ObjectFactory", foundName, type.getName());
                    }
                    if (DEFAULT_BEAN_NAME.equals(foundName)) {
                        // Probably an optional bean, will ignore
                    } else {
                        if (ObjectFactory.class != type) {
                            builder.factory(type, new ObjectFactoryDelegateFactory(foundName, type), scope);
                        } else {
                            throw new ConfigurationException("Cannot locate the chosen ObjectFactory implementation: " + foundName);
                        }
                    }
                }
            }
        } else {
//        	LOG.info("Unable to alias bean type (#0), default mapping already assigned.", type.getName());
            if (LOG.isWarnEnabled()) {
                LOG.warn("Unable to alias bean type (#0), default mapping already assigned.", type.getName());
            }
        }
    }

    static class ObjectFactoryDelegateFactory implements Factory {

        String name;
        Class type;

        ObjectFactoryDelegateFactory(String name, Class type) {
            this.name = name;
            this.type = type;
        }

        public Object create(Context context) throws Exception {
            ObjectFactory objFactory = context.getContainer().getInstance(ObjectFactory.class);
            try {
                return objFactory.buildBean(name, null, true);
            } catch (ClassNotFoundException ex) {
                throw new ConfigurationException("Unable to load bean "+type.getName()+" ("+name+")");
            }
        }

    }
}
