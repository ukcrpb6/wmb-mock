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

import java.util.Map;
import java.util.WeakHashMap;

public class Cache {

    private static final Map caches = new WeakHashMap();

    /**
     * Gets or creates an existing cache
     * 
     * @param componentName
     * @return
     * @throws LookupCacheException
     */
    public static Cache getCache(String componentName) throws LookupCacheException {
        synchronized (caches) {
            Cache cache = (Cache) caches.get(componentName);
            if (cache == null) {
                cache = new Cache(componentName);
                caches.put(componentName, cache);
            }
            return cache;

        }
    }

    private final String componentName;

    private boolean initialized = false;
    private LookupRows cachedData;
    private long cacheRefreshTime;

    // hide cstr
    private Cache(String componentName) throws LookupCacheException {
        this.componentName = componentName;
    }

    private final synchronized LookupRows refreshCache(LookupDataSource dataSource)
            throws LookupCacheException {
        if (needsRefresh()) {
            LookupRows newData = dataSource.loadComponentData(componentName);

            if (newData.getTTL() > 0 && newData.getTTD() > 0) {
                // okay, we should cache this
                cachedData = newData;
            } else {
                cachedData = null;
            }
            cacheRefreshTime = System.currentTimeMillis();
            initialized = true;

            return newData;
        } else {
            // not null since needsRefresh() checks for null
            return cachedData;
        }
    }

    private boolean isInitialized() {
        return initialized;
    }

    private void checkInitialized() {
        if (!isInitialized()) {
            throw new IllegalStateException("Cache not initialized");
        }
    }

    private boolean needsRefresh() {
        if (!isInitialized()) {
            return true;
        } else if (cachedData == null) {
            return true;
        } else {
            return cachedData.getTTL() + cacheRefreshTime < System.currentTimeMillis();
        }
    }

    private boolean staleDataAvailable() {
        checkInitialized();

        if (cachedData == null) {
            return false;
        } else {
            return cachedData.getTTD() + cacheRefreshTime > System.currentTimeMillis();
        }
    }

    // TODO revisit concurrency
    public synchronized String lookupValue(String key, LookupDataSource dataSource)
            throws LookupCacheException {
        try {
            LookupRows data = refreshCache(dataSource);
            return (String) data.getRows().get(key);
        } catch (LookupCacheException e) {
            // use old cache if we got one
            if (staleDataAvailable()) {
                return (String) cachedData.getRows().get(key);
            } else {
                throw new StaleCacheException("Cache has died", e);
            }
        }
    }

    public void updateValues(Map newValues, LookupDataSource ds) {
        try {
        	this.initialized = false;
        	ds.updateComponentData(this.componentName, newValues);
        } finally {

        }
    }
}
