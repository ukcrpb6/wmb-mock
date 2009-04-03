package com.googlecode.wmbutil.log.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AutoIncrementDatabaseStrategy extends DatabaseStrategy {

    private String INSERT_SQL = "INSERT INTO TRANS_LOG (broker, message_flow, component, log_level, message, message_id, exception,  update_time) values(?, ?, ?, ?, ?, ?, ?, ?)";

    private String INSERT_BUSINESS_SQL = "INSERT INTO TRANS_LOG_BUSINESS (log_id, business_id) values(?, ?)";

    
    public PreparedStatement prepareInsertStatement(Connection conn) throws SQLException {
        return conn.prepareStatement(INSERT_SQL);
    }

    public int getGeneratedKey(Statement stmt) throws SQLException {
        ResultSet rs = null;
        try {
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } finally {
            DbUtil.closeQuitely(rs);
        }
    }

    public PreparedStatement prepareInsertBusinessStatement(Connection conn) throws SQLException {
        return conn.prepareStatement(INSERT_BUSINESS_SQL);
    }
    
            
}
