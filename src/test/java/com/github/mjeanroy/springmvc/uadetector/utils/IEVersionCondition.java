package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class IEVersionCondition implements Condition {

	public static IEVersionCondition ieVersionCondition(Browser browser, int version) {
		return new IEVersionCondition(browser, version);
	}

	private Browser browser;

	private int version;

	private IEVersionCondition(Browser browser, int version) {
		this.browser = browser;
		this.version = version;
	}

	@Override
	public boolean check() {
		switch (version) {
			case 6:
				return browser.isInternetExplorer6();
			case 7:
				return browser.isInternetExplorer7();
			case 8:
				return browser.isInternetExplorer8();
			case 9:
				return browser.isInternetExplorer9();
			case 10:
				return browser.isInternetExplorer10();
			case 11:
				return browser.isInternetExplorer11();
			default:
				return false;
		}
	}
}
