package com.ibm.broker.plugin;

public interface MbXPathSupport {
    Object evaluateXPath(MbXPath arg0) throws MbException;

    Object evaluateXPath(String arg0) throws MbException;
}
