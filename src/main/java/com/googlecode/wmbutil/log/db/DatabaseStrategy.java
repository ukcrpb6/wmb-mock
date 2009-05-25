package com.googlecode.wmbutil.log.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class DatabaseStrategy {

    
    public static DatabaseStrategy createStrategy(Connection conn) throws SQLException {
        String database = conn.getMetaData().getDatabaseProductName();

        if ("Oracle".equals(database)) {
            return new OracleDatabaseStrategy();
        } else {
            return new AutoIncrementDatabaseStrategy();
        }
    }
    
    public abstract PreparedStatement prepareInsertStatement(Connection conn) throws SQLException;

    public abstract int getGeneratedKey(Statement stmt) throws SQLException;

    public abstract PreparedStatement prepareInsertBusinessStatement(Connection conn) throws SQLException;

}
