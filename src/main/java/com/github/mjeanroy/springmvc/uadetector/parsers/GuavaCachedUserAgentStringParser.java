/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.springmvc.uadetector.parsers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import java.util.concurrent.TimeUnit;

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
