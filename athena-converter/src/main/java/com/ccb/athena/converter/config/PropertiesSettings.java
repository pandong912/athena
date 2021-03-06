package com.ccb.athena.converter.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.apache.struts2.StrutsException;

import com.ccb.athena.converter.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * A class to handle settings via a properties file.
 */
class PropertiesSettings implements Settings {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesSettings.class);

    private Properties settings;

    /**
     * Creates a new properties config given the name of a properties file. The name is expected to NOT have
     * the ".properties" file extension.  So when <tt>new PropertiesSettings("foo")</tt> is called
     * this class will look in the classpath for the <tt>foo.properties</tt> file.
     *
     * @param name the name of the properties file, excluding the ".properties" extension.
     */
    public PropertiesSettings(String name) {
        
        URL settingsUrl = ClassLoaderUtil.getResource(name + ".properties", getClass());
        
        if (settingsUrl == null) {
            if (LOG.isDebugEnabled()) {
        	LOG.debug(name + ".properties missing");
            }
            settings = new Properties();
            return;
        }
        
        settings = new Properties();

        // Load settings
        InputStream in = null;
        try {
            in = settingsUrl.openStream();
            settings.load(in);
        } catch (IOException e) {
            throw new StrutsException("Could not load " + name + ".properties:" + e, e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException io) {
                    if (LOG.isWarnEnabled()) {
                	LOG.warn("Unable to close input stream", io);
                    }
                }
            }
        }
    }


    /**
     * Gets a property from the properties file.
     *
     * @see #get(String)
     */
    public String get(String aName) throws IllegalArgumentException {
        return settings.getProperty(aName);
    }

    /**
     * Lists all keys in the properties file.
     *
     * @see #list()
     */
    public Iterator list() {
        return settings.keySet().iterator();
    }

}
