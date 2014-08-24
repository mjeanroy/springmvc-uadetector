package com.github.mjeanroy.springmvc.uadetector.factories;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;
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
public class BrowserFactoryBeanTest {

	@Mock
	private HttpServletRequest request;

	@Mock
	private UserAgentStringParser parser;

	@Mock
	private ReadableUserAgent readableUserAgent;

	@InjectMocks
	private BrowserFactoryBean browserFactoryBean;

	@Test
	public void it_should_parse_user_agent() throws Exception {
		String userAgent = "foo";
		when(request.getHeader("User-Agent")).thenReturn(userAgent);
		when(parser.parse(userAgent)).thenReturn(readableUserAgent);

		Object object = browserFactoryBean.getObject();

		assertThat(object)
				.isNotNull()
				.isInstanceOf(Browser.class);

		verify(request).getHeader("User-Agent");
		verify(parser).parse(userAgent);
	}

	@Test
	public void it_should_not_be_a_singleton() {
		assertThat(browserFactoryBean.isSingleton())
				.isFalse();
	}

	@Test
	public void it_should_return_correct_object_type() {
		assertThat(browserFactoryBean.getObjectType())
				.isEqualTo(Browser.class);
	}
}
