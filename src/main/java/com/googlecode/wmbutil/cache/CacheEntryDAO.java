package com.googlecode.wmbutil.cache;

import java.math.BigInteger;

public interface CacheEntryDAO extends GenericDAO<CacheEntry, BigInteger> {
    CacheEntry findInCacheByKey(String cacheName, String key);
}
