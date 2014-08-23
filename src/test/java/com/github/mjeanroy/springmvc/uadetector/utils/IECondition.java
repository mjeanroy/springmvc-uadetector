package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

/**
 * Check that given browser is exactly IE Browser (no matter
 * which version or which platform).
 * Used for tests only.
 */
public class IECondition implements Condition {

	/**
	 * Static Factory.
	 *
	 * @param browser Target Browser.
	 * @return Condition class.
	 */
	public static IECondition IECondition(Browser browser) {
		return new IECondition(browser);
	}

	/** Browser to check. */
	private final Browser browser;

	/**
	 * Private Constructor.
	 * Use static factory instead.
	 *
	 * @param browser Target Browser.
	 */
	private IECondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isIE();
	}
}
