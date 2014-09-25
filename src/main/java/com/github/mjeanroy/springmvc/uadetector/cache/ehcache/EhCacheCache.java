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

import com.github.mjeanroy.springmvc.uadetector.cache.AbstractUADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

/**
 * UADetector cache implementation that use ehcache internally.
 */
public class EhCacheCache extends AbstractUADetectorCache implements UADetectorCache {

	/**
	 * EhCache instance.
	 * This cache is used to store computed {@link ReadableUserAgent} instances.
	 */
	private final Ehcache cache;

	/**
	 * Create new cache.
	 *
	 * @param cache Cache where computed values are stored.
	 * @param parser Parser to compute concrete {@link ReadableUserAgent} instances.
	 */
	public EhCacheCache(Ehcache cache, UserAgentStringParser parser) {
		super(parser);
		this.cache = new SelfPopulatingCache(cache, new EhCacheEntryFactory(parser));
	}

	@Override
	public ReadableUserAgent get(String userAgent) {
		return (ReadableUserAgent) cache.get(userAgent).getObjectValue();
	}

	@Override
	public void shutdown() {
		cache.removeAll();
	}
}
