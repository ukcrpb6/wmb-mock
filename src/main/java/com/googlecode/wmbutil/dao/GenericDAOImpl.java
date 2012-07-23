package com.googlecode.wmbutil.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private final HibernateUtil hibernateUtil;

    public GenericDAOImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    protected Session getSession() {
        return hibernateUtil.getSession();
    }

    @Override
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void merge(T entity) {
        getSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public List<T> findMany(Query query) {
        return (List<T>) query.list();
    }

    @Override
    public T findOne(Query query) {
        return (T) query.uniqueResult();
    }

    @Override
    public List<T> findAll(Class<T> clazz) {
        return (List<T>) getSession().createQuery("from " + clazz.getName()).list();
    }

    @Override
    public T findById(Class<T> clazz, ID id) {
        return (T) getSession().get(clazz, id);
    }
}
