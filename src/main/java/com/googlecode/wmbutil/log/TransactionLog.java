package com.googlecode.wmbutil.log;

/**
 * Main log interface. Will also pick the following from the environment:
 * <ul>
 *      <li>Timestamp</li>
 *      <li>WMB flow name</li>
 *      <li>WMB broker name</li>
 * </ul>
 */
public interface TransactionLog {

    void debug(String message, String messageId, String businessId);
    void debug(String message, String messageId, String[] businessIds);
    void debug(String message, String messageId, String[] businessIds, Throwable t);

    void info(String message, String messageId, String businessId);
    void info(String message, String messageId, String[] businessIds);
    void info(String message, String messageId, String[] businessIds, Throwable t);

    void warn(String message, String messageId, String businessId);
    void warn(String message, String messageId, String[] businessIds);
    void warn(String message, String messageId, String[] businessIds, Throwable t);

    void error(String message, String messageId, String businessId);
    void error(String message, String messageId, String[] businessIds);
    void error(String message, String messageId, String[] businessIds, Throwable t);
}
