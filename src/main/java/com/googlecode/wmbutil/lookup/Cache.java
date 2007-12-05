/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmbutil.lookup;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	private static final Map caches = new HashMap();
	
	/**
	 * Gets or creates an existing cache
	 * @param componentName
	 * @return
	 * @throws LookupCacheException 
	 */
	public static Cache getCache(String componentName) throws LookupCacheException {
		synchronized (caches) {
			Cache cache = (Cache) caches.get(componentName);
			if(cache == null) {
				cache = new Cache(componentName);
				caches.put(componentName, cache);
			}
			return cache;
			
		}
	}
	
	private final String componentName;
	
	private Map cachedData;
	private long cacheRefreshTime;
	
	// hide cstr
	private Cache(String componentName) throws LookupCacheException {
		this.componentName = componentName;
	}
	
	private final synchronized void refreshCache(LookupDataSource dataSource) throws LookupCacheException {
		LookupData[] dataArray = dataSource.lookup(componentName);
		
		Map oldCachedData = cachedData;
		
		if(cachedData != null) {
			cachedData.clear();
		} else {
			cachedData = new HashMap();
		}
		
		try {
			for (int i = 0; i < dataArray.length; i++) {
				LookupData data = dataArray[i];
				
				cachedData.put(data.getKey(), data);
			}
			
			cacheRefreshTime = System.currentTimeMillis();
		} catch(Exception e) {
			cachedData = oldCachedData;
			throw new CacheRefreshException("Failed to refresh cache", e);
		}
	}

	private boolean shouldBeCached(LookupData data) {
		return data.getTtd() > 0 && data.getTtl() > 0;
	}
	
	private boolean dataHasPassedTTL(LookupData data) {
		return data.getTtl() +  cacheRefreshTime > System.currentTimeMillis();
	}

	private boolean dataHasPassedTTD(LookupData data) {
		return data.getTtd() +  cacheRefreshTime > System.currentTimeMillis();
	}
	
	// TODO revisit concurrency
	public synchronized String lookupValue(String key, LookupDataSource dataSource) throws LookupCacheException {
		if(cachedData == null) {
			// initial init failed, try refresh
			refreshCache(dataSource);
			
			if(cachedData == null) {
				// failed refresh, throw exception
				throw new StaleCacheException("Failed to init lookup cache");
			}
		}
		
		LookupData data = (LookupData) cachedData.get(key);
		
		if(data == null) {
			// data not found in cache
			return null;
		} else {
			if(dataHasPassedTTL(data)) {
				// old data, try refresh
				try {
					refreshCache(dataSource);
					
					// get new value
					data = (LookupData) cachedData.get(key);
					return data.getValue();
					
				} catch(CacheRefreshException e) {
					// refresh failed, so if we can use old value
					if(dataHasPassedTTD(data)) {
						throw new StaleCacheException("Cache could not be updated and value has died");
					} else {
						// return old, but still alive data
						return data.getValue();
					}
				}
			} else {
				return data.getValue();
			}
		}
	}
}
