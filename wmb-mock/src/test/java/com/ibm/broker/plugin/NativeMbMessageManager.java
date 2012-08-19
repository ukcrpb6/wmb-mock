package com.ibm.broker.plugin;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface NativeMbMessageManager {
    
    long _createMessage(long handle) throws MbException;

    void _createMessage(long[] paramArrayOfLong) throws MbException;

    void _clearMessage(long handle, long inputContextHandle) throws MbException;

    void _finalizeMessage(long handle, int paramInt) throws MbException;

    byte[] _getBuffer(long handle) throws MbException;

    void _writeBuffer(long handle) throws MbException;

    long _getRootElement(long handle) throws MbException;

    void _copy(long handle, long inputContextHandle) throws MbException;

    Object _evaluateXPath(long handle, long inputContextHandle, MbXPath paramMbXPath) throws MbException;

    Object _evaluateXPath(long handle, String paramString) throws MbException;
    
}
