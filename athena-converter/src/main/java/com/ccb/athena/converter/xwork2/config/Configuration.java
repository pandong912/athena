package com.ccb.athena.converter.xwork2.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccb.athena.converter.config.entities.PackageConfig;
import com.ccb.athena.converter.xwork2.inject.Container;

public interface Configuration extends Serializable {

    void rebuildRuntimeConfiguration();
    
    PackageConfig getPackageConfig(String name);

    Set<String> getPackageConfigNames();

    Map<String, PackageConfig> getPackageConfigs();
    
    void addPackageConfig(String name, PackageConfig packageConfig);

    PackageConfig removePackageConfig(String packageName);

    void destroy();
   
    List<PackageProvider> reloadContainer(List<ContainerProvider> containerProviders) throws ConfigurationException;

    Container getContainer();

    Set<String> getLoadedFileNames();

}
