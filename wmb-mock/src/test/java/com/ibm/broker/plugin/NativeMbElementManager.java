package com.ibm.broker.plugin;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface NativeMbElementManager {
    long _createElementAfter(long handle) throws MbException;

    long _createElementAfterUsingParser(long handle, String paramString) throws MbException;

    long _createElementBefore(long handle) throws MbException;

    long _createElementBeforeUsingParser(long handle, String paramString) throws MbException;

    long _createElementAsFirstChild(long handle) throws MbException;

    long _createElementAsFirstChildUsingParser(long handle, String paramString) throws MbException;

    long _createElementAsLastChild(long handle) throws MbException;

    long _createElementAsLastChildAndSet(long handle, int paramInt, String paramString, Object paramObject) throws MbException;

    long _createElementAsLastChildUsingParser(long handle, String paramString) throws MbException;

    String _getName(long handle) throws MbException;

    String _getNamespace(long handle) throws MbException;

    void _setName(long handle, String paramString) throws MbException;

    void _setNamespace(long handle, String paramString) throws MbException;

    int _getType(long handle) throws MbException;

    void _setType(long handle, int paramInt) throws MbException;

    Object _getValue(long handle) throws MbException;

    String _getValueAsString(long handle) throws MbException;

    void setNullValue(long handle) throws MbException;

    void setIntValue(long handle, int paramInt) throws MbException;

    void setLongValue(long handle, long value) throws MbException;

    void setDoubleValue(long handle, double paramDouble) throws MbException;

    void setDecimalValue(long handle, String paramString) throws MbException;

    void setBooleanValue(long handle, boolean paramBoolean) throws MbException;

    void setBytesValue(long handle, byte[] paramArrayOfByte) throws MbException;

    void setStringValue(long handle, String paramString) throws MbException;

    void setMbTimeValue(long handle, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean) throws MbException;

    void setDurationValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws MbException;

    void setMbTimeStampValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, boolean paramBoolean) throws MbException;

    void setMbDateValue(long handle, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    void setBitSetValue(long handle, boolean[] paramArrayOfBoolean) throws MbException;

    int _getValueState(long handle) throws MbException;

    String _getParserClassName(long handle) throws MbException;

    long _getParent(long handle) throws MbException;

    long _getNextSibling(long handle) throws MbException;

    long _getPreviousSibling(long handle) throws MbException;

    long _getFirstChild(long handle) throws MbException;

    long _getLastChild(long handle) throws MbException;

    long _getFirstElementByPath(long handle, String paramString) throws MbException;

    long[] _getAllElementsByPath(long handle, String paramString) throws MbException;

    void _addAfter(long handle, long childHandle) throws MbException;

    void _addBefore(long handle, long childHandle) throws MbException;

    void _addAsFirstChild(long handle, long childHandle) throws MbException;

    void _addAsLastChild(long handle, long childHandle) throws MbException;

    void _copyElementTree(long handle, long childHandle) throws MbException;

    void _detach(long handle) throws MbException;

    byte[] _toBitstream(long handle, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    long _copy(long handle) throws MbException;

    long _createElementAsLastChildFromBitstream(long handle, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    Object _evaluateXPath(long handle, long inputContextHandle, MbXPath xpath) throws MbException;

    Object _evaluateXPath(long handle, String xpath) throws MbException;
}
