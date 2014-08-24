package com.github.mjeanroy.springmvc.uadetector.resolvers;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;
import com.github.mjeanroy.springmvc.uadetector.tools.UADetectorBrowser;
import net.sf.uadetector.ReadableUserAgent;
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

@RunWith(MockitoJUnitRunner.class)
public class BrowserResolverTest {

	@Mock
	private MethodParameter methodParameter;

	@Mock
	private NativeWebRequest nativeWebRequest;

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent readableUserAgent;

	@InjectMocks
	private BrowserResolver browserResolver;

	@Test
	public void it_should_not_resolved_arguments_with_unknown_class() throws Exception {
		Class klass = Integer.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		Object argument = browserResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isSameAs(WebArgumentResolver.UNRESOLVED);

		verify(methodParameter).getParameterType();
		verifyZeroInteractions(nativeWebRequest);
	}

	@Test
	public void it_should_resolved_arguments_with_correct_class() throws Exception {
		Class klass = Browser.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		String userAgent = "foo";
		when(nativeWebRequest.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object argument = browserResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isInstanceOf(Browser.class);

		assertThat(((Browser) argument).getUserAgent())
				.isNotNull()
				.isSameAs(readableUserAgent);

		verify(methodParameter).getParameterType();
		verify(nativeWebRequest).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}

	@Test
	public void it_should_resolved_arguments_with_correct_implementation() throws Exception {
		Class klass = UADetectorBrowser.class;
		when(methodParameter.getParameterType()).thenReturn(klass);

		String userAgent = "foo";
		when(nativeWebRequest.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object argument = browserResolver.resolveArgument(methodParameter, nativeWebRequest);

		assertThat(argument)
				.isNotNull()
				.isInstanceOf(Browser.class);

		assertThat(((Browser) argument).getUserAgent())
				.isNotNull()
				.isSameAs(readableUserAgent);

		verify(methodParameter).getParameterType();
		verify(nativeWebRequest).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}
}
