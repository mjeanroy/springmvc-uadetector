package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class ChromiumCondition implements Condition {

	public static ChromiumCondition chromiumCondition(Browser browser) {
		return new ChromiumCondition(browser);
	}

	private Browser browser;

	private ChromiumCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isChromium();
	}
}
