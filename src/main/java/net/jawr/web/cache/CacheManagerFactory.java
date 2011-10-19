/**
 * Copyright 2011 Ibrahim Chaehoi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.jawr.web.cache;

import net.jawr.web.config.JawrConfig;
import net.jawr.web.resource.bundle.factory.util.ClassLoaderResourceUtils;

/**
 * The cache manager factory
 * 
 * @author Ibrahim Chaehoi
 */
public class CacheManagerFactory {

	/** The cache manager */
	private AbstractCacheManager cacheManager;
	
	/**
	 * Constructor
	 * 
	 * @param config the jawr config
	 */
	public CacheManagerFactory(JawrConfig config) {
	
		cacheManager = (AbstractCacheManager) config.getContext().getAttribute("JAWR.CACHE.MANAGER");
		if(cacheManager == null){
			String cacheManagerClass = config.getProperty("jawr.cache.manager");
			cacheManager = (AbstractCacheManager) ClassLoaderResourceUtils.buildObjectInstance(cacheManagerClass, new Object[]{config});	
		}
	}
	
	/**
	 * Returns the cache manager
	 * @return
	 */
	public AbstractCacheManager getCacheManager(){
		return cacheManager;
	}
}
