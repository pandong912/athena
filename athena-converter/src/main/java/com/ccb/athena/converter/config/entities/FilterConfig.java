/**
 * 
 */
package com.ccb.athena.converter.config.entities;

import java.io.Serializable;

public class FilterConfig implements Serializable {

	protected String name;
	protected String className;

	protected FilterConfig(String name, String className) {
		this.name = name;
		this.className = className;
	}

	protected FilterConfig(FilterConfig orig) {
		this.name = orig.name;
		this.className = orig.className;
	}

	public String getName() {
		return name;
	}
	
	public String getClassName() {
		return className;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FilterConfig)) {
            return false;
        }

        final FilterConfig filterConfig = (FilterConfig) o;

        if ((className != null) ? (!className.equals(filterConfig.className)) : (filterConfig.className != null)) {
            return false;
        }

        if ((name != null) ? (!name.equals(filterConfig.name)) : (filterConfig.name != null)) {
            return false;
        }
        
        return true;
	}

	@Override
	public String toString() {
		return "FilterConfig [id=" + name + ", clazz=" + className + "]";
	}

	public static final class Builder {
		protected FilterConfig target;

		public Builder(String name, String className) {
			this.target = new FilterConfig(name, className);
		}

		public Builder(FilterConfig orig) {
			this.target = new FilterConfig(orig);
		}

		public Builder name(String name) {
			this.target.name = name;
			return this;
		}

		public Builder className(String className) {
			this.target.className = className;
			return this;
		}

		public FilterConfig build() {
			embalmTarget();
			FilterConfig result = this.target;
			this.target = new FilterConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
