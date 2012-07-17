package com.ibm.broker.plugin;

public interface IMbElement {
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

    IMbElement createElementAfter(int arg0) throws MbException;

    IMbElement createElementAfter(String arg0) throws MbException;

    IMbElement createElementAfter(int arg0, String arg1, Object arg2) throws MbException;

    IMbElement createElementBefore(int arg0) throws MbException;

    IMbElement createElementBefore(int arg0, String arg1, Object arg2) throws MbException;

    IMbElement createElementBefore(String arg0) throws MbException;

    IMbElement createElementAsFirstChild(int arg0) throws MbException;

    IMbElement createElementAsFirstChild(String arg0) throws MbException;

    IMbElement createElementAsFirstChild(int arg0, String arg1, Object arg2) throws MbException;

    IMbElement createElementAsLastChild(int arg0) throws MbException;

    IMbElement createElementAsLastChild(int arg0, String arg1, Object arg2) throws MbException;

    IMbElement createElementAsLastChild(String arg0) throws MbException;

    void setNamespace(String arg0) throws MbException;

    int getParserContext() throws MbException;

    int getSpecificType() throws MbException;

    void setSpecificType(int arg0) throws MbException;

    String getValueAsString() throws MbException;

    int getValueState() throws MbException;

    String getParserClassName() throws MbException;

    IMbElement getNextSibling() throws MbException;

    IMbElement getPreviousSibling() throws MbException;

    IMbElement getFirstChild() throws MbException;

    IMbElement getLastChild() throws MbException;

    Object evaluateXPath(String arg0) throws MbException;

    Object evaluateXPath(MbXPath arg0) throws MbException;

    IMbElement getFirstElementByPath(String arg0) throws MbException;

    IMbElement[] getAllElementsByPath(String arg0) throws MbException;

    void addAfter(IMbElement arg0) throws MbException;

    void addAsFirstChild(IMbElement arg0) throws MbException;

    void addAsLastChild(IMbElement arg0) throws MbException;

    void copyElementTree(IMbElement arg0) throws MbException;

    void detach() throws MbException;

    byte[] toBitstream(String arg0, String arg1, String arg2, int arg3, int arg4, int arg5) throws MbException;

    IMbElement createElementAsLastChildFromBitstream(byte[] arg0, String arg1, String arg2, String arg3, String arg4, int arg5, int arg6, int arg7) throws MbException;

    MbElement getMbElement();
}