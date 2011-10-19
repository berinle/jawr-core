package net.jawr.web.cache;

import net.jawr.web.config.JawrConfig;

public abstract class AbstractCacheManager {

	/**
	 * Constructor
	 * @param config the config
	 */
	public AbstractCacheManager(JawrConfig config) {
		
	}
	
	public abstract void put(String key, Object obj);
	
	public abstract Object get(String key);
	
	public abstract Object remove(String key);
	
	public abstract void clear();
	
}
