package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class SafariCondition implements Condition {

	public static SafariCondition safariCondition(Browser browser) {
		return new SafariCondition(browser);
	}

	private Browser browser;

	private SafariCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isSafari();
	}
}
