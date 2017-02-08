package com.ccb.athena.converter.xwork2;
/**
 * Factory that creates FileManager, default to {@link com.opensymphony.xwork2.util.fs.DefaultFileManager}
 */
public interface FileManagerFactory {

    void setReloadingConfigs(String reloadingConfigs);

    FileManager getFileManager();

}