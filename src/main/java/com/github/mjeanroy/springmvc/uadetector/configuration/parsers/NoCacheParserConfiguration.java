package com.github.mjeanroy.springmvc.uadetector.configuration.parsers;

import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration that use default user agent string parser (no cache).
 */
@Configuration
public class NoCacheParserConfiguration {

	@Bean(destroyMethod = "shutdown")
	public UserAgentStringParser userAgentStringparser() {
		return UADetectorServiceFactory.getResourceModuleParser();
	}
}
