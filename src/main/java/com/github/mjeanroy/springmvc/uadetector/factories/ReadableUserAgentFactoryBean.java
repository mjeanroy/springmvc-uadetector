package com.github.mjeanroy.springmvc.uadetector.factories;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Factory used to create {@link net.sf.uadetector.ReadableUserAgent} bean with
 * scope request.
 *
 * An instance of {@link net.sf.uadetector.UserAgentStringParser} must be available
 * in spring context.
 */
public class ReadableUserAgentFactoryBean implements FactoryBean<ReadableUserAgent> {

	@Autowired
	private UserAgentStringParser parser;

	@Autowired
	private HttpServletRequest request;

	@Override
	public ReadableUserAgent getObject() throws Exception {
		String ua = request.getHeader("User-Agent");
		return parser.parse(ua);
	}

	@Override
	public Class<?> getObjectType() {
		return ReadableUserAgent.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
