package com.ibm.broker.plugin;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface NativeMbMessageAssemblyManager {
    long _createMessageAssembly(long handle, long messageHandle) throws MbException;

    /**
     * @deprecated a new global environment should not be created. Use alternative constructor
     */
    long _createMessageAssembly2(long handle, long localEnvironmentHandle, long globalEnvironmentHandle, long exceptionListHandle, long messageHandle) throws MbException;
  
    long _createMessageAssembly(long handle, long localEnvironmentHandle, long exceptionListHandle, long messageHandle) throws MbException;
  
    long _getMessage(long handle) throws MbException;
  
    long _getLocalEnvironment(long handle) throws MbException;
  
    long _getGlobalEnvironment(long handle) throws MbException;
  
    long _getExceptionList(long handle) throws MbException;
  
    void _clearMessageAssembly(long handle) throws MbException;    
}
