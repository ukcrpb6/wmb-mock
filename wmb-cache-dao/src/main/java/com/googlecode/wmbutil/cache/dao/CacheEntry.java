package com.googlecode.wmbutil.cache.dao;

import com.google.common.base.Objects;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table
@OptimisticLocking
public class CacheEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id;

  @Column
  private String cacheName;

  @Column
  private String name;

  @Column
  private String value;

  public CacheEntry() {}

  public CacheEntry(String cacheName, String name, String value) {
    this.cacheName = cacheName;
    this.name = name;
    this.value = value;
  }

  public String getCacheName() {
    return cacheName;
  }

  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String key) {
    this.name = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .add("cacheName", cacheName)
        .add("key", name)
        .add("value", value).toString();
  }
}
