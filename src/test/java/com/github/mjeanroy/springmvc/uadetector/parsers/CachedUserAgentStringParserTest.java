package com.github.mjeanroy.springmvc.uadetector.parsers;

import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.parser.UserAgentStringParserImpl;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class CachedUserAgentStringParserTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent userAgent;

	@Test
	public void it_should_build_guava_parser_with_default_parser() throws Exception {
		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser();

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(cachedParser, "parser", true);
		ConcurrentHashMap internalCache = (ConcurrentHashMap) readField(cachedParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isExactlyInstanceOf(UserAgentStringParserImpl.class);

		assertThat(internalCache)
				.isNotNull()
				.isExactlyInstanceOf(ConcurrentHashMap.class);
	}

	@Test
	public void it_should_build_guava_parser_with_custom_parser() throws Exception {
		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser(parser);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(cachedParser, "parser", true);
		ConcurrentHashMap internalCache = (ConcurrentHashMap) readField(cachedParser, "cache", true);

		assertThat(internalParser)
				.isNotNull()
				.isSameAs(parser);

		assertThat(internalCache)
				.isNotNull()
				.isExactlyInstanceOf(ConcurrentHashMap.class);
	}

	@Test
	public void it_should_get_parser_data_version() throws Exception {
		String dv = "foo";
		when(parser.getDataVersion()).thenReturn(dv);

		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser(parser);

		String dataVersion = cachedParser.getDataVersion();

		assertThat(dataVersion)
				.isNotNull()
				.isEqualTo(dv);

		verify(parser).getDataVersion();
	}

	@Test
	public void it_should_shutdown_parser_and_clear_cache() throws Exception {
		String dv = "foo";
		when(parser.getDataVersion()).thenReturn(dv);

		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser(parser);

		ConcurrentHashMap internalCache = (ConcurrentHashMap) readField(cachedParser, "cache", true);
		internalCache.put("foo", "bar");
		assertThat(internalCache).containsKey("foo");

		cachedParser.shutdown();

		verify(parser).shutdown();
		assertThat(internalCache).doesNotContainKey("foo");
	}

	@Test
	public void it_should_get_user_agent_value() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);

		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser(parser);

		ReadableUserAgent result = cachedParser.parse(ua);

		assertThat(result)
				.isNotNull()
				.isSameAs(userAgent);

		verify(parser).parse(ua);
	}

	@Test
	public void it_should_get_user_agent_value_once() throws Exception {
		String ua = "foo";
		when(parser.parse(ua)).thenReturn(userAgent);

		CachedUserAgentStringParser cachedParser = new CachedUserAgentStringParser(parser);

		ReadableUserAgent result1 = cachedParser.parse(ua);
		ReadableUserAgent result2 = cachedParser.parse(ua);

		assertThat(result1)
				.isNotNull()
				.isSameAs(userAgent);

		assertThat(result2)
				.isNotNull()
				.isSameAs(userAgent);

		verify(parser, times(1)).parse(ua);
	}
}
