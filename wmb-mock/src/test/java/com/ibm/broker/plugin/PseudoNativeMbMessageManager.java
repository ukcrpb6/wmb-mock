/**
 * Copyright 2012 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.broker.plugin;

import com.google.common.collect.Maps;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;

import java.util.Map;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
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

    private long getNativeHandle(PseudoNativeMbElement element) {
        return element == null ? 0L : element.hashCode();
    }

    @Override
    public long _createMessage(long handle) throws MbException {
        PseudoNativeMbMessage message = new PseudoNativeMbMessage();
        nativeMessages.put((long) message.hashCode(), message);
        return message.hashCode();
    }

    @Override
    public void _createMessage(long[] paramArrayOfLong) throws MbException {
        PseudoNativeMbMessage message = new PseudoNativeMbMessage();
        paramArrayOfLong[0] = message.hashCode();
        paramArrayOfLong[1] = 0L;
        nativeMessages.put((long) message.hashCode(), message);
    }

    @Override
    public void _clearMessage(long handle, long inputContextHandle) throws MbException {
//        nativeMessages.get(handle).clearMessage(inputContextHandle);
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
        return getNativeHandle(nativeMessages.get(handle).getRootElement());
    }

    @Override
    public void _copy(long handle, long inputContextHandle) throws MbException {
//        nativeMessages.get(handle).copy(inputContextHandle);
        throw new UnsupportedOperationException();
    }

    @Override
    public Object _evaluateXPath(long handle, long xpathHandle, MbXPath paramMbXPath) throws MbException {
//        return nativeMessages.get(handle).evaluateXPath(xpathHandle, paramMbXPath);
        throw new UnsupportedOperationException();
    }

    @Override
    public Object _evaluateXPath(long handle, String paramString) throws MbException {
        return nativeMessages.get(handle).evaluateXPath(paramString);
    }

    public void visit(MbMessageVisitor visitor) throws MbException {
        for(PseudoNativeMbMessage message : nativeMessages.values()) {
            message.accept(visitor);
        }
    }

    public void visit(long handle, MbMessageVisitor visitor) throws MbException {
        nativeMessages.get(handle).accept(visitor);
    }
}
