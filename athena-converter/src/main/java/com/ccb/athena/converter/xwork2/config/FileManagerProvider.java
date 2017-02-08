package com.ccb.athena.converter.xwork2.config;

import java.util.Properties;

import com.ccb.athena.converter.xwork2.FileManager;
import com.ccb.athena.converter.xwork2.inject.ContainerBuilder;
import com.ccb.athena.converter.xwork2.inject.Scope;

public class FileManagerProvider implements ContainerProvider {

    private Class<? extends FileManager> fileManagerClass;
    private String name;

    public FileManagerProvider(Class<? extends FileManager> fileManagerClass, String name) {
        this.fileManagerClass = fileManagerClass;
        this.name = name;
    }

    public void destroy() {
    }

    public void init(Configuration configuration) throws ConfigurationException {
    }

    public boolean needsReload() {
        return false;
    }

    public void register(ContainerBuilder builder, Properties props) throws ConfigurationException {
        builder.factory(FileManager.class, name, fileManagerClass, Scope.SINGLETON);
    }

}