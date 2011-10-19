/**
 * 
 */
package net.jawr.web.cache;

import net.jawr.web.config.JawrConfig;
import net.jawr.web.resource.bundle.factory.util.ClassLoaderResourceUtils;

/**
 * @author ibrahim Chaehoi
 *
 */
public class CacheManagerFactory {

	private AbstractCacheManager cacheManager;
	
	public CacheManagerFactory(JawrConfig config) {
	
		cacheManager = (AbstractCacheManager) config.getContext().getAttribute("JAWR.CACHE.MANAGER");
		if(cacheManager == null){
			String cacheManagerClass = config.getProperty("jawr.cache.manager");
			cacheManager = (AbstractCacheManager) ClassLoaderResourceUtils.buildObjectInstance(cacheManagerClass, new Object[]{config});	
		}
	}
	
	public AbstractCacheManager getCacheManager(){
		return cacheManager;
	}
}
