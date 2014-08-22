package com.github.mjeanroy.springmvc.uadetector.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import com.github.mjeanroy.springmvc.uadetector.resolvers.BrowserResolver;
import com.github.mjeanroy.springmvc.uadetector.resolvers.ReadableUserAgentResolver;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

@Configuration
public class UADetectorConfiguration extends WebMvcConfigurerAdapter {

	@Autowired(required = false)
	private UserAgentStringParser parser;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(readableUserAgentResolver()));
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(browserResolver()));
	}

	@Bean
	public ReadableUserAgentResolver readableUserAgentResolver() {
		return new ReadableUserAgentResolver(parser());
	}

	@Bean
	public BrowserResolver browserResolver() {
		return new BrowserResolver(parser());
	}

	private UserAgentStringParser parser() {
		return parser != null ? parser : UADetectorServiceFactory.getResourceModuleParser();
	}
}
