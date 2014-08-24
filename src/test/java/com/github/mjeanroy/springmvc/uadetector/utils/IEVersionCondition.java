package com.github.mjeanroy.springmvc.uadetector.utils;

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
