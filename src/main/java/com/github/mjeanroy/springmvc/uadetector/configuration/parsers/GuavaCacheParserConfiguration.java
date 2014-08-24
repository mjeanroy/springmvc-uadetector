package com.github.mjeanroy.springmvc.uadetector.configuration.parsers;

import com.github.mjeanroy.springmvc.uadetector.parsers.GuavaCachedUserAgentStringParser;
import net.sf.uadetector.UserAgentStringParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration that use a parser using a cache provided
 * by Guava library.
 * Guava must be on classpath to use it.
 */
@Configuration
public class GuavaCacheParserConfiguration {

	@Bean(destroyMethod = "shutdown")
	public UserAgentStringParser userAgentStringParser() {
		return new GuavaCachedUserAgentStringParser();
	}
}
