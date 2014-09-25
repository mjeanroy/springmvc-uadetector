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
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

@RunWith(MockitoJUnitRunner.class)
public class EhCacheEntryFactoryTest {

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent userAgent;

	@Test
	public void it_should_create_entry_factory() throws Exception {
		EhCacheEntryFactory factory = new EhCacheEntryFactory(parser);

		UserAgentStringParser internalParser = (UserAgentStringParser) readField(factory, "parser", true);
		assertThat(internalParser).isNotNull().isSameAs(parser);
	}

	@Test
	public void it_should_parse_user_agent() throws Exception {
		when(parser.parse("foo")).thenReturn(userAgent);
		EhCacheEntryFactory factory = new EhCacheEntryFactory(parser);

		ReadableUserAgent r = (ReadableUserAgent) factory.createEntry("foo");

		assertThat(r).isNotNull().isSameAs(userAgent);
	}
}
