package com.ccb.athena.converter.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.ccb.athena.converter.AthenaConstants;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class DefaultSettings implements Settings {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSettings.class);

    /**
     * The Settings object that handles API calls.
     */
    private Settings delegate;

    /**
     * Constructs an instance by loading the standard property files, 
     * any custom property files (<code>struts.custom.properties</code>), 
     * and any custom message resources ().
     * <p>
     * Since this constructor  combines Settings from multiple resources,
     * it utilizes a {@link DelegatingSettings} instance,
     * and all API calls are handled by that instance.
     */
    public DefaultSettings() {

        ArrayList<Settings> list = new ArrayList<Settings>();

        // stuts.properties, default.properties
        try {
            list.add(new PropertiesSettings("athena"));
        } catch (Exception e) {
            LOG.warn("DefaultSettings: Could not find or error in struts.properties", e);
        }

        delegate = new DelegatingSettings(list);

        // struts.custom.properties
        String files = delegate.get(AthenaConstants.ATHENA_CUSTOM_PROPERTIES);
        if (files != null) {
            StringTokenizer customProperties = new StringTokenizer(files, ",");

            while (customProperties.hasMoreTokens()) {
                String name = customProperties.nextToken();
                try {
                    list.add(new PropertiesSettings(name));
                } catch (Exception e) {
                    LOG.error("DefaultSettings: Could not find " + name + ".properties. Skipping.");
                }
            }

            delegate = new DelegatingSettings(list);
        }
    }

    public String get(String aName) throws IllegalArgumentException {
        return delegate.get(aName);
    }

    public Iterator list() {
        return delegate.list();
    }

}
