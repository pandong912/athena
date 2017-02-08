package com.ccb.athena.converter.config.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.util.location.Located;
import com.opensymphony.xwork2.util.location.Location;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class PackageConfig extends Located implements Comparable, Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(PackageConfig.class);

    protected Map<String, ActionConfig> actionConfigs;
    protected Map<String, Object> filterConfigs;
    protected Map<String, Object> segmentConfigs;
    protected Map<String, MappingConfig> mappingConfigs;
    protected List<PackageConfig> parents;
    protected String name;
    protected String namespace = "";
    protected boolean isAbstract = false;
    protected boolean needsRefresh;

    protected PackageConfig(String name) {
        this.name = name;
        actionConfigs = new LinkedHashMap<String, ActionConfig>();
        parents = new ArrayList<PackageConfig>();
    }

    protected PackageConfig(PackageConfig orig) {
        this.name = orig.name;
        this.namespace = orig.namespace;
        this.isAbstract = orig.isAbstract;
        this.needsRefresh = orig.needsRefresh;
        this.actionConfigs = new LinkedHashMap<String, ActionConfig>(orig.actionConfigs);
        this.filterConfigs = new LinkedHashMap<String, Object>(orig.filterConfigs);
        this.segmentConfigs = new LinkedHashMap<String, Object>(orig.segmentConfigs);
        this.mappingConfigs = new LinkedHashMap<String, MappingConfig>(orig.mappingConfigs);
        this.parents = new ArrayList<PackageConfig>(orig.parents);
        this.location = orig.location;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public Map<String, ActionConfig> getActionConfigs() {
        return actionConfigs;
    }

    /**
     * returns the Map of all the ActionConfigs available in the current package.
     * ActionConfigs defined in ancestor packages will be included in this Map.
     *
     * @return a Map of ActionConfig Objects with the action name as the key
     * @see ActionConfig
     */
    public Map<String, ActionConfig> getAllActionConfigs() {
        Map<String, ActionConfig> retMap = new LinkedHashMap<String, ActionConfig>();

        if (!parents.isEmpty()) {
            for (PackageConfig parent : parents) {
                retMap.putAll(parent.getAllActionConfigs());
            }
        }

        retMap.putAll(getActionConfigs());

        return retMap;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public List<PackageConfig> getParents() {
        return new ArrayList<PackageConfig>(parents);
    }

    public boolean isNeedsRefresh() {
        return needsRefresh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PackageConfig)) {
            return false;
        }

        final PackageConfig packageConfig = (PackageConfig) o;

        if (isAbstract != packageConfig.isAbstract) {
            return false;
        }

        if ((actionConfigs != null) ? (!actionConfigs.equals(packageConfig.actionConfigs)) : (packageConfig.actionConfigs != null)) {
            return false;
        }

        if ((name != null) ? (!name.equals(packageConfig.name)) : (packageConfig.name != null)) {
            return false;
        }

        if ((namespace != null) ? (!namespace.equals(packageConfig.namespace)) : (packageConfig.namespace != null)) {
            return false;
        }

        if ((parents != null) ? (!parents.equals(packageConfig.parents)) : (packageConfig.parents != null)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ((name != null) ? name.hashCode() : 0);
        result = (29 * result) + ((parents != null) ? parents.hashCode() : 0);
        result = (29 * result) + ((actionConfigs != null) ? actionConfigs.hashCode() : 0);
        result = (29 * result) + ((namespace != null) ? namespace.hashCode() : 0);
        result = (29 * result) + (isAbstract ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        return "PackageConfig: [" + name + "] for namespace [" + namespace + "] with parents [" + parents + "]";
    }

    public int compareTo(Object o) {
        PackageConfig other = (PackageConfig) o;
        String full = namespace + "!" + name;
        String otherFull = other.namespace + "!" + other.name;

        // note, this isn't perfect (could come from different parents), but it is "good enough"
        return full.compareTo(otherFull);
    }
    
    public Object getFilterConfig(String name) {
        return getAllFilterConfigs().get(name);
    }
    
    public Map<String, Object> getAllFilterConfigs() {
        Map<String, Object> retMap = new LinkedHashMap<String, Object>();

        if (!parents.isEmpty()) {
            for (PackageConfig parentContext : parents) {
                retMap.putAll(parentContext.getAllFilterConfigs());
            }
        }

        retMap.putAll(getFilterConfigs());

        return retMap;
    }
    
    public Map<String, Object> getFilterConfigs() {
        return filterConfigs;
    }
    
    public Object getSegmentConfig(String name) {
        return getAllSegmentConfigs().get(name);
    }
    
    public Map<String, Object> getAllSegmentConfigs() {
        Map<String, Object> retMap = new LinkedHashMap<String, Object>();

        if (!parents.isEmpty()) {
            for (PackageConfig parentContext : parents) {
                retMap.putAll(parentContext.getAllSegmentConfigs());
            }
        }

        retMap.putAll(getSegmentConfigs());

        return retMap;
    }
    
    public Map<String, Object> getSegmentConfigs() {
        return segmentConfigs;
    }

    /**
     * The builder for this object.  An instance of this object is the only way to construct a new instance.  The
     * purpose is to enforce the immutability of the object.  The methods are structured in a way to support chaining.
     * After setting any values you need, call the {@link #build()} method to create the object.
     */
    public static class Builder {

        protected PackageConfig target;
        private boolean strictDMI;

        public Builder(String name) {
            target = new PackageConfig(name);
        }

        public Builder(PackageConfig config) {
            target = new PackageConfig(config);
        }

        public Builder name(String name) {
            target.name = name;
            return this;
        }

        public Builder isAbstract(boolean isAbstract) {
            target.isAbstract = isAbstract;
            return this;
        }

        public Builder namespace(String namespace) {
            if (namespace == null) {
                target.namespace = "";
            } else {
                target.namespace = namespace;
            }
            return this;
        }

        public Builder needsRefresh(boolean needsRefresh) {
            target.needsRefresh = needsRefresh;
            return this;
        }

        public Builder addActionConfig(String name, ActionConfig action) {
            target.actionConfigs.put(name, action);
            return this;
        }
        
        public Builder addFilterConfig(FilterConfig config) {
            target.filterConfigs.put(config.getName(), config);
            return this;
        }
        
        public Builder addFilterStackConfig(FilterStackConfig config) {
            target.filterConfigs.put(config.getName(), config);
            return this;
        }
        
        public Builder addSegmentConfig(SegmentConfig config) {
            target.segmentConfigs.put(config.getName(), config);
            return this;
        }
        
        public Builder addSegmentStackConfig(SegmentStackConfig config) {
            target.segmentConfigs.put(config.getName(), config);
            return this;
        }
        
        public Builder addMappingConfig(String name, MappingConfig config) {
            target.mappingConfigs.put(name, config);
            return this;
        }

        public Builder addParents(List<PackageConfig> parents) {
            for (PackageConfig config : parents) {
                addParent(config);
            }
            return this;
        }

        public Builder addParent(PackageConfig parent) {
            target.parents.add(0, parent);
            return this;
        }

        public Builder location(Location loc) {
            target.location = loc;
            return this;
        }

        public boolean isNeedsRefresh() {
            return target.needsRefresh;
        }

        public String getName() {
            return target.name;
        }

        public String getNamespace() {
            return target.namespace;
        }
        
        public Object getFilterConfig(String name) {
            return target.getAllFilterConfigs().get(name);
        }
        
        public Object getSegmentConfig(String name) {
            return target.getAllSegmentConfigs().get(name);
        }

        public Builder strictMethodInvocation(boolean strict) {
            strictDMI = strict;
            return this;
        }

        public boolean isStrictMethodInvocation() {
            return strictDMI;
        }

        public PackageConfig build() {
            embalmTarget();
            PackageConfig result = target;
            target = new PackageConfig(result);
            return result;
        }

        protected void embalmTarget() {
            target.actionConfigs = Collections.unmodifiableMap(target.actionConfigs);
            target.parents = Collections.unmodifiableList(target.parents);
        }

        @Override
        public String toString() {
            return "[BUILDER] " + target.toString();
        }
    }

}
