package com.googlecode.wmbutil.dao;

import com.google.common.base.Throwables;
import com.googlecode.wmbutil.cache.MbProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;

import java.net.URL;
import java.sql.Connection;

public class HibernateUtil {

    private final SessionFactory sessionFactory;
    private MbProvider<Connection> connectionProvider;

    public HibernateUtil(URL url) {
        this(new Configuration().configure(url));
    }

    public HibernateUtil(Configuration cfg) {
        try {
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        } catch (Throwable ex) {
            Throwables.propagateIfInstanceOf(ex, ExceptionInInitializerError.class);
            throw new ExceptionInInitializerError(ex.getMessage());
        }
    }

    public HibernateUtil(Configuration cfg, MbProvider<Connection> connectionProvider) {
        try {
            if (cfg.getProperty(AvailableSettings.CONNECTION_PROVIDER) != null) {
                throw new ExceptionInInitializerError("Property " + AvailableSettings.CONNECTION_PROVIDER +
                        " cannot be specified for this session factory.");
            }
            cfg.setProperty(AvailableSettings.CONNECTION_PROVIDER, UserSuppliedConnectionProviderImpl.class.getName());
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
            this.connectionProvider = connectionProvider;
        } catch (Throwable ex) {
            Throwables.propagateIfInstanceOf(ex, ExceptionInInitializerError.class);
            throw new ExceptionInInitializerError(ex.getMessage());
        }
    }

    public Session getSession() {
        if (connectionProvider != null) {
            try {
                return sessionFactory.withOptions().connection(connectionProvider.get()).openSession();
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        } else {
            return sessionFactory.getCurrentSession();
        }
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
