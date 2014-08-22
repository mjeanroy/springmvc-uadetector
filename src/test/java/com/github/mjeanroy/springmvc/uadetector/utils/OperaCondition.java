package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class OperaCondition implements Condition {

	public static OperaCondition operaCondition(Browser browser) {
		return new OperaCondition(browser);
	}

	private Browser browser;

	private OperaCondition(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean check() {
		return browser.isOpera();
	}
}
