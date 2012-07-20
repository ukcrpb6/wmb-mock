package com.googlecode.wmbutil.cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.w3c.dom.Document;

import java.io.File;
import java.net.URL;

public class HibernateUtil {

    private final SessionFactory sessionFactory;

    public HibernateUtil() {
        this(new Configuration().configure());
    }

    public HibernateUtil(String configString) {
        this(new Configuration().configure(configString));
    }

    public HibernateUtil(File configFile) {
        this(new Configuration().configure(configFile));
    }

    public HibernateUtil(URL configUrl) {
        this(new Configuration().configure(configUrl));
    }

    public HibernateUtil(Document configDocument) {
        this(new Configuration().configure(configDocument));
    }

    public HibernateUtil(Configuration cfg) {
        try {
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Transaction beginTransaction() {
        return getSession().beginTransaction();
    }

    public void commitTransaction() {
        getSession().getTransaction().commit();
    }

    public void rollbackTransaction() {
        getSession().getTransaction().rollback();
    }

    public void closeSession() {
        getSession().close();
    }

}
