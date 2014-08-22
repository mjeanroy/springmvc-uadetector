package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class InternetExplorerCondition implements Condition {

	public static InternetExplorerCondition internetExplorerCondition(Browser browser) {
		return new InternetExplorerCondition(browser);
	}

	private Browser browser;

	private InternetExplorerCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isInternetExplorer();
	}
}
