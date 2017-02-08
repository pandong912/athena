package com.ccb.athena.converter.config;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class DelegatingSettings implements Settings {

    /**
     * The Settings objects.
     */
    List<Settings> delegates;

    /**
     * Creates a new DelegatingSettings object utilizing the list of {@link Settings} objects.
     *
     * @param delegates The Settings objects to use as delegates
     */
    public DelegatingSettings(List<Settings> delegates) {
        this.delegates = delegates;
    }

    public String get(String name) throws IllegalArgumentException {
        for (Settings delegate : delegates) {
            String value = delegate.get(name);
            if (value != null) {
                return value;
            }
        }
        return null;
    }


    public Iterator list() {
        boolean workedAtAll = false;

        Set<Object> settingList = new HashSet<Object>();
        UnsupportedOperationException e = null;

        for (Settings delegate : delegates) {
            try {
                Iterator list = delegate.list();

                while (list.hasNext()) {
                    settingList.add(list.next());
                }

                workedAtAll = true;
            } catch (UnsupportedOperationException ex) {
                e = ex;

                // Try next delegate
            }
        }

        if (!workedAtAll) {
            throw (e == null) ? new UnsupportedOperationException() : e;
        } else {
            return settingList.iterator();
        }
    }

}
