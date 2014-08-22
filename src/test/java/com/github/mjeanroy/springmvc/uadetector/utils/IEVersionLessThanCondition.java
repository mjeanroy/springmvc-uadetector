package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

public class IEVersionLessThanCondition implements Condition {

	public static IEVersionLessThanCondition ieVersionLessThanCondition(Browser browser, int version) {
		return new IEVersionLessThanCondition(browser, version);
	}

	private Browser browser;

	private int version;

	private IEVersionLessThanCondition(Browser browser, int version) {
		this.browser = browser;
		this.version = version;
	}

	@Override
	public boolean check() {
		switch (version) {
			case 6:
				return browser.isInternetExplorerLessThan6() &&
						browser.isInternetExplorerLessThan7() &&
						browser.isInternetExplorerLessThan8() &&
						browser.isInternetExplorerLessThan9() &&
						browser.isInternetExplorerLessThan10();

			case 7:
				return !browser.isInternetExplorerLessThan6() &&
						browser.isInternetExplorerLessThan7() &&
						browser.isInternetExplorerLessThan8() &&
						browser.isInternetExplorerLessThan9() &&
						browser.isInternetExplorerLessThan10();

			case 8:
				return !browser.isInternetExplorerLessThan6() &&
						!browser.isInternetExplorerLessThan7() &&
						browser.isInternetExplorerLessThan8() &&
						browser.isInternetExplorerLessThan9() &&
						browser.isInternetExplorerLessThan10();

			case 9:
				return !browser.isInternetExplorerLessThan6() &&
						!browser.isInternetExplorerLessThan7() &&
						!browser.isInternetExplorerLessThan8() &&
						browser.isInternetExplorerLessThan9() &&
						browser.isInternetExplorerLessThan10();

			case 10:
				return !browser.isInternetExplorerLessThan6() &&
						!browser.isInternetExplorerLessThan7() &&
						!browser.isInternetExplorerLessThan8() &&
						!browser.isInternetExplorerLessThan9() &&
						browser.isInternetExplorerLessThan10();

			default:
				return false;
		}
	}
}
