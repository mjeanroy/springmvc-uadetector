package com.github.mjeanroy.springmvc.uadetector.parsers;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * Build uadetector User Agent String Parser implementation with a guava cache
 * to not compute twice a user agent value.
 * This class delegated parsing to a dedicated uadetector parser.
 */
public class GuavaCachedUserAgentStringParser implements UserAgentStringParser {

	/** Internal parser implementation. */
	private final UserAgentStringParser parser;

	/** Internal cache. */
	private final LoadingCache<String, ReadableUserAgent> cache;

	/**
	 * Build parser with default internal parser implementation and a default Guava Cache.
	 *
	 * Default internal parser is retrieved using {@link net.sf.uadetector.service.UADetectorServiceFactory#getResourceModuleParser()} method.
	 *
	 * Guava cache is initialized with:
	 * - Maximum size of 100.
	 * - Two Hours expiration.
	 */
	public GuavaCachedUserAgentStringParser() {
		this.parser = defaultParser();
		this.cache = defaultCache();
	}

	/**
	 * Build parser with customer internal parser implementation and a default Guava Cache.
	 *
	 * Guava cache is initialized with:
	 * - Maximum size of 100.
	 * - Two Hours expiration.
	 *
	 * @param parser Internal parser implementation.
	 */
	public GuavaCachedUserAgentStringParser(UserAgentStringParser parser) {
		this.parser = parser;
		this.cache = defaultCache();
	}

	/**
	 * Build parser with default internal parser implementation and a custom Guava Cache.
	 *
	 * Default internal parser is retrieved using {@link net.sf.uadetector.service.UADetectorServiceFactory#getResourceModuleParser()} method.
	 *
	 * @param cacheBuilder Guava cache builder used to create new guava cache.
	 */
	public GuavaCachedUserAgentStringParser(CacheBuilder<Object, Object> cacheBuilder) {
		this.parser = defaultParser();
		this.cache = buildCache(cacheBuilder);
	}

	/**
	 * Build parser with custom parser implementation and a custom Guava Cache.
	 *
	 * @param parser Internal parser implementation.
	 * @param cacheBuilder Guava cache builder used to create new guava cache.
	 */
	public GuavaCachedUserAgentStringParser(UserAgentStringParser parser, CacheBuilder<Object, Object> cacheBuilder) {
		this.parser = parser;
		this.cache = buildCache(cacheBuilder);
	}

	/**
	 * Create default internal parser.
	 *
	 * @return Default parser.
	 */
	private UserAgentStringParser defaultParser() {
		return UADetectorServiceFactory.getResourceModuleParser();
	}

	/**
	 * Create guava cache using default guava cache builder.
	 *
	 * @return Default cache.
	 */
	private LoadingCache<String, ReadableUserAgent> defaultCache() {
		final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterWrite(2, TimeUnit.HOURS);

		return buildCache(cacheBuilder);
	}

	/**
	 * Build guava cache using given cache builder.
	 *
	 * @param builder Cache builder.
	 * @return Cache using given builder.
	 */
	private LoadingCache<String, ReadableUserAgent> buildCache(CacheBuilder<Object, Object> builder) {
		return builder.build(new GuavaReadableUserAgentCacheLoader(parser));
	}

	@Override
	public String getDataVersion() {
		return parser.getDataVersion();
	}

	@Override
	public ReadableUserAgent parse(final String userAgent) {
		return cache.getUnchecked(userAgent);
	}

	@Override
	public void shutdown() {
		cache.invalidateAll();
		parser.shutdown();
	}
}
