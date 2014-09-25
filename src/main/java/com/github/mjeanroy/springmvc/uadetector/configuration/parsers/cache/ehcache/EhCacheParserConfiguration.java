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

package com.github.mjeanroy.springmvc.uadetector.configuration.parsers.cache.ehcache;

import org.springframework.context.annotation.Configuration;

import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.cache.ehcache.EhCacheCache;
import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.cache.AbstractCacheConfiguration;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Configuration that use a parser using a cache provided
 * by ehcache library.
 * EhCache must be on classpath to use it.
 */
@Configuration
public class EhCacheParserConfiguration extends AbstractCacheConfiguration {

	protected UADetectorCache cache(UserAgentStringParser parser) {
		CacheManager cacheManager = CacheManager.getInstance();

		Cache ehCache = cacheManager.getCache("uadetector");
		CacheConfiguration configuration = ehCache.getCacheConfiguration();
		configuration.setMemoryStoreEvictionPolicy("LRU");
		configuration.setMaxEntriesLocalHeap(getMaximumSize());
		configuration.setTimeToLiveSeconds(getTimeToLiveInSeconds());

		return new EhCacheCache(ehCache, parser);
	}
}
