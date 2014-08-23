package com.github.mjeanroy.springmvc.uadetector.utils;

import com.github.mjeanroy.springmvc.uadetector.tools.Browser;

/**
 * Class that check that given browser is IE in specific version and
 * not other supported version.
 * Used for tests only.
 */
public class IEVersionCondition implements Condition {

	/**
	 * Static factory.
	 *
	 * @param browser Target Browser.
	 * @param version Target version.
	 * @return Condition class.
	 */
	public static IEVersionCondition ieVersionCondition(Browser browser, int version) {
		return new IEVersionCondition(browser, version);
	}

	/** Browser to check. */
	private final Browser browser;

	/** IE Version to check. */
	private final int version;

	/**
	 * Private constructor, use factory instead.
	 *
	 * @param browser Target Browser.
	 * @param version Target version.
	 */
	private IEVersionCondition(Browser browser, int version) {
		this.browser = browser;
		this.version = version;
	}

	@Override
	public boolean check() {
		return isSupportedVersion() && checkIE(version);
	}

	private boolean isSupportedVersion() {
		return version >= 6 && version <= 11;
	}

	private boolean checkIE(int version) {
		return browser.isIE6() == (version == 6)
				&& browser.isIE7() == (version == 7)
				&& browser.isIE8() == (version == 8)
				&& browser.isIE9() == (version == 9)
				&& browser.isIE10() == (version == 10)
				&& browser.isIE11() == (version == 11);
	}
}
