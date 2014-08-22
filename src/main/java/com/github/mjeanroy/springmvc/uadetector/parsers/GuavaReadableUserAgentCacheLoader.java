package com.github.mjeanroy.springmvc.uadetector.parsers;

import com.google.common.cache.CacheLoader;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Cache loader to user with guava version of uadetector parser.
 * This implementation is thread safe.
 */
public class GuavaReadableUserAgentCacheLoader extends CacheLoader<String, ReadableUserAgent> {

	/** User Agent Parser. */
	private final UserAgentStringParser parser;

	/**
	 * Build new cache loader using given parser.
	 *
	 * @param parser Parser.
	 */
	public GuavaReadableUserAgentCacheLoader(UserAgentStringParser parser) {
		this.parser = parser;
	}

	@Override
	public ReadableUserAgent load(String userAgent) throws Exception {
		return parser.parse(userAgent);
	}
}
