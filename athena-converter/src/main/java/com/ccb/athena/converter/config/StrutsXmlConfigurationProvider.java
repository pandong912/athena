package com.ccb.athena.converter.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ccb.athena.converter.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class StrutsXmlConfigurationProvider extends XmlConfigurationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(StrutsXmlConfigurationProvider.class);
    private File baseDir = null;
    private String filename;
    private String reloadKey;
//    private ServletContext servletContext;

    /**
     * Constructs the configuration provider
     *
     * @param errorIfMissing If we should throw an exception if the file can't be found
     */
    public StrutsXmlConfigurationProvider(boolean errorIfMissing) {
        this("struts.xml", errorIfMissing);
    }

    /**
     * Constructs the configuration provider
     *
     * @param filename The filename to look for
     * @param errorIfMissing If we should throw an exception if the file can't be found
     * @param ctx Our ServletContext
     */
    public StrutsXmlConfigurationProvider(String filename, boolean errorIfMissing) {
        super(filename, errorIfMissing);
        this.filename = filename;
        reloadKey = "configurationReload-"+filename;
        Map<String,String> dtdMappings = new HashMap<String,String>(getDtdMappings());
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.0//EN", "struts-2.0.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1//EN", "struts-2.1.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN", "struts-2.1.7.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.3//EN", "struts-2.3.dtd");
        setDtdMappings(dtdMappings);
        File file = new File(filename);
        if (file.getParent() != null) {
            this.baseDir = file.getParentFile();
        }
    }
    
    @Override
    public void loadPackages() {
        ActionContext ctx = ActionContext.getContext();
        ctx.put(reloadKey, Boolean.TRUE);
        super.loadPackages();
    }

    /**
     * Look for the configuration file on the classpath and in the file system
     *
     * @param fileName The file name to retrieve
     * @see com.opensymphony.xwork2.config.providers.XmlConfigurationProvider#getConfigurationUrls
     */
    @Override
    protected Iterator<URL> getConfigurationUrls(String fileName) throws IOException {
        URL url = null;
        if (baseDir != null) {
            url = findInFileSystem(fileName);
            if (url == null) {
                return super.getConfigurationUrls(fileName);
            }
        }
        if (url != null) {
            List<URL> list = new ArrayList<URL>();
            list.add(url);
            return list.iterator();
        } else {
            return super.getConfigurationUrls(fileName);
        }
    }

    protected URL findInFileSystem(String fileName) throws IOException {
        URL url = null;
        File file = new File(fileName);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to load file " + file);
        }

        // Trying relative path to original file
        if (!file.exists()) {
            file = new File(baseDir, fileName);
        }
        if (file.exists()) {
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new IOException("Unable to convert "+file+" to a URL");
            }
        }
        return url;
    }

    /**
     * Overrides needs reload to ensure it is only checked once per request
     */
    @Override
    public boolean needsReload() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.get(reloadKey) == null && super.needsReload();
        } else {
            return super.needsReload();
        }

    }
    
    public String toString() {
        return ("Struts XML configuration provider ("+filename+")");
    }
}
