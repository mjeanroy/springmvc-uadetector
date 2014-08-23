package com.github.mjeanroy.springmvc.uadetector.configuration.factories;

import com.github.mjeanroy.springmvc.uadetector.factories.BrowserFactoryBean;
import com.github.mjeanroy.springmvc.uadetector.factories.ReadableUserAgentFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Configuration that let applications use autowiring
 * with {@link net.sf.uadetector.ReadableUserAgent} and
 * {@link com.github.mjeanroy.springmvc.uadetector.tools.Browser} instances.
 *
 * Be careful, this configuration is not enabled by default.
 */
@Configuration
public class UADetectorFactoriesConfiguration {

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ReadableUserAgentFactoryBean readableUserAgentFactoryBean() {
		return new ReadableUserAgentFactoryBean();
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public BrowserFactoryBean browserFactoryBean() {
		return new BrowserFactoryBean();
	}
}
