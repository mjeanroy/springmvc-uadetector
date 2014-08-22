package com.github.mjeanroy.springmvc.uadetector.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * Resolver that can be used to get user agent arguments.
 */
public class ReadableUserAgentResolver implements WebArgumentResolver {

	/**
	 * Parser used to read user-agent header and detect device and browsers.
	 * Parser is thread safe and can be used concurrently.
	 */
	private final UserAgentStringParser parser;

	/**
	 * Create resolver with a default parser.
	 */
	public ReadableUserAgentResolver() {
		this.parser = UADetectorServiceFactory.getResourceModuleParser();
	}

	/**
	 * Create resolver with a custom parser.
	 *
	 * @param parser Parser.
	 */
	public ReadableUserAgentResolver(UserAgentStringParser parser) {
		this.parser = parser;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		final Class klass = methodParameter.getParameterType();

		if (ReadableUserAgent.class.isAssignableFrom(klass)) {
			final String header = webRequest.getHeader("User-Agent");
			return parser.parse(header);
		}

		return UNRESOLVED;
	}
}
