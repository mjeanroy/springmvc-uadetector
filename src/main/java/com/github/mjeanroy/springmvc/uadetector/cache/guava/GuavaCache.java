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

package com.github.mjeanroy.springmvc.uadetector.cache.guava;

import com.github.mjeanroy.springmvc.uadetector.cache.AbstractUADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Cache implementation using guava cache implementation.
 */
public class GuavaCache extends AbstractUADetectorCache implements UADetectorCache {

	/**
	 * Guava cache instance use internally.
	 */
	private final LoadingCache<String, ReadableUserAgent> cache;

	/**
	 * Create Guava cache instance.
	 *
	 * @param builder Guava cache builder.
	 * @param parser User Agent parser.
	 */
	public GuavaCache(CacheBuilder<Object, Object> builder, UserAgentStringParser parser) {
		super(parser);
		this.cache = builder.build(new GuavaCacheLoader(parser));
	}

	@Override
	public ReadableUserAgent get(String userAgent) {
		return cache.getUnchecked(userAgent);
	}

	@Override
	public void shutdown() {
		cache.invalidateAll();
	}
}
