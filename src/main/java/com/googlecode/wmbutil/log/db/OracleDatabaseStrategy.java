package com.googlecode.wmbutil.log.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Note that due to a bug in the Oracle JDBC drivers, this code only
 * works with Oracle 10.2.0.3 drivers or newer. The Oracle 11 drivers do
 * not currently work.
 * So, if you get an  ArrayIndexOutOfBoundsException, make sure you use the
 * correct Oracle JDBC driver version. See this thread for a dicussion:
 * http://forums.oracle.com/forums/thread.jspa?messageID=2698647
 */
public class OracleDatabaseStrategy extends DatabaseStrategy {

    protected String INSERT_SQL = "INSERT INTO TRANS_LOG (id, broker, message_flow, component, log_level, message, message_id, exception,  update_time) values(TRANS_LOG_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private String INSERT_BUSINESS_SQL = "INSERT INTO TRANS_LOG_BUSINESS (id, log_id, business_id) values(TRANS_LOG_SEQ.nextval, ?, ?)";
    
    public PreparedStatement prepareInsertStatement(Connection conn) throws SQLException {
        return conn.prepareStatement(INSERT_SQL, new String[]{"id"}); 
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
