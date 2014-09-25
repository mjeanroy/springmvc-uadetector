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

import static com.github.mjeanroy.springmvc.uadetector.commons.PreConditions.notNull;

import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.cache.simple.SimpleCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Simple parser using a cache implementation.
 * Cache implementation is free and must be an implementation of {@link UADetectorCache}.
 */
public class CachedUserAgentStringParser implements UserAgentStringParser {

	/**
	 * Parser implementation.
	 */
	private final UserAgentStringParser parser;

	/**
	 * Cache implementation.
	 * Default is to use an instance of {@link SimpleCache}.
	 */
	private final UADetectorCache cache;

	/**
	 * Build cached parser using custom internal parser.
	 *
	 * @param parser Internal parser.
	 */
	public CachedUserAgentStringParser(final UserAgentStringParser parser) {
		this.parser = notNull(parser, "Parser must not be null");
		this.cache = new SimpleCache(parser);
	}

	/**
	 * Build cached parser using custom internal parser and custom cache
	 * implementation.
	 *
	 * @param parser Internal parser.
	 * @param cache Cache implementation.
	 */
	public CachedUserAgentStringParser(final UserAgentStringParser parser, final UADetectorCache cache) {
		this.parser = notNull(parser, "Parser must not be null");
		this.cache = notNull(cache, "Cache must not be null");
	}

	@Override
	public String getDataVersion() {
		return parser.getDataVersion();
	}

	@Override
	public ReadableUserAgent parse(final String userAgent) {
		return cache.get(userAgent);
	}

	@Override
	public void shutdown() {
		cache.shutdown();
		parser.shutdown();
	}

	/**
	 * Return parser used internally.
	 *
	 * @return Internal parser.
	 */
	public UserAgentStringParser getParser() {
		return parser;
	}
}
