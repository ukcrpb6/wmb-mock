package com.googlecode.wmbutil.dao.hibernate;


import com.googlecode.wmbutil.dao.MbConnectionProvider;
import com.ibm.broker.plugin.MbException;
import org.hibernate.HibernateException;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.service.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbHibernateConnectionProvider extends UserSuppliedConnectionProviderImpl implements Configurable {

  private static ThreadLocal<MbConnectionProvider> internalConnectionProvider = new ThreadLocal<MbConnectionProvider>();

  public static void setInternalConnectionProvider(MbConnectionProvider internalConnectionProvider) {
    MbHibernateConnectionProvider.internalConnectionProvider.set(internalConnectionProvider);
  }

  @Override public Connection getConnection() throws SQLException {
    if (internalConnectionProvider.get() == null) {
      throw new HibernateException("Connection provider must be set.");
    }
    try {
      return protectMessageBrokerConnection(internalConnectionProvider.get().get());
    } catch (MbException e) {
      throw new SQLException(e);
    }
  }

  private Connection protectMessageBrokerConnection(final Connection protectedConnection) {
    return (Connection) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
      @Override public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("close".equals(method.getName()) ||
            "commit".equals(method.getName()) ||
            "rollback".equals(method.getName())) {
          return null;
        }
        return method.invoke(protectedConnection, objects);
      }
    });
  }

  @Override public void configure(Properties properties) throws HibernateException {
    if (internalConnectionProvider.get() == null) {
      throw new HibernateException("Connection provider must be set.");
    }
  }

  @Override public void closeConnection(Connection conn) throws SQLException {
    // Cannot free connection from MB pool
  }

}
