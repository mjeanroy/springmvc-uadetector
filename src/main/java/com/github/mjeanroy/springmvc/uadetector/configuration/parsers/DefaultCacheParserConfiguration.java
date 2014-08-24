package com.github.mjeanroy.springmvc.uadetector.configuration.parsers;

import com.github.mjeanroy.springmvc.uadetector.parsers.CachedUserAgentStringParser;
import net.sf.uadetector.UserAgentStringParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration that use parser with default cache (using a concurrent
 * hash map implementation).
 */
@Configuration
public class DefaultCacheParserConfiguration {

	@Bean(destroyMethod = "shutdown")
	public UserAgentStringParser userAgentStringParser() {
		return new CachedUserAgentStringParser();
	}
}
