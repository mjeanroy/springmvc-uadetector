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

package com.github.mjeanroy.springmvc.uadetector.resolvers;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentStringParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ReadableUserAgentResolverTest {

	@Mock
	private MethodParameter methodParameter;

	@Mock
	private NativeWebRequest nativeWebRequest;

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent readableUserAgent;

	@InjectMocks
	private ReadableUserAgentResolver readableUserAgentResolver;

	@Test
	public void it_should_not_resolved_arguments_with_unknown_class() throws Exception {
		Class klass = Integer.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		Object argument = readableUserAgentResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isSameAs(WebArgumentResolver.UNRESOLVED);

		verify(methodParameter).getParameterType();
		verifyZeroInteractions(nativeWebRequest);
	}

	@Test
	public void it_should_resolved_arguments_with_correct_class() throws Exception {
		Class klass = ReadableUserAgent.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		String userAgent = "foo";
		when(nativeWebRequest.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object argument = readableUserAgentResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isSameAs(readableUserAgent);

		verify(methodParameter).getParameterType();
		verify(nativeWebRequest).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}

	@Test
	public void it_should_resolved_arguments_with_correct_implementation() throws Exception {
		Class klass = UserAgent.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		String userAgent = "foo";
		when(nativeWebRequest.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object argument = readableUserAgentResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isSameAs(readableUserAgent);

		verify(methodParameter).getParameterType();
		verify(nativeWebRequest).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}
}
