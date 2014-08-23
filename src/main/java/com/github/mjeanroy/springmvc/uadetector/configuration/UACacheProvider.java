package com.github.mjeanroy.springmvc.uadetector.configuration;

import com.github.mjeanroy.springmvc.uadetector.commons.ClassUtils;
import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.DefaultCacheParserConfiguration;
import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.GuavaCacheParserConfiguration;
import com.github.mjeanroy.springmvc.uadetector.configuration.parsers.NoCacheParserConfiguration;

/**
 * Cache strategies.
 */
public enum UACacheProvider {

	/** Do not use any cache. */
	NONE {
		@Override
		public Class getConfigurationClass() {
			return NoCacheParserConfiguration.class;
		}
	},

	/** Use default cache, using concurrent map. */
	DEFAULT {
		@Override
		public Class getConfigurationClass() {
			return DefaultCacheParserConfiguration.class;
		}
	},

	/** Use Guava Cache implementation. */
	GUAVA {
		@Override
		public Class getConfigurationClass() {
			return GuavaCacheParserConfiguration.class;
		}
	},

	/**
	 * Auto Detect cache implementation:
	 * - If Guava is on classpath, use guava cache loader.
	 * - Otherwise use default cache implementation.
	 */
	AUTO {
		@Override
		public Class getConfigurationClass() {
			if (ClassUtils.isPresent("com.google.common.cache.CacheBuilder")) {
				return GuavaCacheParserConfiguration.class;
			} else {
				return DefaultCacheParserConfiguration.class;
			}
		}
	};

	/**
	 * Return configuration class to use associated with this cache
	 * strategy.
	 *
	 * @return Configuration class.
	 */
	public abstract Class getConfigurationClass();
}
