package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

/**
 * Check that given browser is exactly Google Chrome Browser (no matter
 * which version or which platform).
 * Used for tests only.
 */
public class GoogleChromeCondition implements Condition {

	/**
	 * Static Factory.
	 *
	 * @param browser Target Browser.
	 * @return Condition class.
	 */
	public static GoogleChromeCondition googleChromeCondition(Browser browser) {
		return new GoogleChromeCondition(browser);
	}

	/** Browser to check. */
	private final Browser browser;

	/**
	 * Private Constructor.
	 * Use static factory instead.
	 *
	 * @param browser Target Browser.
	 */
	private GoogleChromeCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isGoogleChrome();
	}
}
