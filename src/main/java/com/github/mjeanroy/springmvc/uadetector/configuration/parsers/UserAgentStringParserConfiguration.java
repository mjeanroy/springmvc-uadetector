package com.github.mjeanroy.springmvc.uadetector.configuration.parsers;

import com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector;
import com.github.mjeanroy.springmvc.uadetector.configuration.UACacheProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Selector that select parser configuration according to
 * {@link com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector} parameters.
 */
public class UserAgentStringParserConfiguration implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableUADetector.class.getName());

		UACacheProvider cacheStrategy = (UACacheProvider) attributes.get("cache");

		// null should mean no cache
		if (cacheStrategy == null) {
			cacheStrategy = UACacheProvider.NONE;
		}

		Class klass = cacheStrategy.getConfigurationClass();
		String name = klass.getName();

		return new String[]{ name };
	}
}
