package com.github.mjeanroy.springmvc.uadetector.parsers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

@RunWith(MockitoJUnitRunner.class)
public class GuavaReadableUserAgentCacheLoaderTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent userAgent;

	@Test
	public void it_should_load_user_agent_from_parser() throws Exception {
		String userAgentKey = "foo";
		when(parser.parse(userAgentKey)).thenReturn(userAgent);

		GuavaReadableUserAgentCacheLoader cacheLoader = new GuavaReadableUserAgentCacheLoader(parser);
		ReadableUserAgent result = cacheLoader.load(userAgentKey);

		assertThat(result)
				.isNotNull()
				.isEqualTo(userAgent);

		verify(parser).parse(userAgentKey);
	}
}
