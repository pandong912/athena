package com.ccb.athena.converter.config.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RuleConfig implements Serializable {

	public static final String DEFAULT_METHOD = "execute";
	
	protected String name;
	protected String className;
	protected String methodName;
	protected Map<String, String> params;

//	protected List<ParamConfig> list;
	
	public RuleConfig(String name, String className) {
		this.name = name;
		this.className = className;
		params = new LinkedHashMap<String, String>();
	}

	public RuleConfig(RuleConfig orig) {
		this.name = orig.name;
		this.className = orig.className;
		this.methodName = orig.methodName;
		this.params = new LinkedHashMap<String,String>(orig.params);
	}

	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
	
    public Map<String, String> getParams() {
        return params;
    }

//	public List<ParamConfig> getList() {
//		return list;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
//		result = prime * result + ((sheetName == null) ? 0 : sheetName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RuleConfig)) {
            return false;
        }

        final RuleConfig ruleConfig = (RuleConfig) o;

        if ((className != null) ? (!className.equals(ruleConfig.className)) : (ruleConfig.className != null)) {
            return false;
        }

        if ((name != null) ? (!name.equals(ruleConfig.name)) : (ruleConfig.name != null)) {
            return false;
        }

        if ((methodName != null) ? (!methodName.equals(ruleConfig.methodName)) : (ruleConfig.methodName != null)) {
            return false;
        }
        
        return true;
	}
	
	@Override
	public String toString() {
		return "RuleConfig [id=" + name + ", clazz=" + className + ", method=" + methodName + "]";
	}

	public static final class Builder {
		protected RuleConfig target;

		public Builder(String name, String className) {
			target = new RuleConfig(name, className);
		}

		public Builder(RuleConfig orig) {
			target = new RuleConfig(orig);
		}

		public Builder name(String name) {
			target.name = name;
			return this;
		}

		public Builder className(String className) {
			target.className = className;
			return this;
		}

		public RuleConfig build() {
			embalmTarget();
			RuleConfig result = this.target;
			target = new RuleConfig(this.target);
			return result;
		}

		protected void embalmTarget() {
			target.params = Collections.unmodifiableMap(target.params);
		}
	}
}
