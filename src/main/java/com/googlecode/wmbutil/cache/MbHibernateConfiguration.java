package com.googlecode.wmbutil.cache;

import org.hibernate.cfg.Configuration;

public class MbHibernateConfiguration {

    public static Configuration createConfiguration() {
        return createConfiguration("org.hibernate.dialect.MySQL5Dialect");
    }

    public static Configuration createConfiguration(String dialect) {
        return new Configuration()
                .addClass(CacheEntry.class)
                .setProperty("current_session_context_class", "thread")
                .setProperty("connection.autocommit", "false")
                .setProperty("use_second_level_cache", "false")
                .setProperty("dialect", dialect);
    }

}
