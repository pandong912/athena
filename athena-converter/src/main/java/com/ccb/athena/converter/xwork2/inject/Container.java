package com.ccb.athena.converter.xwork2.inject;

import java.io.Serializable;
import java.util.Set;

public interface Container extends Serializable {

	/**
	 * Default dependency name.
	 */
	String DEFAULT_NAME = "default";

	/**
	 * Injects dependencies into the fields and methods of an existing object.
	 */
	void inject(Object o);

	/**
	 * Creates and injects a new instance of type {@code implementation}.
	 */
	<T> T inject(Class<T> implementation);

	/**
	 * Gets an instance of the given dependency which was declared in
	 * {@link com.opensymphony.xwork2.inject.ContainerBuilder}.
	 */
	<T> T getInstance(Class<T> type, String name);

	/**
	 * Convenience method.&nbsp;Equivalent to {@code getInstance(type,
	 * DEFAULT_NAME)}.
	 */
	<T> T getInstance(Class<T> type);

	/**
	 * Gets a set of all registered names for the given type
	 * 
	 * @param type
	 *            The instance type
	 * @return A set of registered names or empty set if no instances are
	 *         registered for that type
	 */
	Set<String> getInstanceNames(Class<?> type);
	
	/**
	 * Sets the scope strategy for the current thread.
	 */
	void setScopeStrategy(Scope.Strategy scopeStrategy);

	/**
	 * Removes the scope strategy for the current thread.
	 */
	void removeScopeStrategy();
}
