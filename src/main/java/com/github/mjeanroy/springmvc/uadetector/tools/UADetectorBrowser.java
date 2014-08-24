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

package com.github.mjeanroy.springmvc.uadetector.tools;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentFamily;

import static java.lang.Integer.parseInt;
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

/**
 * Default implementation for browser.
 * Since {@link net.sf.uadetector.ReadableUserAgent} is thread safe,
 * this implementation is thread safe and ensure {@link com.github.mjeanroy.springmvc.uadetector.tools.Browser}
 * contract.
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
	public String getName() {
		return userAgent.getName();
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
	public boolean isIE() {
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
	public boolean isIE6() {
		return checkIE(6);
	}

	@Override
	public boolean isIE7() {
		return checkIE(7);
	}

	@Override
	public boolean isIE8() {
		return checkIE(8);
	}

	@Override
	public boolean isIE9() {
		return checkIE(9);
	}

	@Override
	public boolean isIE10() {
		return checkIE(10);
	}

	@Override
	public boolean isIE11() {
		return checkIE(11);
	}

	@Override
	public boolean isIELessThan6() {
		return checkIELessThan(6);
	}

	@Override
	public boolean isIELessThan7() {
		return checkIELessThan(7);
	}

	@Override
	public boolean isIELessThan8() {
		return checkIELessThan(8);
	}

	@Override
	public boolean isIELessThan9() {
		return checkIELessThan(9);
	}

	@Override
	public boolean isIELessThan10() {
		return checkIELessThan(10);
	}

	@Override
	public boolean checkIE(int version) {
		return isIE() && getMajorVersionAsNumber() == version;
	}

	@Override
	public boolean checkIELessThan(int version) {
		return isIE() && getMajorVersionAsNumber() <= version;
	}

	private int getMajorVersionAsNumber() {
		String majorVersion = getMajorVersion();
		return parseInt(majorVersion);
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
