package com.googlecode.wmbutil.dao;

import com.googlecode.wmbutil.cache.dao.CacheEntry;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class MbHibernateConfiguration {

    public static Configuration createConfiguration() {
        return createConfiguration("org.hibernate.dialect.MySQL5Dialect");
    }

    public static Configuration createConfiguration(String dialect) {
        return new Configuration()
                .addAnnotatedClass(CacheEntry.class)
                .setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .setProperty(AvailableSettings.AUTOCOMMIT, "false")
                .setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "false")
                .setProperty(AvailableSettings.DIALECT, dialect);
    }

}
