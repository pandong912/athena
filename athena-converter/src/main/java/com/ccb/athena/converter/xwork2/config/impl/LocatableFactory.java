package com.ccb.athena.converter.xwork2.config.impl;

import java.util.LinkedHashMap;

import com.ccb.athena.converter.xwork2.inject.Context;
import com.ccb.athena.converter.xwork2.inject.Factory;
import com.ccb.athena.converter.xwork2.inject.Scope;
import com.opensymphony.xwork2.util.location.Located;
import com.opensymphony.xwork2.util.location.LocationUtils;

public class LocatableFactory<T> extends Located implements Factory<T> {


    private Class implementation;
    private Class type;
    private String name;
    private Scope scope;

    public LocatableFactory(String name, Class type, Class implementation, Scope scope, Object location) {
        this.implementation = implementation;
        this.type = type;
        this.name = name;
        this.scope = scope;
        setLocation(LocationUtils.getLocation(location));
    }

    @SuppressWarnings("unchecked")
    public T create(Context context) {
        Object obj = context.getContainer().inject(implementation);
        return (T) obj;
    }

    @Override
    public String toString() {
        String fields = new LinkedHashMap<String, Object>() {
            {
                put("type", type);
                put("name", name);
                put("implementation", implementation);
                put("scope", scope);
            }
        }.toString();
        StringBuilder sb = new StringBuilder(fields);
        sb.append(super.toString());
        sb.append(" defined at ");
        sb.append(getLocation().toString());
        return sb.toString();
    }
}
