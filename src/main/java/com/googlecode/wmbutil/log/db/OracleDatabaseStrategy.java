package com.googlecode.wmbutil.log.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDatabaseStrategy extends DatabaseStrategy {

    protected String INSERT_SQL = "INSERT INTO TRANS_LOG (broker, message_flow, component, log_level, message, message_id, exception,  update_time, id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private String INSERT_BUSINESS_SQL = "INSERT INTO TRANS_LOG_BUSINESS (id, log_id, business_id) values(TRANS_LOG_SEQ.nextval, ?, ?)";
    
    private int logId;
    
    private int getNextId(Connection conn) throws SQLException {
        Statement idStmt = null;
        ResultSet rs = null;

        try {
            idStmt = conn.createStatement();
            rs = idStmt.executeQuery("SELECT TRANS_LOG_SEQ.nextval from dual");
            rs.next();
            return rs.getInt(1);
        } finally {
            DbUtil.closeQuitely(rs);
            DbUtil.closeQuitely(idStmt);
        }
    }
    
    public PreparedStatement prepareInsertStatement(Connection conn) throws SQLException {
        // this is a hack due to an ArrayIndexOutOfBoundsException in the 11g JDBC drivers:
        // http://forums.oracle.com/forums/thread.jspa?messageID=2698647
        // otherwise we could have embedded the nextval selection into the insert
        // and picked up the generated value using getGeneratedKeys
        logId = getNextId(conn);
        
        PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL); 
        insertStmt.setInt(9, logId);
        return insertStmt;
    }

    public int getGeneratedKey(Statement stmt) throws SQLException {
        return logId;
    }

    public PreparedStatement prepareInsertBusinessStatement(Connection conn) throws SQLException {
        return conn.prepareStatement(INSERT_BUSINESS_SQL); 
    }
            
}
