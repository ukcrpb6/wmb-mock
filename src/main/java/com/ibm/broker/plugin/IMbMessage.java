package com.ibm.broker.plugin;

public interface IMbMessage extends MbXPathSupport {
    boolean isReadOnly();

    byte[] getBuffer() throws MbException;

    void clearMessage() throws MbException;

    void finalizeMessage(int arg0) throws MbException;

    MbElement getRootElement() throws MbException;
}