package com.github.mjeanroy.springmvc.uadetector.commons;

import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Static Utilities.
 */
@SuppressWarnings("unchecked")
public final class ClassUtils {

	private ClassUtils() {
	}

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

	/**
	 * Get annotation method value.
	 *
	 * @param importingClassMetadata Metadata.
	 * @param annotationClass        Annotation class to look for.
	 * @param name                   Name of method.
	 * @param defaultValue           Default value if original value is null.
	 * @param <T>                    Type of returned value.
	 * @return Annotation value, or default value if original value is null.
	 */
	public static <T> T getAnnotationValue(AnnotationMetadata importingClassMetadata, Class annotationClass, String name, T defaultValue) {
		Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(annotationClass.getName());
		T value = (T) attributes.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}
}
