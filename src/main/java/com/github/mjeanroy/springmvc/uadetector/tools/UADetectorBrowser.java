package com.github.mjeanroy.springmvc.uadetector.tools;

import static net.sf.uadetector.ReadableDeviceCategory.Category;
import static net.sf.uadetector.UserAgentFamily.CHROME;
import static net.sf.uadetector.UserAgentFamily.CHROME_MOBILE;
import static net.sf.uadetector.UserAgentFamily.CHROMIUM;
import static net.sf.uadetector.UserAgentFamily.FIREFOX;
import static net.sf.uadetector.UserAgentFamily.IE;
import static net.sf.uadetector.UserAgentFamily.IE_MOBILE;
import static net.sf.uadetector.UserAgentFamily.MOBILE_FIREFOX;
import static net.sf.uadetector.UserAgentFamily.MOBILE_SAFARI;
import static net.sf.uadetector.UserAgentFamily.OPERA;
import static net.sf.uadetector.UserAgentFamily.OPERA_MINI;
import static net.sf.uadetector.UserAgentFamily.OPERA_MOBILE;
import static net.sf.uadetector.UserAgentFamily.SAFARI;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentFamily;

/**
 * Default implementation for browser.
 */
public class UADetectorBrowser implements Browser {

	/** User Agent Reader. */
	private final ReadableUserAgent userAgent;

	/**
	 * Build new browser using uadetector user agent reader.
	 *
	 * @param userAgent User Agent.
	 */
	public UADetectorBrowser(ReadableUserAgent userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public ReadableUserAgent getUserAgent() {
		return userAgent;
	}

	@Override
	public boolean isDesktop() {
		return getCategory() == Category.PERSONAL_COMPUTER;
	}

	@Override
	public Category getCategory() {
		return userAgent.getDeviceCategory().getCategory();
	}

	@Override
	public boolean isSmartphone() {
		return getCategory() == Category.SMARTPHONE;
	}

	@Override
	public boolean isTablet() {
		return getCategory() == Category.TABLET;
	}

	@Override
	public boolean isInternetExplorer() {
		UserAgentFamily family = userAgent.getFamily();
		return family == IE
				|| family == IE_MOBILE;
	}

	@Override
	public boolean isGoogleChrome() {
		UserAgentFamily family = userAgent.getFamily();
		return family == CHROME ||
				family == CHROME_MOBILE;
	}

	@Override
	public boolean isChromium() {
		return userAgent.getFamily() == CHROMIUM;
	}

	@Override
	public boolean isFirefox() {
		UserAgentFamily family = userAgent.getFamily();
		return family == FIREFOX
				|| family == MOBILE_FIREFOX;
	}

	@Override
	public boolean isOpera() {
		UserAgentFamily family = userAgent.getFamily();
		return family == OPERA
				|| family == OPERA_MOBILE
				|| family == OPERA_MINI;
	}

	@Override
	public boolean isSafari() {
		UserAgentFamily family = userAgent.getFamily();
		return family == SAFARI
				|| family == MOBILE_SAFARI;
	}

	@Override
	public String getMajorVersion() {
		return userAgent.getVersionNumber().getMajor();
	}

	@Override
	public boolean isInternetExplorer6() {
		return checkInternetExplorer(6);
	}

	@Override
	public boolean isInternetExplorer7() {
		return checkInternetExplorer(7);
	}

	@Override
	public boolean isInternetExplorer8() {
		return checkInternetExplorer(8);
	}

	@Override
	public boolean isInternetExplorer9() {
		return checkInternetExplorer(9);
	}

	@Override
	public boolean isInternetExplorer10() {
		return checkInternetExplorer(10);
	}

	@Override
	public boolean isInternetExplorer11() {
		return checkInternetExplorer(11);
	}

	@Override
	public boolean isInternetExplorerLessThan6() {
		return checkInternetExplorerLessThan(6);
	}

	@Override
	public boolean isInternetExplorerLessThan7() {
		return checkInternetExplorerLessThan(7);
	}

	@Override
	public boolean isInternetExplorerLessThan8() {
		return checkInternetExplorerLessThan(8);
	}

	@Override
	public boolean isInternetExplorerLessThan9() {
		return checkInternetExplorerLessThan(9);
	}

	@Override
	public boolean isInternetExplorerLessThan10() {
		return checkInternetExplorerLessThan(10);
	}

	@Override
	public boolean checkInternetExplorer(int version) {
		return isInternetExplorer() && getMajorVersionAsNumber() == version;
	}

	@Override
	public boolean checkInternetExplorerLessThan(int version) {
		return isInternetExplorer() && getMajorVersionAsNumber() <= version;
	}

	private int getMajorVersionAsNumber() {
		String majorVersion = getMajorVersion();
		return Integer.valueOf(majorVersion);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof UADetectorBrowser) {
			UADetectorBrowser b = (UADetectorBrowser) o;
			return userAgent.equals(b.userAgent);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return userAgent.hashCode();
	}

	@Override
	public String toString() {
		return userAgent.toString();
	}
}
