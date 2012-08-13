package com.googlecode.wmbutil.dao.hibernate;

import com.google.common.base.Throwables;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import java.net.URL;

public class HibernateUtil {

  private final SessionFactory sessionFactory;

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
