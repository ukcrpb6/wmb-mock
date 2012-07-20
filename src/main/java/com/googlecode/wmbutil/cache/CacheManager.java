package com.googlecode.wmbutil.cache;

import java.math.BigInteger;
import java.util.List;

public interface CacheManager {

    CacheEntry findInCacheByKey(String cacheName, String key);

    List<CacheEntry> loadAllCacheEntries();

    void saveNewCacheEntry(CacheEntry entry);

    CacheEntry findCacheEntryById(BigInteger id);

    void deleteCacheEntry(CacheEntry entry);

}
