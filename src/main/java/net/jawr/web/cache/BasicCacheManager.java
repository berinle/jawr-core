/**
 * 
 */
package net.jawr.web.cache;

import net.jawr.web.config.JawrConfig;

/**
 * @author ibrahim Chaehoi
 *
 */
public class BasicCacheManager extends AbstractCacheManager {

	public BasicCacheManager(JawrConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.jawr.web.cache.AbstractCacheManager#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(String key, Object obj) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.jawr.web.cache.AbstractCacheManager#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.jawr.web.cache.AbstractCacheManager#remove(java.lang.String)
	 */
	@Override
	public Object remove(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.jawr.web.cache.AbstractCacheManager#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
