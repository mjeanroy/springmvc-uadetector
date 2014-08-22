package com.github.mjeanroy.springmvc.uadetector.tools;

import static com.github.mjeanroy.springmvc.uadetector.utils.ChromiumCondition.chromiumCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.FirefoxCondition.firefoxCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.GoogleChromeCondition.googleChromeCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.IEVersionCondition.ieVersionCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.IEVersionLessThanCondition.ieVersionLessThanCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.InternetExplorerCondition.internetExplorerCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.OperaCondition.operaCondition;
import static com.github.mjeanroy.springmvc.uadetector.utils.SafariCondition.safariCondition;
import static java.util.Arrays.asList;
import static net.sf.uadetector.ReadableDeviceCategory.Category;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.mjeanroy.springmvc.uadetector.utils.Condition;
import net.sf.uadetector.DeviceCategory;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.VersionNumber;

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
	public void it_should_if_browser_is_internet_explorer() {
		checkInternetExplorer(UserAgentFamily.IE);
		checkInternetExplorer(UserAgentFamily.IE_MOBILE);
	}

	@Test
	public void it_should_check_if_browser_is_firefox() {
		checkFirefox(UserAgentFamily.FIREFOX);
		checkFirefox(UserAgentFamily.MOBILE_FIREFOX);
	}

	@Test
	public void it_should_check_if_browser_is_opera() {
		checkOpera(UserAgentFamily.OPERA);
		checkOpera(UserAgentFamily.OPERA_MINI);
		checkOpera(UserAgentFamily.OPERA_MOBILE);
	}

	@Test
	public void it_should_check_if_browser_is_google_chrome() {
		checkGoogleChrome(UserAgentFamily.CHROME);
		checkGoogleChrome(UserAgentFamily.CHROME_MOBILE);
	}

	@Test
	public void it_should_check_if_browser_is_chromium() {
		checkChromium(UserAgentFamily.CHROMIUM);
	}

	@Test
	public void it_should_check_if_browser_is_safari() {
		checkSafari(UserAgentFamily.SAFARI);
		checkSafari(UserAgentFamily.MOBILE_SAFARI);
	}

	@Test
	public void it_should_check_if_browser_is_ie_with_specific_version() {
		checkInternetExplorerVersion(6);
		checkInternetExplorerVersion(7);
		checkInternetExplorerVersion(8);
		checkInternetExplorerVersion(9);
		checkInternetExplorerVersion(10);
		checkInternetExplorerVersion(11);
	}

	@Test
	public void it_should_check_if_browser_is_ie_with_specific_version_less_than() {
		checkInternetExplorerVersionLessThan(6);
		checkInternetExplorerVersionLessThan(7);
		checkInternetExplorerVersionLessThan(8);
		checkInternetExplorerVersionLessThan(9);
		checkInternetExplorerVersionLessThan(10);
	}

	private void checkInternetExplorer(UserAgentFamily family) {
		check(family, internetExplorerCondition(browser1));
	}

	private void checkFirefox(UserAgentFamily family) {
		check(family, firefoxCondition(browser1));
	}

	private void checkOpera(UserAgentFamily family) {
		check(family, operaCondition(browser1));
	}

	private void checkGoogleChrome(UserAgentFamily family) {
		check(family, googleChromeCondition(browser1));
	}

	private void checkChromium(UserAgentFamily family) {
		check(family, chromiumCondition(browser1));
	}

	private void checkSafari(UserAgentFamily family) {
		check(family, safariCondition(browser1));
	}

	private void checkInternetExplorerVersion(int version) {
		VersionNumber versionNumber = new VersionNumber(asList(String.valueOf(version)));
		when(userAgent1.getFamily()).thenReturn(UserAgentFamily.IE);
		when(userAgent1.getVersionNumber()).thenReturn(versionNumber);

		Condition function = ieVersionCondition(browser1, version);
		boolean isValid = function.check();
		assertThat(isValid).isTrue();
	}

	private void checkInternetExplorerVersionLessThan(int version) {
		VersionNumber versionNumber = new VersionNumber(asList(String.valueOf(version)));
		when(userAgent1.getFamily()).thenReturn(UserAgentFamily.IE);
		when(userAgent1.getVersionNumber()).thenReturn(versionNumber);

		Condition function = ieVersionLessThanCondition(browser1, version);
		boolean isValid = function.check();
		assertThat(isValid).isTrue();
	}

	private void check(UserAgentFamily family, Condition function) {
		when(userAgent1.getFamily()).thenReturn(family);
		boolean isValid = function.check();
		assertThat(isValid).isTrue();
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
