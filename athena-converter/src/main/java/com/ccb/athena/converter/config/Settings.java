package com.ccb.athena.converter.config;

import java.util.Iterator;

interface Settings {

    /**
     * Retrieve value for provided name
     *
     * @param name The name of the setting value to retrieve
     * @return The setting value as a String or null
     */
    String get(String name);

    /**
     * Returns {@link java.util.Iterator} with all values
     *
     * @return A list of the settings as an iterator
     */
    Iterator list();

}
