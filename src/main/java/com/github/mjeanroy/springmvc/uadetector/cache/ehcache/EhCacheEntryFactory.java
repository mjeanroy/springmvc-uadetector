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

package com.github.mjeanroy.springmvc.uadetector.cache.ehcache;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Factory that create cache entry.
 * This factory should be used with an instance of {@link EhCacheCache}.
 */
public class EhCacheEntryFactory implements CacheEntryFactory {

	/**
	 * UADetector parser.
	 * This parser will be used to parse user agent header to concrete {@link net.sf.uadetector.ReadableUserAgent} instances.
	 */
	private final UserAgentStringParser parser;

	/**
	 * Create new factory.
	 *
	 * @param parser Parser to use to parser user agent value to {@link net.sf.uadetector.ReadableUserAgent}.
	 */
	public EhCacheEntryFactory(UserAgentStringParser parser) {
		this.parser = parser;
	}

	@Override
	public Object createEntry(Object userAgent) throws Exception {
		return parser.parse((String) userAgent);
	}
}
