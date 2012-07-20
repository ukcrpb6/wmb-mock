package com.googlecode.wmbutil.cache;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HibernateCacheManager {

    private final Map<String, LoadingCache<String, Optional<String>>> caches = Maps.newConcurrentMap();

    private final HibernateUtil hibernateUtil;

    public HibernateCacheManager(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    private CacheLoader<String, Optional<String>> createHibernateLoader(final String cacheName) {
        return new CacheLoader<String, Optional<String>>() {
            CacheManagerImpl manager = new CacheManagerImpl(hibernateUtil);
            @Override
            public Optional<String> load(String key) throws Exception {
                return Optional.fromNullable(manager.findInCacheByKey(cacheName, key))
                        .transform(new Function<CacheEntry, String>() {
                            @Override
                            public String apply(CacheEntry input) {
                                return input.getValue();
                            }
                        });
            }
        };
    }

    public LoadingCache<String, Optional<String>> getCache(final String cacheName) {
        LoadingCache<String, Optional<String>> cache = caches.get(cacheName);
        if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .expireAfterAccess(1, TimeUnit.SECONDS)
                    .build(createHibernateLoader(cacheName));
            caches.put(cacheName, cache);
        }
        return cache;
    }
}
