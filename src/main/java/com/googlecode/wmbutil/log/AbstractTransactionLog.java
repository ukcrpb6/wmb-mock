package com.googlecode.wmbutil.log;

import org.apache.log4j.Level;

import com.ibm.broker.plugin.MbMessageAssembly;

public abstract class AbstractTransactionLog implements TransactionLog {

    protected void transLog(Level level, String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        transLog(level, message, messageId, businessIds, assembly, null);
    }
    
    protected abstract void transLog(Level level, String message, String messageId, String[] businessIds, MbMessageAssembly assembly, Throwable t);
    
    public void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        transLog(Level.DEBUG, message, messageId, businessIds, assembly);
    }

    public void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        transLog(Level.INFO, message, messageId, businessIds, assembly);
    }

    public void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        transLog(Level.WARN, message, messageId, businessIds, assembly);
    }

    public void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        transLog(Level.ERROR, message, messageId, businessIds, assembly);
    }

    public void debug(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        transLog(Level.DEBUG, message, messageId, new String[]{businessId}, assembly);
        
    }

    public void error(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        transLog(Level.ERROR, message, messageId, new String[]{businessId}, assembly);
        
    }

    public void info(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        transLog(Level.INFO, message, messageId, new String[]{businessId}, assembly);
    }

    public void warn(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        transLog(Level.WARN, message, messageId, new String[]{businessId}, assembly);
    }

    public void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        transLog(Level.DEBUG, message, messageId, businessIds, assembly, t);
        
    }

    public void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        transLog(Level.ERROR, message, messageId, businessIds, assembly, t);
        
    }

    public void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        transLog(Level.INFO, message, messageId, businessIds, assembly, t);
        
    }

    public void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        transLog(Level.WARN, message, messageId, businessIds, assembly, t);
        
    }
}
