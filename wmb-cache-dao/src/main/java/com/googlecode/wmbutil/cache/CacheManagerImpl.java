package com.googlecode.wmbutil.cache;

import com.google.common.collect.ImmutableList;
import com.googlecode.wmbutil.cache.dao.CacheEntry;
import com.googlecode.wmbutil.cache.dao.CacheEntryDAO;
import com.googlecode.wmbutil.cache.dao.CacheEntryDAOImpl;
import com.googlecode.wmbutil.dao.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import java.math.BigInteger;
import java.util.List;

public class CacheManagerImpl implements CacheManager {

  private final HibernateUtil hibernateUtil;
  private final CacheEntryDAO cacheEntryDAO;

  public CacheManagerImpl(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
    this.cacheEntryDAO = new CacheEntryDAOImpl(hibernateUtil);
  }

  @Override
  public CacheEntry findInCacheByKey(String cacheName, String key) {
    CacheEntry entry = null;
    try {
      hibernateUtil.beginTransaction();
      entry = cacheEntryDAO.findInCacheByKey(cacheName, key);
      hibernateUtil.commitTransaction();
    } catch (NonUniqueResultException ex) {
      System.out.println("Query returned more than one result!");
      ex.printStackTrace();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    }
    return entry;
  }

  @Override
  public List<CacheEntry> loadAllCacheEntries() {
    List<CacheEntry> entries = ImmutableList.of();
    try {
      hibernateUtil.beginTransaction();
      entries = ImmutableList.copyOf(cacheEntryDAO.findAll(CacheEntry.class));
      hibernateUtil.commitTransaction();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    }
    return entries;
  }

  @Override
  public void saveNewCacheEntry(CacheEntry entry) {
    try {
      hibernateUtil.beginTransaction();
      cacheEntryDAO.save(entry);
      hibernateUtil.commitTransaction();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public CacheEntry findCacheEntryById(BigInteger id) {
    CacheEntry entry = null;
    try {
      hibernateUtil.beginTransaction();
      entry = cacheEntryDAO.findById(CacheEntry.class, id);
      hibernateUtil.commitTransaction();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    }
    return entry;
  }

  @Override
  public void deleteCacheEntry(CacheEntry entry) {
    try {
      hibernateUtil.beginTransaction();
      cacheEntryDAO.delete(entry);
      hibernateUtil.commitTransaction();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    }
  }
}
