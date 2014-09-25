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

import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class EhCacheCacheTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent userAgent;

	private CacheManager cacheManager = CacheManager.getInstance();

	private Cache ehCache;

	@Before
	public void setUp() {
		cacheManager.addCache("uadetector");

		ehCache = cacheManager.getCache("uadetector");
		CacheConfiguration configuration = ehCache.getCacheConfiguration();
		configuration.setMemoryStoreEvictionPolicy("LRU");
		configuration.setMaxEntriesLocalHeap(100);
		configuration.setTimeToLiveSeconds(3);
	}

	@After
	public void tearDown() {
		cacheManager.removeAllCaches();
	}

	@Test
	public void it_should_build_simple_cache_with_parser() throws Exception {
		EhCacheCache cache = new EhCacheCache(ehCache, parser);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(cache, "parser", true);
		Ehcache internalCache = (Ehcache) readField(cache, "cache", true);

		assertThat(internalParser).isNotNull().isSameAs(parser);
		assertThat(internalCache).isNotNull();
		assertThat(internalCache.getSize()).isZero();
	}

	@Test
	public void it_should_parse_value_once() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);
		EhCacheCache cache = new EhCacheCache(ehCache, parser);

		ReadableUserAgent r1 = cache.get("foo");
		ReadableUserAgent r2 = cache.get("foo");

		Ehcache internalCache = (Ehcache) readField(cache, "cache", true);

		assertThat(internalCache.getSize()).isNotZero().isEqualTo(1);
		assertThat(r1).isNotNull().isSameAs(r2).isSameAs(userAgent);
		verify(parser, times(1)).parse(ua);

	}

	@Test
	public void it_should_clear_cache() throws Exception {
		when(parser.parse("foo")).thenReturn(userAgent);
		EhCacheCache cache = new EhCacheCache(ehCache, parser);
		cache.get("foo");

		Ehcache internalCache = (Ehcache) readField(cache, "cache", true);
		assertThat(internalCache.getSize()).isNotZero().isEqualTo(1);

		cache.shutdown();

		internalCache = (Ehcache) readField(cache, "cache", true);
		assertThat(internalCache.getSize()).isZero();
	}
}
