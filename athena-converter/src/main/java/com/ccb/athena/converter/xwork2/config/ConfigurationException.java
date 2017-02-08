package com.ccb.athena.converter.xwork2.config;

import com.ccb.athena.converter.xwork2.XWorkException;

public class ConfigurationException extends XWorkException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8224022680115517901L;

	/**
     * Constructs a <code>ConfigurationException</code> with no detail message.
     */
    public ConfigurationException() {
    }

    /**
     * Constructs a <code>ConfigurationException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ConfigurationException(String s) {
        super(s);
    }
    
    /**
     * Constructs a <code>ConfigurationException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ConfigurationException(String s, Object target) {
        super(s, target);
    }

    /**
     * Constructs a <code>ConfigurationException</code> with no detail message.
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a <code>ConfigurationException</code> with no detail message.
     */
    public ConfigurationException(Throwable cause, Object target) {
        super(cause, target);
    }

    /**
     * Constructs a <code>ConfigurationException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ConfigurationException(String s, Throwable cause) {
        super(s, cause);
    }
    
    /**
     * Constructs a <code>ConfigurationException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ConfigurationException(String s, Throwable cause, Object target) {
        super(s, cause, target);
    }
}
