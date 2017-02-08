/**
 * 
 */
package com.ccb.athena.converter.config.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MappingConfig implements Serializable {

	protected String name;
	protected String source;
	protected List<FilterMapping> filters;
	protected RequestConfig requestConfig;
	protected ResponseConfig responseConfig;

	public MappingConfig(String name, String source) {
		this.name = name;
		this.source = source;
		filters = new ArrayList<FilterMapping>();
	}

	public MappingConfig(MappingConfig orig) {
		this.name = orig.name;
		this.source = orig.source;
		this.filters = new ArrayList<FilterMapping>(orig.getFilters());
		this.requestConfig = orig.requestConfig;
		this.responseConfig = orig.responseConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((requestConfig == null) ? 0 : requestConfig.hashCode());
		result = prime * result + ((responseConfig == null) ? 0 : responseConfig.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappingConfig other = (MappingConfig) obj;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (requestConfig == null) {
			if (other.requestConfig != null)
				return false;
		} else if (!requestConfig.equals(other.requestConfig))
			return false;
		if (responseConfig == null) {
			if (other.responseConfig != null)
				return false;
		} else if (!responseConfig.equals(other.responseConfig))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MappingConfig [name=" + name + ", source=" + source + ", filters=" + filters + ", requestConfig=" + requestConfig + ", responseConfig=" + responseConfig + "]";
	}

	public String getName() {
		return name;
	}

	public String getSource() {
		return source;
	}

	public List<FilterMapping> getFilters() {
		return filters;
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public ResponseConfig getResponseConfig() {
		return responseConfig;
	}

	public static final class Builder {
		protected MappingConfig target;

		public Builder(String name, String source) {
			this.target = new MappingConfig(name, source);
		}

		public Builder(MappingConfig orig) {
			this.target = new MappingConfig(orig);
		}

		public Builder name(String name) {
			this.target.name = name;
			return this;
		}

		public Builder source(String source) {
			this.target.source = source;
			return this;
		}

		public Builder addFilter(FilterMapping filter) {
			target.filters.add(filter);
			return this;
		}

		public Builder addFilters(List<FilterMapping> filters) {
			target.filters.addAll(filters);
			return this;
		}

		public Builder filters(List<FilterMapping> filters) {
			target.filters.clear();
			target.filters.addAll(filters);
			return this;
		}

		public Builder addRequestConfig(RequestConfig requestConfig) {
			this.target.requestConfig = requestConfig;
			return this;
		}

		public Builder addResponseConfig(ResponseConfig responseConfig) {
			this.target.responseConfig = responseConfig;
			return this;
		}

		public MappingConfig build() {
			embalmTarget();
			MappingConfig result = this.target;
			this.target = new MappingConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
