package com.ibm.broker.plugin;

import com.google.common.collect.Maps;

import java.util.Map;

public class PseudoNativeMbMessageManager implements NativeMbMessageManager {

    Map<Long, PseudoNativeMbMessage> nativeMessages = Maps.newHashMap();

    private static class InstanceHolder {
        static final PseudoNativeMbMessageManager instance = new PseudoNativeMbMessageManager();
    }

    public static PseudoNativeMbMessageManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbMessageManager() {
    }

    @Override
    public long _createMessage(long handle) throws MbException {
        PseudoNativeMbMessage message = new PseudoNativeMbMessage();
        nativeMessages.put((long) message.hashCode(), message);
        return message.hashCode();
    }

    @Override
    public void _createMessage(long[] paramArrayOfLong) throws MbException {
        // Brand spanking new message
        throw new UnsupportedOperationException();
    }

    @Override
    public void _clearMessage(long handle, long inputContextHandle) throws MbException {
        nativeMessages.get(handle).clearMessage(inputContextHandle);
    }

    @Override
    public void _finalizeMessage(long handle, int noneOrValidate) throws MbException {
        nativeMessages.get(handle).finalizeMessage(noneOrValidate);
    }

    @Override
    public byte[] _getBuffer(long handle) throws MbException {
        return nativeMessages.get(handle).getBuffer();
    }

    @Override
    public void _writeBuffer(long handle) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long _getRootElement(long handle) throws MbException {
        return nativeMessages.get(handle).getRootElement();
    }

    @Override
    public void _copy(long handle, long inputContextHandle) throws MbException {
        nativeMessages.get(handle).copy(inputContextHandle);
    }

    @Override
    public Object _evaluateXPath(long handle, long xpathHandle, MbXPath paramMbXPath) throws MbException {
        return nativeMessages.get(handle).evaluateXPath(xpathHandle, paramMbXPath);
    }

    @Override
    public Object _evaluateXPath(long handle, String paramString) throws MbException {
        return nativeMessages.get(handle).evaluateXPath(paramString);
    }
}
