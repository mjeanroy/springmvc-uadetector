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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.parser.UserAgentStringParserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class GuavaCachedUserAgentStringParserTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private Cache cache;

	@Mock
	private ReadableUserAgent userAgent;

	@Test
	public void it_should_build_guava_parser_with_default_parser() throws Exception {
		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser();

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(guavaParser, "parser", true);
		LoadingCache internalCache = (LoadingCache) readField(guavaParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isExactlyInstanceOf(UserAgentStringParserImpl.class);

		assertThat(internalCache)
				.isNotNull();
	}

	@Test
	public void it_should_build_guava_parser_with_custom_parser_and_default_cache() throws Exception {
		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(guavaParser, "parser", true);
		LoadingCache internalCache = (LoadingCache) readField(guavaParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isSameAs(parser);

		assertThat(internalCache)
				.isNotNull();
	}

	@Test
	public void it_should_build_guava_parser_with_default_parser_and_custom_cache() throws Exception {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(builder);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(guavaParser, "parser", true);
		LoadingCache internalCache = (LoadingCache) readField(guavaParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isExactlyInstanceOf(UserAgentStringParserImpl.class);

		assertThat(internalCache)
				.isNotNull();
	}

	@Test
	public void it_should_build_guava_parser_with_custom_parser_and_custom_cache() throws Exception {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser, builder);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(guavaParser, "parser", true);
		LoadingCache internalCache = (LoadingCache) readField(guavaParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isSameAs(parser);

		assertThat(internalCache)
				.isNotNull();
	}

	@Test
	public void it_should_get_parser_data_version() throws Exception {
		String dv = "foo";
		when(parser.getDataVersion()).thenReturn(dv);

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser);

		String dataVersion = guavaParser.getDataVersion();

		assertThat(dataVersion)
				.isNotNull()
				.isEqualTo(dv);

		verify(parser).getDataVersion();
	}

	@Test
	public void it_should_shutdown_parser_and_clear_cache() throws Exception {
		String dv = "foo";
		when(parser.getDataVersion()).thenReturn(dv);

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser);

		LoadingCache internalCache = (LoadingCache) readField(guavaParser, "cache", true);
		internalCache.put("foo", "bar");
		assertThat(internalCache.asMap()).containsKey("foo");

		guavaParser.shutdown();

		verify(parser).shutdown();
		assertThat(internalCache.asMap()).doesNotContainKey("foo");
	}

	@Test
	public void it_should_get_user_agent_value() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser);

		ReadableUserAgent result = guavaParser.parse(ua);

		assertThat(result)
				.isNotNull()
				.isSameAs(userAgent);

		verify(parser).parse(ua);
	}

	@Test
	public void it_should_get_user_agent_value_once() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);

		GuavaCachedUserAgentStringParser guavaParser = new GuavaCachedUserAgentStringParser(parser);

		ReadableUserAgent result1 = guavaParser.parse(ua);
		ReadableUserAgent result2 = guavaParser.parse(ua);

		assertThat(result1)
				.isNotNull()
				.isSameAs(userAgent);

		assertThat(result2)
				.isNotNull()
				.isSameAs(userAgent);

		verify(parser, times(1)).parse(ua);
	}
}
