package com.ibm.broker.plugin;

import com.google.common.collect.Maps;

import java.util.Map;

public class PseudoNativeMbElementManager implements NativeMbElement {

    Map<Long, PseudoNativeMbElement> nativeMessages = Maps.newHashMap();

    private static class InstanceHolder {
        static final PseudoNativeMbElementManager instance = new PseudoNativeMbElementManager();
    }

    public static PseudoNativeMbElementManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbElementManager() {
    }
    
    @Override
    public long _createElementAfter(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAfterUsingParser(long handle, String paramString) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementBefore(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementBeforeUsingParser(long handle, String paramString) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsFirstChild(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsFirstChildUsingParser(long handle, String paramString) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsLastChild(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsLastChildAndSet(long handle, int paramInt, String paramString, Object paramObject) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsLastChildUsingParser(long handle, String paramString) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String _getName(long handle) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String _getNamespace(long handle) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _setName(long handle, String paramString) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _setNamespace(long handle, String paramString) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int _getType(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _setType(long handle, int paramInt) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object _getValue(long handle) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String _getValueAsString(long handle) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setNullValue(long handle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setIntValue(long handle, int paramInt) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLongValue(long handle, long value) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDoubleValue(long handle, double paramDouble) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDecimalValue(long handle, String paramString) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBooleanValue(long handle, boolean paramBoolean) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBytesValue(long handle, byte[] paramArrayOfByte) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStringValue(long handle, String paramString) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMbTimeValue(long handle, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDurationValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMbTimeStampValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, boolean paramBoolean) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMbDateValue(long handle, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBitSetValue(long handle, boolean[] paramArrayOfBoolean) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int _getValueState(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String _getParserClassName(long handle) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getParent(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getNextSibling(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getPreviousSibling(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getFirstChild(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getLastChild(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _getFirstElementByPath(long handle, String paramString) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long[] _getAllElementsByPath(long handle, String paramString) throws MbException {
        return new long[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _addAfter(long handle, long childHandle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _addBefore(long handle, long childHandle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _addAsFirstChild(long handle, long childHandle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _addAsLastChild(long handle, long childHandle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _copyElementTree(long handle, long childHandle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void _detach(long handle) throws MbException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] _toBitstream(long handle, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _copy(long handle) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long _createElementAsLastChildFromBitstream(long handle, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object _evaluateXPath(long handle, long inputContextHandle, MbXPath xpath) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object _evaluateXPath(long handle, String xpath) throws MbException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
