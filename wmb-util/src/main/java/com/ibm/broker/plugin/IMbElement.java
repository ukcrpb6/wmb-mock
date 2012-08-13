package com.ibm.broker.plugin;

public interface IMbElement extends MbXPathSupport {
    String getName() throws MbException;

    Object getValue() throws MbException;

    MbElement getParent() throws MbException;

    void setName(String arg0) throws MbException;

    MbElement copy() throws MbException;

    int getType() throws MbException;

    boolean isReadOnly();

    void setValue(Object arg0) throws MbException;

    void addBefore(MbElement arg0) throws MbException;

    boolean is(MbElement arg0);

    String getNamespace() throws MbException;

    MbElement createElementAfter(int arg0) throws MbException;

    MbElement createElementAfter(String arg0) throws MbException;

    MbElement createElementAfter(int arg0, String arg1, Object arg2) throws MbException;

    MbElement createElementBefore(int arg0) throws MbException;

    MbElement createElementBefore(int arg0, String arg1, Object arg2) throws MbException;

    MbElement createElementBefore(String arg0) throws MbException;

    MbElement createElementAsFirstChild(int arg0) throws MbException;

    MbElement createElementAsFirstChild(String arg0) throws MbException;

    MbElement createElementAsFirstChild(int arg0, String arg1, Object arg2) throws MbException;

    MbElement createElementAsLastChild(int arg0) throws MbException;

    MbElement createElementAsLastChild(int arg0, String arg1, Object arg2) throws MbException;

    MbElement createElementAsLastChild(String arg0) throws MbException;

    void setNamespace(String arg0) throws MbException;

    int getParserContext() throws MbException;

    int getSpecificType() throws MbException;

    void setSpecificType(int arg0) throws MbException;

    String getValueAsString() throws MbException;

    int getValueState() throws MbException;

    String getParserClassName() throws MbException;

    MbElement getNextSibling() throws MbException;

    MbElement getPreviousSibling() throws MbException;

    MbElement getFirstChild() throws MbException;

    MbElement getLastChild() throws MbException;

    MbElement getFirstElementByPath(String arg0) throws MbException;

    MbElement[] getAllElementsByPath(String arg0) throws MbException;

    void addAfter(MbElement arg0) throws MbException;

    void addAsFirstChild(MbElement arg0) throws MbException;

    void addAsLastChild(MbElement arg0) throws MbException;

    void copyElementTree(MbElement arg0) throws MbException;

    void detach() throws MbException;

    byte[] toBitstream(String arg0, String arg1, String arg2, int arg3, int arg4, int arg5) throws MbException;

    MbElement createElementAsLastChildFromBitstream(byte[] arg0, String arg1, String arg2, String arg3, String arg4, int arg5, int arg6, int arg7) throws MbException;
}