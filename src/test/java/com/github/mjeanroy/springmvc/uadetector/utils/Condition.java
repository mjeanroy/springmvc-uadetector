package com.github.mjeanroy.springmvc.uadetector.utils;

/**
 * Abstract interface that can be implemented to check
 * condition in unit tests.
 */
public interface Condition {

	/**
	 * Check condition.
	 *
	 * @return True if condition is valid, false otherwise.
	 */
	boolean check();
}
