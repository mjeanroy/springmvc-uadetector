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

package com.github.mjeanroy.springmvc.uadetector.factories;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadableUserAgentFactoryBeanTest {

	@Mock
	private HttpServletRequest request;

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent readableUserAgent;

	@InjectMocks
	private ReadableUserAgentFactoryBean readableUserAgentFactoryBean;

	@Test
	public void it_should_parse_user_agent() throws Exception {
		String userAgent = "foo";
		when(request.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object object = readableUserAgentFactoryBean.getObject();

		assertThat(object)
				.isNotNull()
				.isInstanceOf(ReadableUserAgent.class)
				.isSameAs(readableUserAgent);

		verify(request).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}

	@Test
	public void it_should_not_be_a_singleton() {
		assertThat(readableUserAgentFactoryBean.isSingleton())
				.isFalse();
	}

	@Test
	public void it_should_return_correct_object_type() {
		assertThat(readableUserAgentFactoryBean.getObjectType())
				.isEqualTo(ReadableUserAgent.class);
	}
}
