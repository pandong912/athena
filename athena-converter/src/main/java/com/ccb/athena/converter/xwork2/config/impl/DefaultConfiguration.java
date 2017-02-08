package com.ccb.athena.converter.xwork2.config.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ccb.athena.converter.config.entities.PackageConfig;
import com.ccb.athena.converter.xwork2.FileManager;
import com.ccb.athena.converter.xwork2.FileManagerFactory;
import com.ccb.athena.converter.xwork2.ObjectFactory;
import com.ccb.athena.converter.xwork2.config.Configuration;
import com.ccb.athena.converter.xwork2.config.ConfigurationException;
import com.ccb.athena.converter.xwork2.config.ContainerProvider;
import com.ccb.athena.converter.xwork2.config.FileManagerFactoryProvider;
import com.ccb.athena.converter.xwork2.config.FileManagerProvider;
import com.ccb.athena.converter.xwork2.config.PackageProvider;
import com.ccb.athena.converter.xwork2.inject.Container;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Context;
import com.ccb.athena.converter.xwork2.inject.Factory;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.ccb.athena.converter.xwork2.util.fs.DefaultFileManager;
import com.ccb.athena.converter.xwork2.util.fs.DefaultFileManagerFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.factory.ActionFactory;
import com.opensymphony.xwork2.factory.DefaultActionFactory;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class DefaultConfiguration implements Configuration {

    protected static final Logger LOG = LoggerFactory.getLogger(DefaultConfiguration.class);

    protected Map<String, PackageConfig> packageContexts = new LinkedHashMap<String, PackageConfig>();
    protected Container container;
    protected String defaultFrameworkBeanName;
    protected Set<String> loadedFileNames = new TreeSet<String>();

    ObjectFactory objectFactory;

    public DefaultConfiguration() {
        this("xwork");
    }

    public DefaultConfiguration(String defaultBeanName) {
        this.defaultFrameworkBeanName = defaultBeanName;
    }

	@Override
	public void rebuildRuntimeConfiguration() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PackageConfig getPackageConfig(String name) {
		return packageContexts.get(name);
	}

	@Override
	public Set<String> getPackageConfigNames() {
		return packageContexts.keySet();
	}

	@Override
	public Map<String, PackageConfig> getPackageConfigs() {
		return packageContexts;
	}

	@Override
	public void addPackageConfig(String name, PackageConfig packageConfig) {
		packageContexts.put(name, packageConfig);		
	}

	@Override
	public PackageConfig removePackageConfig(String packageName) {
		return packageContexts.remove(packageName);
	}

	@Override
	public void destroy() {
        packageContexts.clear();
        loadedFileNames.clear();
	}

	@Override
	public List<PackageProvider> reloadContainer(List<ContainerProvider> providers) throws ConfigurationException {
        packageContexts.clear();
        loadedFileNames.clear();
        List<PackageProvider> packageProviders = new ArrayList<PackageProvider>();

        ContainerProperties props = new ContainerProperties();
        ContainerBuilder builder = new ContainerBuilder();
        Container bootstrap = createBootstrapContainer(providers);
        for (final ContainerProvider containerProvider : providers)
        {
            bootstrap.inject(containerProvider);
            containerProvider.init(this);
            containerProvider.register(builder, props);
        }
        props.setConstants(builder);

        builder.factory(Configuration.class, new Factory<Configuration>() {
            public Configuration create(Context context) throws Exception {
                return DefaultConfiguration.this;
            }
        });

        ActionContext oldContext = ActionContext.getContext();
        try {
            // Set the bootstrap container for the purposes of factory creation

//            setContext(bootstrap);
            container = builder.create(false);
//            setContext(container);
            objectFactory = container.getInstance(ObjectFactory.class);

            // Process the configuration providers first
            for (final ContainerProvider containerProvider : providers)
            {
                if (containerProvider instanceof PackageProvider) {
                    container.inject(containerProvider);
                    ((PackageProvider)containerProvider).loadPackages();
                    packageProviders.add((PackageProvider)containerProvider);
                }
            }

            rebuildRuntimeConfiguration();
        } finally {
            if (oldContext == null) {
                ActionContext.setContext(null);
            }
        }
        return packageProviders;
	}
	
    protected Container createBootstrapContainer(List<ContainerProvider> providers) {
        ContainerBuilder builder = new ContainerBuilder();
        boolean fmFactoryRegistered = false;
        for (ContainerProvider provider : providers) {
            if (provider instanceof FileManagerProvider) {
                provider.register(builder, null);
            }
            if (provider instanceof FileManagerFactoryProvider) {
                provider.register(builder, null);
                fmFactoryRegistered = true;
            }
        }
        builder.factory(ObjectFactory.class, Scope.SINGLETON);
        builder.factory(ActionFactory.class, DefaultActionFactory.class, Scope.SINGLETON);
        builder.factory(FileManager.class, "system", DefaultFileManager.class, Scope.SINGLETON);
        if (!fmFactoryRegistered) {
            builder.factory(FileManagerFactory.class, DefaultFileManagerFactory.class, Scope.SINGLETON);
        }
        
        return builder.create(true);
    }
    
    protected ActionContext setContext(Container cont) {
        ActionContext context = ActionContext.getContext();
        if (context == null) {
            ValueStack vs = cont.getInstance(ValueStackFactory.class).createValueStack();
            context = new ActionContext(vs.getContext());
            ActionContext.setContext(context);
        }
        return context;
    }

	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	@Override
	public Set<String> getLoadedFileNames() {
		// TODO Auto-generated method stub
		return loadedFileNames;
	}
	
    class ContainerProperties extends LocatableProperties {
        private static final long serialVersionUID = -7320625750836896089L;

        @Override
        public Object setProperty(String key, String value) {
            String oldValue = getProperty(key);
            if (LOG.isInfoEnabled() && oldValue != null && !oldValue.equals(value) && !defaultFrameworkBeanName.equals(oldValue)) {
                LOG.info("Overriding property "+key+" - old value: "+oldValue+" new value: "+value);
            }
            return super.setProperty(key, value);
        }

        public void setConstants(ContainerBuilder builder) {
            for (Object keyobj : keySet()) {
                String key = (String)keyobj;
                builder.factory(String.class, key,
                        new LocatableConstantFactory<String>(getProperty(key), getPropertyLocation(key)));
            }
        }
    }

}