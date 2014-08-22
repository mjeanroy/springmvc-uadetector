package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class FirefoxCondition implements Condition {

	public static FirefoxCondition firefoxCondition(Browser browser) {
		return new FirefoxCondition(browser);
	}

	private Browser browser;

	private FirefoxCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isFirefox();
	}
}
