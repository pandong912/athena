package com.ccb.athena.converter.config.entities;

import java.io.Serializable;

import com.ccb.athena.converter.filter.Filter;

public class FilterMapping implements Serializable {
	
	protected String name;
	protected Filter filter;

	public FilterMapping(String name, Filter filter) {
		this.name = name;
		this.filter = filter;
	}

	public String getName() {
		return this.name;
	}

	public Filter getFilter() {
		return this.filter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FilterMapping)) {
            return false;
        }

        final FilterMapping filterMapping = (FilterMapping) o;

        if ((filter != null) ? (!filter.equals(filterMapping.filter)) : (filterMapping.filter != null)) {
            return false;
        }

        if ((name != null) ? (!name.equals(filterMapping.name)) : (filterMapping.name != null)) {
            return false;
        }
        
        return true;
	}

	@Override
	public String toString() {
		return "FilterMapping [name=" + name + ", filter=" + filter + "]";
	}
}
