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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileLookupDataSource implements LookupDataSource {

	private CacheRefreshException exceptionToThrow;
	private Map caches = new HashMap();
	
	public FileLookupDataSource(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line = reader.readLine();
		
		while(line != null) {
			String[] tokens = line.split(";");
			
			String cacheName = tokens[0];
			String key = tokens[1];
			String value = tokens[2];
			long ttl = Long.parseLong(tokens[3]);
			long ttd = Long.parseLong(tokens[4]);
			
			LookupRows data = (LookupRows)caches.get(cacheName);
			
			if(data == null) {
				data = new LookupRows(cacheName, ttl, ttd, new HashMap());
				caches.put(cacheName, data);
			}
			
			data.getRows().put(key, value);
			
			line = reader.readLine();
		}
		
	}
	
	// TODO returns null if cache is not known, should throw?
	public LookupRows loadComponentData(String cacheName) throws CacheRefreshException {
		throwIfSet();
		
		return (LookupRows)caches.get(cacheName);
	}


	private void throwIfSet() throws CacheRefreshException {
		if(exceptionToThrow != null) {
			throw exceptionToThrow;
		}
	}
	
	public CacheRefreshException getExceptionToThrow() {
		return exceptionToThrow;
	}

	public void setExceptionToThrow(CacheRefreshException exceptionToThrow) {
		this.exceptionToThrow = exceptionToThrow;
	}
}
