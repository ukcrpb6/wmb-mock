package com.googlecode.wmbutil.dao.hibernate;

import com.googlecode.wmbutil.dao.MbConnectionProvider;
import com.ibm.broker.plugin.MbNode;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class MbHibernateConfiguration {

  public static Configuration createConfiguration() {
    return createConfiguration("org.hibernate.dialect.MySQL5Dialect");
  }

  public static Configuration createConfiguration(String dialect) {
    return new Configuration()
        .setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread")
        .setProperty(AvailableSettings.AUTOCOMMIT, "false")
        .setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "false")
        .setProperty(AvailableSettings.DIALECT, dialect);
  }

  public static Configuration createConfiguration(MbNode node, String serviceName, String dialect) {
    MbHibernateConnectionProvider.setInternalConnectionProvider(
        new MbConnectionProvider(node, serviceName, MbNode.JDBC_TransactionType.MB_TRANSACTION_AUTO));
    return new Configuration()
        .setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread")
        .setProperty(AvailableSettings.AUTOCOMMIT, "false")
        .setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "false")
        .setProperty(AvailableSettings.DIALECT, dialect)
        .setProperty(AvailableSettings.CONNECTION_PROVIDER,
            MbHibernateConnectionProvider.class.getName())
        .setProperty(AvailableSettings.TRANSACTION_STRATEGY, MbTransactionFactory.class.getName());
  }

}
