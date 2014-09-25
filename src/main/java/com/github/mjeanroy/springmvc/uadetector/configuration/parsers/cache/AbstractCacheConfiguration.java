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

package com.github.mjeanroy.springmvc.uadetector.configuration.parsers.cache;

import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.parsers.CachedUserAgentStringParser;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * Abstract configuration that define uadetector configuration based on
 * cache implementation.
 * The cache implementation must be provided by subclasses.
 */
@Configuration
@PropertySource(
		value = "classpath:uadetector.properties",
		ignoreResourceNotFound = true
)
public abstract class AbstractCacheConfiguration {

	/**
	 * Default cache size.
	 * The default is 100.
	 */
	public static final long DEFAULT_MAXIMUM_SIZE = 100;

	/**
	 * Default Time To Live (TTL) value.
	 * The default is one day.
	 */
	public static final long DEFAULT_TTL = 3600 * 24;

	@Autowired
	private Environment environment;

	@Bean(destroyMethod = "shutdown")
	public UserAgentStringParser userAgentStringParser() {
		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
		UADetectorCache cache = cache(parser);
		return new CachedUserAgentStringParser(parser, cache);
	}

	/**
	 * Build specific cache implementation.
	 *
	 * @param parser Internal parser.
	 * @return Cache implementation.
	 */
	protected abstract UADetectorCache cache(UserAgentStringParser parser);

	/**
	 * Get maximum size of cache.
	 *
	 * @return Maximum cache size.
	 */
	protected long getMaximumSize() {
		return parseLong(environment.getProperty("uadetector.maxSize", valueOf(DEFAULT_MAXIMUM_SIZE)));
	}

	/**
	 * Get TTL (Time To Live) value: this is the time entries are stored in cache before being evicted.
	 * @return Time To Live value.
	 */
	protected long getTimeToLiveInSeconds() {
		return parseLong(environment.getProperty("uadetector.ttl", valueOf(DEFAULT_TTL)));
	}
}
