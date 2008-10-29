package com.googlecode.wmbutil.log;

import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * Main log interface. Will also pick the following from the environment:
 * <ul>
 *      <li>Timestamp</li>
 *      <li>WMB flow name</li>
 *      <li>WMB broker name</li>
 * </ul>
 */
public interface TransactionLog {

    void debug(String message, String messageId, String businessId, MbMessageAssembly assembly);
    void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly);
    void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t);
    
    void info(String message, String messageId, String businessId, MbMessageAssembly assembly);
    void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly);
    void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t);

    void warn(String message, String messageId, String businessId, MbMessageAssembly assembly);
    void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly);
    void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t);

    void error(String message, String messageId, String businessId, MbMessageAssembly assembly);
    void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly);
    void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t);
}
