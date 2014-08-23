package com.github.mjeanroy.springmvc.uadetector.factories;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;
import com.github.mjeanroy.springmvc.uadetector.tools.UADetectorBrowser;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Factory used to create {@link com.github.mjeanroy.springmvc.uadetector.tools.Browser} bean with
 * scope request.
 *
 * An instance of {@link net.sf.uadetector.UserAgentStringParser} must be available
 * in spring context.
 */
public class BrowserFactoryBean implements FactoryBean<Browser> {

	@Autowired
	private UserAgentStringParser parser;

	@Autowired
	private HttpServletRequest request;

	@Override
	public Browser getObject() throws Exception {
		String ua = request.getHeader("User-Agent");
		ReadableUserAgent userAgent = parser.parse(ua);
		return new UADetectorBrowser(userAgent);
	}

	@Override
	public Class<?> getObjectType() {
		return Browser.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
