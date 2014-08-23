package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

/**
 * Check that given browser is exactly Firefox Browser (no matter
 * which version or which platform).
 * Used for tests only.
 */
public class FirefoxCondition implements Condition {

	/**
	 * Static Factory.
	 *
	 * @param browser Target Browser.
	 * @return Condition class.
	 */
	public static FirefoxCondition firefoxCondition(Browser browser) {
		return new FirefoxCondition(browser);
	}

	/** Browser to check. */
	private final Browser browser;

	/**
	 * Private Constructor.
	 * Use static factory instead.
	 *
	 * @param browser Target Browser.
	 */
	private FirefoxCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isFirefox();
	}
}
