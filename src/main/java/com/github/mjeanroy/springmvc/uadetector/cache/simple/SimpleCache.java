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

package com.github.mjeanroy.springmvc.uadetector.cache.simple;

import static com.github.mjeanroy.springmvc.uadetector.commons.LaunderThrowable.launderThrowable;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.github.mjeanroy.springmvc.uadetector.cache.AbstractUADetectorCache;
import com.github.mjeanroy.springmvc.uadetector.cache.UADetectorCache;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;

/**
 * Simple cache using instance of {@link ConcurrentHashMap}.
 * Cache implementation is freely inspired from awesome Brian Goetz Memoizer implementation
 * published in "Java Concurrent In Practice".
 */
public class SimpleCache extends AbstractUADetectorCache implements UADetectorCache {

	/** Cache of computed results. */
	private final ConcurrentMap<String, Future<ReadableUserAgent>> cache;

	/**
	 * Build cached parser using custom internal parser.
	 *
	 * @param parser Internal parser.
	 */
	public SimpleCache(UserAgentStringParser parser) {
		super(parser);
		this.cache = new ConcurrentHashMap<String, Future<ReadableUserAgent>>();
	}

	@Override
	public ReadableUserAgent get(final String userAgent) {
		// Use while true to retry parsing in case of CancellationException
		boolean interrupted = false;
		ReadableUserAgent ua = null;

		while (ua == null) {
			Future<ReadableUserAgent> task = cache.get(userAgent);
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
				ua = task.get();
			}
			catch (CancellationException e) {
				cache.remove(userAgent, task);
				// Do not return anything and retry
			}
			catch (InterruptedException ex) {
				cache.remove(userAgent, task);
				interrupted = true;
				// Do not return anything and retry
			}
			catch (ExecutionException ex) {
				throw launderThrowable(ex.getCause());
			}
		}

		if (interrupted) {
			// Restore interrupt status
			Thread.currentThread().interrupt();
		}

		return ua;
	}

	@Override
	public void shutdown() {
		cache.clear();
	}
}
