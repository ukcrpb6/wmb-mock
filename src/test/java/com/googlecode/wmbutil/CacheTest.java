package com.googlecode.wmbutil;

import com.googlecode.wmbutil.cache.*;
import com.googlecode.wmbutil.cache.dao.CacheEntry;
import com.googlecode.wmbutil.dao.HibernateUtil;
import junit.framework.Assert;
import org.junit.Test;

public class CacheTest {

    private static final String CACHE_A = "a";
    private static final String CACHE_B = "b";
    private static final String ENTRY_1_KEY = "simple";

    @Test
    public void testCache() throws Exception {
        HibernateUtil hibernateUtil = new HibernateUtil(getClass().getResource("/hibernate.cfg.xml"));
        HibernateCacheManager cacheManager = new HibernateCacheManager(hibernateUtil);
        Assert.assertFalse(cacheManager.getCache(CACHE_A).get(ENTRY_1_KEY).isPresent());

        // Create entry
        CacheManager cm = new CacheManagerImpl(hibernateUtil);
        cm.saveNewCacheEntry(new CacheEntry(CACHE_A, ENTRY_1_KEY, "value"));

        // Wait for eviction
        Thread.sleep(2000);

        Assert.assertTrue(cacheManager.getCache(CACHE_A).get(ENTRY_1_KEY).isPresent());
        Assert.assertEquals("value", cacheManager.getCache(CACHE_A).get(ENTRY_1_KEY).get());
        Assert.assertFalse(cacheManager.getCache(CACHE_B).get(ENTRY_1_KEY).isPresent());

        // Wait for eviction
        Thread.sleep(2500);
        // Entries are by default just invalidated not removed until cleaned, so force a clean of invalidated objects
        cacheManager.getCache(CACHE_A).cleanUp();

        Assert.assertEquals(0, cacheManager.getCache(CACHE_A).size());
    }
}
