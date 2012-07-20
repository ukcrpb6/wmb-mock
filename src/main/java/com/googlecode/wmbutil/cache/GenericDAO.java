package com.googlecode.wmbutil.cache;

import org.hibernate.Query;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

    void save(T entity);

    void merge(T entity);

    void delete(T entity);

    List<T> findMany(Query query);

    T findOne(Query query);

    List<T> findAll(Class<T> clazz);

    T findById(Class<T> clazz, ID id);

}
