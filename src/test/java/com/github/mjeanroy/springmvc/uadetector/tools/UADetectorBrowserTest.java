package com.github.mjeanroy.springmvc.uadetector.tools;

import com.github.mjeanroy.springmvc.uadetector.utils.Condition;
import net.sf.uadetector.DeviceCategory;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.VersionNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.mjeanroy.springmvc.uadetector.utils.ChromiumCondition.chromiumCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.FirefoxCondition.firefoxCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.GoogleChromeCondition.googleChromeCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.IECondition.IECondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.IEVersionCondition.ieVersionCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.IEVersionLessThanCondition.ieVersionLessThanCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.OperaCondition.operaCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.SafariCondition.safariCondition;
import static java.util.Arrays.asList;
import static net.sf.uadetector.ReadableDeviceCategory.Category;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UADetectorBrowserTest {

	@Mock
	private ReadableUserAgent userAgent1;

	@Mock
	private ReadableUserAgent userAgent2;

	private UADetectorBrowser browser1;

	private UADetectorBrowser browser2;

	@Before
	public void setUp() {
		browser1 = new UADetectorBrowser(userAgent1);
		browser2 = new UADetectorBrowser(userAgent2);
	}

	@Test
	public void it_should_return_browser_category() {
		Category category = Category.SMARTPHONE;
		DeviceCategory deviceCategory = newDeviceCategory(category);

		when(userAgent1.getDeviceCategory()).thenReturn(deviceCategory);

		Category result = browser1.getCategory();

		assertThat(result)
				.isNotNull()
				.isEqualTo(category);
	}

	@Test
	public void it_should_check_if_browser_is_desktop() {
		Category categoryPc = Category.PERSONAL_COMPUTER;
		Category categorySmartphone = Category.SMARTPHONE;

		DeviceCategory pc = newDeviceCategory(categoryPc);
		DeviceCategory smartphone = newDeviceCategory(categorySmartphone);

		when(userAgent1.getDeviceCategory()).thenReturn(pc);
		when(userAgent2.getDeviceCategory()).thenReturn(smartphone);

		boolean isDesktop1 = browser1.isDesktop();
		boolean isDesktop2 = browser2.isDesktop();

		assertThat(isDesktop1).isTrue();
		assertThat(isDesktop2).isFalse();
	}

	@Test
	public void it_should_check_if_browser_is_smartphone() {
		Category categoryPc = Category.PERSONAL_COMPUTER;
		Category categorySmartphone = Category.SMARTPHONE;

		DeviceCategory pc = newDeviceCategory(categoryPc);
		DeviceCategory smartphone = newDeviceCategory(categorySmartphone);

		when(userAgent1.getDeviceCategory()).thenReturn(smartphone);
		when(userAgent2.getDeviceCategory()).thenReturn(pc);

		boolean isSmartphone1 = browser1.isSmartphone();
		boolean isSmartphone2 = browser2.isSmartphone();

		assertThat(isSmartphone1).isTrue();
		assertThat(isSmartphone2).isFalse();
	}

	@Test
	public void it_should_check_if_browser_is_tablet() {
		Category categoryTablet = Category.TABLET;
		Category categorySmartphone = Category.SMARTPHONE;

		DeviceCategory tablet = newDeviceCategory(categoryTablet);
		DeviceCategory smartphone = newDeviceCategory(categorySmartphone);

		when(userAgent1.getDeviceCategory()).thenReturn(tablet);
		when(userAgent2.getDeviceCategory()).thenReturn(smartphone);

		boolean isTablet1 = browser1.isTablet();
		boolean isTablet2 = browser2.isTablet();

		assertThat(isTablet1).isTrue();
		assertThat(isTablet2).isFalse();
	}

	@Test
	public void it_should_check_if_browser_is_IE() {
		Condition condition = IECondition(browser1);

		checkFamily(UserAgentFamily.IE, condition);
		checkFamily(UserAgentFamily.IE_MOBILE, condition);
	}

	@Test
	public void it_should_check_if_browser_is_firefox() {
		Condition condition = firefoxCondition(browser1);

		checkFamily(UserAgentFamily.FIREFOX, condition);
		checkFamily(UserAgentFamily.MOBILE_FIREFOX, condition);
	}

	@Test
	public void it_should_check_if_browser_is_opera() {
		Condition condition = operaCondition(browser1);

		checkFamily(UserAgentFamily.OPERA, condition);
		checkFamily(UserAgentFamily.OPERA_MINI, condition);
		checkFamily(UserAgentFamily.OPERA_MOBILE, condition);
	}

	@Test
	public void it_should_check_if_browser_is_google_chrome() {
		Condition condition = googleChromeCondition(browser1);

		checkFamily(UserAgentFamily.CHROME, condition);
		checkFamily(UserAgentFamily.CHROME_MOBILE, condition);
	}

	@Test
	public void it_should_check_if_browser_is_chromium() {
		Condition condition = chromiumCondition(browser1);

		checkFamily(UserAgentFamily.CHROMIUM, condition);
	}

	@Test
	public void it_should_check_if_browser_is_safari() {
		Condition condition = safariCondition(browser1);

		checkFamily(UserAgentFamily.SAFARI, condition);
		checkFamily(UserAgentFamily.MOBILE_SAFARI, condition);
	}

	@Test
	public void it_should_check_if_browser_is_ie_with_specific_version() {
		checkIEVersion(6);
		checkIEVersion(7);
		checkIEVersion(8);
		checkIEVersion(9);
		checkIEVersion(10);
		checkIEVersion(11);
	}

	@Test
	public void it_should_check_if_browser_is_ie_with_specific_version_less_than() {
		checkIEVersionLessThan(6);
		checkIEVersionLessThan(7);
		checkIEVersionLessThan(8);
		checkIEVersionLessThan(9);
		checkIEVersionLessThan(10);
	}

	private void checkIEVersion(int version) {
		VersionNumber versionNumber = new VersionNumber(asList(String.valueOf(version)));
		when(userAgent1.getFamily()).thenReturn(UserAgentFamily.IE);
		when(userAgent1.getVersionNumber()).thenReturn(versionNumber);

		Condition function = ieVersionCondition(browser1, version);
		assertThat(function.check())
				.overridingErrorMessage("Expect browser to be IE %s", version)
				.isTrue();
	}

	private void checkIEVersionLessThan(int version) {
		VersionNumber versionNumber = new VersionNumber(asList(String.valueOf(version)));
		when(userAgent1.getFamily()).thenReturn(UserAgentFamily.IE);
		when(userAgent1.getVersionNumber()).thenReturn(versionNumber);

		Condition function = ieVersionLessThanCondition(browser1, version);
		assertThat(function.check())
				.overridingErrorMessage("Expect browser to be IE <= %s", version)
				.isTrue();
	}

	private void checkFamily(UserAgentFamily family, Condition function) {
		when(userAgent1.getFamily()).thenReturn(family);
		assertThat(function.check())
				.overridingErrorMessage("Browser expected to be %s", family)
				.isTrue();
	}

	private DeviceCategory newDeviceCategory(Category category) {
		return new DeviceCategory.Builder()
				.setCategory(category)
				.setIcon("foo")
				.setInfoUrl("foo")
				.setName("foo")
				.build();
	}
}
