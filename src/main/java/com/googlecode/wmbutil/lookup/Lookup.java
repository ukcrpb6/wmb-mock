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

/**
 * Provides a simple way of looking up key-value values
 * from typically a database. Values are also cached to 
 * increase performance and reliability.
 */
public class Lookup {

	private LookupDataSource ds;
	private Cache cache;
	
	/**
	 * Create a lookup instance
	 * @param ds The data source to use for loading data
	 * @param componentName The name of the component for which
	 * 		the lookup will be performed
	 * @throws LookupCacheException If the cache can not be loaded or refreshed.
	 */
	public Lookup(LookupDataSource ds, String componentName) throws LookupCacheException {
		this.cache = Cache.getCache(componentName);
		this.ds = ds;
	}

	/**
	 * Create a lookup instance using the default JDBC data source
	 * @param componentName The name of the component for which
	 * 		the lookup will be performed
	 * @throws LookupCacheException If the cache can not be loaded or refreshed.
	 */
	public Lookup(String componentName) throws LookupCacheException {
		this.cache = Cache.getCache(componentName);
		this.ds = new JdbcLookupDataSource();
	}

	/**
	 * Looks up the value for the provided key.
	 * @param key The key to look up
	 * @return The value for the key, or null if the key is not found
	 * @throws LookupCacheException If the cache can not be loaded or refreshed.
	 */
	public String lookupValue(String key) throws LookupCacheException {
		return cache.lookupValue(key, ds);
	}
    
    public void updateValues(Map newValues) throws LookupCacheException {
        cache.updateValues(newValues, ds);
    }
}
