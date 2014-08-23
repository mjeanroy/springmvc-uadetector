package com.github.mjeanroy.springmvc.uadetector.resolvers;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;
import com.github.mjeanroy.springmvc.uadetector.tools.UADetectorBrowser;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Resolver that can be used to get user agent arguments.
 */
public class BrowserResolver implements WebArgumentResolver {

	/**
	 * Parser used to read user-agent header and detect device and browsers.
	 * Parser is thread safe and can be used concurrently.
	 */
	private final UserAgentStringParser parser;

	/** Create resolver with a default parser. */
	public BrowserResolver() {
		this.parser = UADetectorServiceFactory.getResourceModuleParser();
	}

	/**
	 * Create resolver with a custom parser.
	 *
	 * @param parser Parser.
	 */
	public BrowserResolver(UserAgentStringParser parser) {
		this.parser = parser;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		final Class klass = methodParameter.getParameterType();

		if (Browser.class.isAssignableFrom(klass)) {
			final String header = webRequest.getHeader("User-Agent");
			final ReadableUserAgent userAgent = parser.parse(header);
			return new UADetectorBrowser(userAgent);
		}

		return UNRESOLVED;
	}
}
