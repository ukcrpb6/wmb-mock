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

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class LookupTest extends TestCase {

	
	
	public void test() throws LookupCacheException, IOException {
		FileLookupDataSource ds = new FileLookupDataSource(new File("src/test/java/test-lookup-data.txt"));
		
		Lookup lookup = new Lookup(ds, "component1");
		
		assertEquals("value1", lookup.lookupValue("key1"));
		assertEquals("value2", lookup.lookupValue("key2"));
		assertEquals("value3", lookup.lookupValue("key3"));
		assertNull(lookup.lookupValue("key4"));

		Lookup lookup2 = new Lookup(ds, "component2");
		assertNull(lookup2.lookupValue("key3"));
		assertEquals("value4", lookup2.lookupValue("key1"));
		assertEquals("value5", lookup2.lookupValue("key5"));
	}
	
	public void testInitialLoadFail() throws LookupCacheException, IOException {
		FileLookupDataSource ds = new FileLookupDataSource(new File("src/test/java/test-lookup-data.txt"));
		ds.setExceptionToThrow(new CacheRefreshException("mock"));
		
		try  {
			// must not be able to load cache
			new Lookup(ds,  "component56");
		} catch(CacheRefreshException e) {
			// ok!
		}
	}
	
	public void testServeFromAliveCache() throws LookupCacheException, IOException, InterruptedException {
		FileLookupDataSource ds = new FileLookupDataSource(new File("src/test/java/test-lookup-data.txt"));

		Lookup lookup = new Lookup(ds, "component1");
		
		assertEquals("value1", lookup.lookupValue("key1"));

		ds.setExceptionToThrow(new CacheRefreshException("mock"));
		
		// sleep so that TTL is passed
		Thread.sleep(1100);
		
		assertEquals("value1", lookup.lookupValue("key1"));

	}

	public void testServeFromDeadCache() throws LookupCacheException, IOException, InterruptedException {
		FileLookupDataSource ds = new FileLookupDataSource(new File("src/test/java/test-lookup-data.txt"));

		Lookup lookup = new Lookup(ds, "component1");
		
		assertEquals("value1", lookup.lookupValue("key1"));

		ds.setExceptionToThrow(new CacheRefreshException("mock"));
		
		// sleep so that TTD is passed
		Thread.sleep(3000);
		
		try {
			lookup.lookupValue("key1");
			fail("Must throw StaleCacheException");
		} catch(StaleCacheException e) {
			// ok
		}

	}
}
