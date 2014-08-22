package com.github.mjeanroy.springmvc.uadetector.parsers;

import static com.github.mjeanroy.springmvc.uadetector.commons.LaunderThrowable.launderThrowable;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

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
		this.cache = new ConcurrentHashMap<>();
	}

	/**
	 * Build cached parser using custom internal parser.
	 *
	 * @param parser Internal parser.
	 */
	public CachedUserAgentStringParser(UserAgentStringParser parser) {
		this.parser = parser;
		this.cache = new ConcurrentHashMap<>();
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

				FutureTask<ReadableUserAgent> newTask = new FutureTask<>(callable);
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
			catch (ExecutionException | InterruptedException ex) {
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
