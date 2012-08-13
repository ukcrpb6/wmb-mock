package com.googlecode.wmbutil.cache.dao;

import com.googlecode.wmbutil.dao.hibernate.GenericDAOImpl;
import com.googlecode.wmbutil.dao.hibernate.HibernateUtil;
import org.hibernate.Query;

import java.math.BigInteger;

public class CacheEntryDAOImpl extends GenericDAOImpl<CacheEntry, BigInteger> implements CacheEntryDAO {

  public CacheEntryDAOImpl(HibernateUtil hibernateUtil) {
    super(hibernateUtil);
  }

  @Override
  public CacheEntry findInCacheByKey(String cacheName, String key) {
    Query query = getSession().createQuery("SELECT e FROM CacheEntry e WHERE e.cacheName = :cacheName AND e.name = :key")
        .setParameter("cacheName", cacheName).setParameter("key", key);
    return findOne(query);
  }

}
