package com.googlecode.wmbutil.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import com.googlecode.wmbutil.jdbc.DataSourceLocator;

/**
 * Logs messages to a JDBC data source
 */
public class JdbcTransactionAppender extends AppenderSkeleton {

    private String INSERT_SQL = "INSERT INTO TRANS_LOG (broker, message_flow, component, log_level, message, message_id, exception,  update_time) values(?, ?, ?, ?, ?, ?, ?, ?);";

    private String INSERT_BUSINESS_SQL = "INSERT INTO TRANS_LOG_BUSINESS (log_id, business_id) values(?, ?);";

    private String dataSource;
    
    protected void append(LoggingEvent event) {
        Object msg = event.getMessage();

        if (msg instanceof TransactionMessage) {
            TransactionMessage transMsg = (TransactionMessage) msg;

            DataSourceLocator dsLocator = DataSourceLocator.getInstance();
            DataSource ds = null;
            Connection conn = null;
            PreparedStatement stmt = null;
            PreparedStatement busStmt = null;

            try {
                ds = dsLocator.lookup(dataSource);

                conn = ds.getConnection();
                conn.setReadOnly(false);
                conn.setAutoCommit(false);

                stmt = conn.prepareStatement(INSERT_SQL);
                stmt.setString(1, transMsg.getBrokerName());
                stmt.setString(2, transMsg.getFlowName());
                stmt.setString(3, transMsg.getNodeName());
                stmt.setString(4, event.getLevel().toString());
                stmt.setString(5, transMsg.getMessage());
                stmt.setString(6, transMsg.getMessageId());

                if (event.getThrowableInformation() != null
                        && event.getThrowableInformation().getThrowable() != null) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    event.getThrowableInformation().getThrowable()
                            .printStackTrace(pw);
                    stmt.setString(7, sw.toString());
                } else {
                    stmt.setString(7, null);
                }
                stmt.setTimestamp(8, new Timestamp(new java.util.Date()
                        .getTime()));

                stmt.executeUpdate();

                ResultSet rs = null;
                int logId;
                try {
                    rs = stmt.getGeneratedKeys();
                    rs.next();
                    logId = rs.getInt(1);
                } finally {
                    closeQuitely(rs);
                }

                busStmt = conn.prepareStatement(INSERT_BUSINESS_SQL);
                if (transMsg.getBusinessIds() != null) {
                    String[] businessIds = transMsg.getBusinessIds();
                    for (int i = 0; i < businessIds.length; i++) {
                        busStmt.setInt(1, logId);
                        busStmt.setString(2, businessIds[i]);
                        busStmt.execute();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                LogLog
                        .error(
                                "Failed to write log message to transaction log database",
                                e);
            } finally {
                closeQuitely(stmt);
                closeQuitely(busStmt);
                closeQuitely(conn);
            }
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("Received log event from ");
            sb.append(event.getLoggerName());
            sb.append(". ");
            sb.append(getClass().getSimpleName());
            sb.append(" can only handle WMB transaction messages, check Log4j configuration.");
            
            LogLog
                .warn(sb.toString());
            
        }
    }

    private void closeQuitely(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void closeQuitely(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void closeQuitely(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public void close() {
        // do nothing
        
    }

    public boolean requiresLayout() {
        return false;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }


}
