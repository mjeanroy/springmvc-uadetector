package com.github.mjeanroy.springmvc.uadetector.configuration.parsers;

import com.github.mjeanroy.springmvc.uadetector.commons.ClassUtils;
import com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector;
import com.github.mjeanroy.springmvc.uadetector.configuration.UACacheProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Selector that select parser configuration according to
 * {@link com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector} parameters.
 */
public class UserAgentStringParserSelectorConfiguration implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		UACacheProvider cacheStrategy = ClassUtils.getAnnotationValue(importingClassMetadata, EnableUADetector.class, "cache", UACacheProvider.NONE);
		Class klass = cacheStrategy.getConfigurationClass();
		return new String[]{ klass.getName() };
	}
}
