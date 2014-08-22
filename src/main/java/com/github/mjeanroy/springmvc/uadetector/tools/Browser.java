package com.github.mjeanroy.springmvc.uadetector.tools;

import net.sf.uadetector.ReadableDeviceCategory;
import net.sf.uadetector.ReadableUserAgent;

public interface Browser {

	ReadableUserAgent getUserAgent();

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
	boolean isInternetExplorer();

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
	boolean isInternetExplorer6();

	/**
	 * Check if browser is IE7.
	 *
	 * @return True if browser is exactly IE7.
	 */
	boolean isInternetExplorer7();

	/**
	 * Check if browser is IE8.
	 *
	 * @return True if browser is exactly IE8.
	 */
	boolean isInternetExplorer8();

	/**
	 * Check if browser is IE9.
	 *
	 * @return True if browser is exactly IE9.
	 */
	boolean isInternetExplorer9();

	/**
	 * Check if browser is IE10.
	 *
	 * @return True if browser is exactly IE10.
	 */
	boolean isInternetExplorer10();

	/**
	 * Check if browser is IE11.
	 *
	 * @return True if browser is exactly IE11.
	 */
	boolean isInternetExplorer11();

	/**
	 * Check if browser is IE6 or less.
	 *
	 * @return True if browser is exactly IE6 or less.
	 */
	boolean isInternetExplorerLessThan6();

	/**
	 * Check if browser is IE7 or less.
	 *
	 * @return True if browser is exactly IE7 or less.
	 */
	boolean isInternetExplorerLessThan7();

	/**
	 * Check if browser is IE8 or less.
	 *
	 * @return True if browser is exactly IE8 or less.
	 */
	boolean isInternetExplorerLessThan8();

	/**
	 * Check if browser is IE9 or less.
	 *
	 * @return True if browser is exactly IE9 or less.
	 */
	boolean isInternetExplorerLessThan9();

	/**
	 * Check if browser is IE10 or less.
	 *
	 * @return True if browser is exactly IE10 or less.
	 */
	boolean isInternetExplorerLessThan10();

	/**
	 * Check if browser is IE in given major version.
	 *
	 * @param version Major version to check.
	 * @return True if browser is exactly IE in given version.
	 */
	boolean checkInternetExplorer(int version);

	/**
	 * Check if browser is IE in exactly given major version or less.
	 *
	 * @param version Major version to check.
	 * @return True if browser is exactly IE in given version or less.
	 */
	boolean checkInternetExplorerLessThan(int version);
}
