package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class GoogleChromeCondition implements Condition {

	public static GoogleChromeCondition googleChromeCondition(Browser browser) {
		return new GoogleChromeCondition(browser);
	}

	private Browser browser;

	private GoogleChromeCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isGoogleChrome();
	}
}
