package com.googlecode.wmbutil.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.googlecode.wmbutil.jdbc.DataSourceLocator;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNode;

/**
 * Logs messages to a JDBC data source
 */
public class JdbcTransactionLog extends AbstractTransactionLog {

    private String INSERT_SQL = "INSERT INTO TRANS_LOG (broker, message_flow, component, level, message, message_id, exception,  update_time) values(?, ?, ?, ?, ?, ?, ?, ?);";
    private String INSERT_BUSINESS_SQL = "INSERT INTO TRANS_LOG_BUSINESS (log_id, business_id) values(?, ?);";

    
    private Logger log = Logger.getLogger(JdbcTransactionLog.class);
    private String component;
    private String brokerName;
    private String flowName;

    // TODO remove, only used for testing
    JdbcTransactionLog(String brokerName, String flowName, String component) throws MbException {
        this.brokerName = brokerName;
        this.flowName = flowName;
        this.component = component;
    }

    
    public JdbcTransactionLog(MbNode node, String component) throws MbException {
        brokerName = node.getBroker().getName();
        flowName = node.getMessageFlow().getName();
        this.component = component;
    }

    public JdbcTransactionLog(MbNode node) throws MbException {
        this(node, null);
    }

    
    protected void log(Level level, String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t) {
        DataSourceLocator dsLocator = DataSourceLocator.getInstance();
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement busStmt = null;
        
        try {
            ds = dsLocator.lookup("translog");
        
            conn = ds.getConnection();
            conn.setReadOnly(false);
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setString(1, brokerName);
            stmt.setString(2, flowName);
            stmt.setString(3, component);
            stmt.setString(4, level.toString());
            stmt.setString(5, message);
            stmt.setString(6, messageId);
            
            if(t != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                t.printStackTrace(pw);
                stmt.setString(7, sw.toString());
            } else {
                stmt.setString(7, null);
            }
            stmt.setDate(8, new Date(new java.util.Date().getTime())); 
            
            stmt.executeUpdate();
            
            ResultSet rs = null;
            int logId;
            try  {
                rs = stmt.getGeneratedKeys();
                rs.next();
                logId = rs.getInt(1);
            } finally {
                closeQuitely(rs);
            }
            
            busStmt = conn.prepareStatement(INSERT_BUSINESS_SQL);
            for(int i = 0; i<businessIds.length; i++) {
                busStmt.setInt(1, logId);
                busStmt.setString(2, businessIds[i]);
                busStmt.execute();
            }
            conn.commit();
        } catch(SQLException e) {
             log.error("Failed to write log message to transaction log database", e);
             String fqcn = "wmb." + brokerName + "." + flowName;             
             log.log(fqcn, level, message, t);
        } finally {
            closeQuitely(stmt);
            closeQuitely(busStmt);
            closeQuitely(conn);
        }
    }
    
    private void closeQuitely(Statement stmt) {
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void closeQuitely(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
    
    private void closeQuitely(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
