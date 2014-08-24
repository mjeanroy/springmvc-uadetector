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

package com.github.mjeanroy.springmvc.uadetector.parsers;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.github.mjeanroy.springmvc.uadetector.commons.LaunderThrowable.launderThrowable;

/**
 * Simple parser using a simple cache.
 * Cache implementation is freely inspired from awesome Brian Goetz Memoizer implementation
 * published in "Java Concurrent In Practice".
 */
public class CachedUserAgentStringParser implements UserAgentStringParser {

	/** Parser implementation. */
	private final UserAgentStringParser parser;

	/** Cache of computed results. */
	private final ConcurrentMap<String, FutureTask<ReadableUserAgent>> cache;

	/**
	 * Build cached parser using default internal parser.
	 *
	 * Default internal parser is retrieved using {@link net.sf.uadetector.service.UADetectorServiceFactory#getResourceModuleParser()} method.
	 */
	public CachedUserAgentStringParser() {
		this.parser = UADetectorServiceFactory.getResourceModuleParser();
		this.cache = new ConcurrentHashMap<String, FutureTask<ReadableUserAgent>>();
	}

	/**
	 * Build cached parser using custom internal parser.
	 *
	 * @param parser Internal parser.
	 */
	public CachedUserAgentStringParser(UserAgentStringParser parser) {
		this.parser = parser;
		this.cache = new ConcurrentHashMap<String, FutureTask<ReadableUserAgent>>();
	}

	@Override
	public String getDataVersion() {
		return parser.getDataVersion();
	}

	@Override
	public ReadableUserAgent parse(final String userAgent) {
		// Use while true to retry parsing in case of CancellationException
		while (true) {
			FutureTask<ReadableUserAgent> task = cache.get(userAgent);
			if (task == null) {
				Callable<ReadableUserAgent> callable = new Callable<ReadableUserAgent>() {
					@Override
					public ReadableUserAgent call() throws Exception {
						return parser.parse(userAgent);
					}
				};

				FutureTask<ReadableUserAgent> newTask = new FutureTask<ReadableUserAgent>(callable);
				task = cache.putIfAbsent(userAgent, newTask);
				if (task == null) {
					task = newTask;
					newTask.run();
				}
			}

			try {
				return task.get();
			}
			catch (CancellationException e) {
				cache.remove(userAgent, task);
				// Do not return anything and retry
			}
			catch (ExecutionException ex) {
				throw launderThrowable(ex);
			}
			catch (InterruptedException ex) {
				throw launderThrowable(ex);
			}
		}
	}

	@Override
	public void shutdown() {
		cache.clear();
		parser.shutdown();
	}
}
