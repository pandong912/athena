package com.ccb.athena.converter.config.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterStackConfig implements Serializable {

	protected String name;
	protected List<FilterMapping> filters;

	protected FilterStackConfig(String name) {
		this.name = name;
		this.filters = new ArrayList<>();
	}

	protected FilterStackConfig(FilterStackConfig orig) {
		this.name = orig.name;
		this.filters = new ArrayList<FilterMapping>(orig.filters);
	}
	
	public String getName() {
		return name;
	}
	
	public List<FilterMapping> getFilters() {
		return filters;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
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

        final FilterStackConfig filterStackConfig = (FilterStackConfig) o;

        if ((filters != null) ? (!filters.equals(filterStackConfig.filters)) : (filterStackConfig.filters != null)) {
            return false;
        }

        if ((name != null) ? (!name.equals(filterStackConfig.name)) : (filterStackConfig.name != null)) {
            return false;
        }
        
        return true;
	}

	@Override
	public String toString() {
		return "FilterStackConfig [filters=" + filters + ", name=" + name + "]";
	}

	public static class Builder {
		protected FilterStackConfig target;

		public Builder(String name) {
			this.target = new FilterStackConfig(name);
		}

		public Builder name(String name) {
			this.target.name = name;
			return this;
		}

		public Builder addFilter(FilterMapping filterMapping) {
			this.target.filters.add(filterMapping);
			return this;
		}

		public Builder addFilters(List<FilterMapping> filters) {
			this.target.filters.addAll(filters);
			return this;
		}

		public FilterStackConfig build() {
			embalmTarget();
			FilterStackConfig result = this.target;
			this.target = new FilterStackConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
