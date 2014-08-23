package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

/**
 * Check that given browser is exactly Chromium Browser (no matter
 * which version or which platform).
 * Used for tests only.
 */
public class ChromiumCondition implements Condition {

	/**
	 * Static Factory.
	 *
	 * @param browser Target Browser.
	 * @return Condition class.
	 */
	public static ChromiumCondition chromiumCondition(Browser browser) {
		return new ChromiumCondition(browser);
	}

	/** Browser to check. */
	private final Browser browser;

	/**
	 * Private Constructor.
	 * Use static factory instead.
	 *
	 * @param browser Target Browser.
	 */
	private ChromiumCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isChromium();
	}
}
