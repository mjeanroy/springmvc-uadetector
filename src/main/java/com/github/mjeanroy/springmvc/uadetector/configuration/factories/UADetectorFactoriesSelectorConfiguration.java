package com.github.mjeanroy.springmvc.uadetector.configuration.factories;

import com.github.mjeanroy.springmvc.uadetector.commons.ClassUtils;
import com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Selector that select add {@link com.github.mjeanroy.springmvc.uadetector.configuration.factories.UADetectorFactoriesConfiguration}
 * configuration class if {@link com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector#autowired()}
 * returned true, otherwise, no additional configuration is added.
 */
public class UADetectorFactoriesSelectorConfiguration implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		boolean autowiredStrategy = ClassUtils.getAnnotationValue(importingClassMetadata, EnableUADetector.class, "autowired", false);
		if (autowiredStrategy) {
			return new String[]{ UADetectorFactoriesConfiguration.class.getName() };
		} else {
			return new String[]{ };
		}
	}
}
