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

package com.github.mjeanroy.springmvc.uadetector.cache.simple;

import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class SimpleCacheTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent userAgent;

	@Test
	public void it_should_build_simple_cache_with_parser() throws Exception {
		SimpleCache cache = new SimpleCache(parser);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(cache, "parser", true);
		ConcurrentMap<String, ReadableUserAgent> internalCache = (ConcurrentMap<String, ReadableUserAgent>) readField(cache, "cache", true);

		assertThat(internalParser).isNotNull().isSameAs(parser);
		assertThat(internalCache).isNotNull().isEmpty();
	}

	@Test
	public void it_should_parse_value_once() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);
		SimpleCache cache = new SimpleCache(parser);

		ReadableUserAgent r1 = cache.get("foo");
		ReadableUserAgent r2 = cache.get("foo");

		assertThat(r1).isNotNull().isSameAs(r2).isSameAs(userAgent);

		ConcurrentMap<String, ReadableUserAgent> internalCache = (ConcurrentMap<String, ReadableUserAgent>) readField(cache, "cache", true);
		assertThat(internalCache).isNotNull().isNotEmpty().hasSize(1);
		verify(parser, times(1)).parse(ua);
	}

	@Test
	public void it_should_clear_cache() throws Exception {
		when(parser.parse("foo")).thenReturn(userAgent);
		SimpleCache cache = new SimpleCache(parser);
		cache.get("foo");

		ConcurrentMap<String, ReadableUserAgent> internalCache = (ConcurrentMap<String, ReadableUserAgent>) readField(cache, "cache", true);
		assertThat(internalCache).isNotNull().isNotEmpty().hasSize(1);

		cache.shutdown();

		internalCache = (ConcurrentMap<String, ReadableUserAgent>) readField(cache, "cache", true);
		assertThat(internalCache).isNotNull().isEmpty();
	}
}
