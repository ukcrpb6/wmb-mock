package com.googlecode.wmbutil.cache.dao;

import com.googlecode.wmbutil.dao.GenericDAO;

import java.math.BigInteger;

public interface CacheEntryDAO extends GenericDAO<CacheEntry, BigInteger> {
  CacheEntry findInCacheByKey(String cacheName, String key);
}
