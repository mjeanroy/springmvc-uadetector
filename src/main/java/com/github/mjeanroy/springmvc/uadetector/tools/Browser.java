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

import net.sf.uadetector.ReadableDeviceCategory;
import net.sf.uadetector.ReadableUserAgent;

/**
 * Browser specification that can be used to check againt:
 * - Platform: desktop, smartphone, tablet.
 * - Major families: Google Chrome, Firefox, Safari, Opera, Chromium and IE.
 * - Specific versions of IE: since most of work for web developers is to check
 * IE version, shortcuts are made to check if target browser is IE6 / 7 / 8 / 9 / 10.
 * <p/>
 * Implementation must be thread safe.
 */
public interface Browser {

	/**
	 * Get readable user agent.
	 *
	 * @return User Agent.
	 */
	ReadableUserAgent getUserAgent();

	/**
	 * Get browser name.
	 *
	 * @return Browser Name.
	 */
	String getName();

	/**
	 * Check if browser is a desktop browser.
	 *
	 * @return True if browser is a desktop browser.
	 */
	boolean isDesktop();

	/**
	 * Check if browser is a smartphone browser (iphone, android smartphone).
	 *
	 * @return True if browser is a smartphone browser.
	 */
	boolean isSmartphone();

	/**
	 * Check if browser is a tablet browser (ipad, android tablet).
	 *
	 * @return True if browser is a tablet browser.
	 */
	boolean isTablet();

	/**
	 * Return browser category.
	 *
	 * @return Browser category.
	 */
	ReadableDeviceCategory.Category getCategory();

	/**
	 * Check if browser is Internet Explorer (no matter the category or version).
	 *
	 * @return True if browser is a version of Internet Explorer.
	 */
	boolean isIE();

	/**
	 * Check if browser is Google Chrome (no matter the category or version).
	 *
	 * @return True if browser is a version of Google Chrome.
	 */
	boolean isGoogleChrome();

	/**
	 * Check if browser is Chromium (no matter the category or version).
	 *
	 * @return True if browser is a version of Chromium.
	 */
	boolean isChromium();

	/**
	 * Check if browser is Firefox (no matter the category or version).
	 *
	 * @return True if browser is a version of Firefox.
	 */
	boolean isFirefox();

	/**
	 * Check if browser is Opera (no matter the category or version).
	 *
	 * @return True if browser is a version of Opera.
	 */
	boolean isOpera();

	/**
	 * Check if browser is Safari (no matter the category or version).
	 *
	 * @return True if browser is a version of Safari.
	 */
	boolean isSafari();

	/**
	 * Get major version of browser.
	 *
	 * @return Major version.
	 */
	String getMajorVersion();

	/**
	 * Check if browser is IE6.
	 *
	 * @return True if browser is exactly IE6.
	 */
	boolean isIE6();

	/**
	 * Check if browser is IE7.
	 *
	 * @return True if browser is exactly IE7.
	 */
	boolean isIE7();

	/**
	 * Check if browser is IE8.
	 *
	 * @return True if browser is exactly IE8.
	 */
	boolean isIE8();

	/**
	 * Check if browser is IE9.
	 *
	 * @return True if browser is exactly IE9.
	 */
	boolean isIE9();

	/**
	 * Check if browser is IE10.
	 *
	 * @return True if browser is exactly IE10.
	 */
	boolean isIE10();

	/**
	 * Check if browser is IE11.
	 *
	 * @return True if browser is exactly IE11.
	 */
	boolean isIE11();

	/**
	 * Check if browser is IE6 or less.
	 *
	 * @return True if browser is exactly IE6 or less.
	 */
	boolean isIELessThan6();

	/**
	 * Check if browser is IE7 or less.
	 *
	 * @return True if browser is exactly IE7 or less.
	 */
	boolean isIELessThan7();

	/**
	 * Check if browser is IE8 or less.
	 *
	 * @return True if browser is exactly IE8 or less.
	 */
	boolean isIELessThan8();

	/**
	 * Check if browser is IE9 or less.
	 *
	 * @return True if browser is exactly IE9 or less.
	 */
	boolean isIELessThan9();

	/**
	 * Check if browser is IE10 or less.
	 *
	 * @return True if browser is exactly IE10 or less.
	 */
	boolean isIELessThan10();

	/**
	 * Check if browser is IE in given major version.
	 *
	 * @param version Major version to check.
	 * @return True if browser is exactly IE in given version.
	 */
	boolean checkIE(int version);

	/**
	 * Check if browser is IE in exactly given major version or less.
	 *
	 * @param version Major version to check.
	 * @return True if browser is exactly IE in given version or less.
	 */
	boolean checkIELessThan(int version);
}
