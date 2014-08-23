package com.github.mjeanroy.springmvc.uadetector.configuration;

import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.UserAgentStringParserConfiguration;
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
		UserAgentStringParserConfiguration.class,
		UADetectorConfiguration.class
})
public @interface EnableUADetector {

	/** Disable cache or use provided implementation. */
	UACacheProvider cache() default UACacheProvider.NONE;

}
