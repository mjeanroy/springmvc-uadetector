package com.github.mjeanroy.springmvc.uadetector.commons;

/**
 * Static Utilities.
 */
public final class ClassUtils {

	/**
	 * Check that a given class is available on classpath.
	 *
	 * @param className Fully Qualified Class Name.
	 * @return True if class is available on classpath, false otherwise.
	 */
	public static boolean isPresent(String className) {
		try {
			Class.forName(className);
			return true;
		}
		catch (Throwable ex) {
			return false;
		}
	}
}
