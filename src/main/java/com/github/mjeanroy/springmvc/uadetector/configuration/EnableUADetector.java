package com.github.mjeanroy.springmvc.uadetector.configuration;

import com.github.mjeanroy.springmvc.uadetector.configuration.factories.UADetectorFactoriesSelectorConfiguration;
import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.UserAgentStringParserSelectorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
		UserAgentStringParserSelectorConfiguration.class,
		UADetectorFactoriesSelectorConfiguration.class,
		UADetectorConfiguration.class
})
public @interface EnableUADetector {

	/**
	 * Disable cache or use provided implementation.
	 * Default is no cache.
	 *
	 * @return Cache strategy to use.
	 */
	UACacheProvider cache() default UACacheProvider.NONE;

	/**
	 * Enable autowiring of {@link net.sf.uadetector.ReadableUserAgent}
	 * and {@link com.github.mjeanroy.springmvc.uadetector.tools.Browser} instances.
	 *
	 * Since these objects required a request scope and proxy mode, it is disabled
	 * by default.
	 *
	 * @return True to enable autowiring, false otherwise.
	 */
	boolean autowired() default false;
}
