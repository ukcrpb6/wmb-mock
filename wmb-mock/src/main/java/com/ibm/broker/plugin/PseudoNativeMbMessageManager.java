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

import com.google.common.base.Throwables;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessageManager
        extends AbstractNativeManager<MbMessage, PseudoNativeMbMessage> implements NativeMbMessageManager {

    private static class InstanceHolder {
        static final PseudoNativeMbMessageManager instance = new PseudoNativeMbMessageManager();
    }

    public static PseudoNativeMbMessageManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbMessageManager() {
    }

    @Override protected PseudoNativeMbMessage createNativeInstance(Object... parameters) {
        return new PseudoNativeMbMessage();
    }

    @Override public long getHandle(MbMessage object) {
        return object.getHandle();
    }

    /*
     * Native Interface
     */

    @Override
    public long _createMessage(long handle) throws MbException {
        return allocate().getHandle();
    }

    @Override
    public void _createMessage(long[] paramArrayOfLong) throws MbException {
        PseudoNativeMbMessage message = allocate();
        paramArrayOfLong[0] = message.getHandle();
        paramArrayOfLong[1] = 0L;
    }

    @Override
    public void _clearMessage(long handle, long inputContextHandle) throws MbException {
        getNative(handle).clearMessage();
    }

    @Override
    public void _finalizeMessage(long handle, int noneOrValidate) throws MbException {
        getNative(handle).finalizeMessage(noneOrValidate);
    }

    @Override
    public byte[] _getBuffer(long handle) throws MbException {
        return getNative(handle).getBuffer();
    }

    @Override
    public void _writeBuffer(long handle) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long _getRootElement(long handle) throws MbException {
        return getNative(handle).getRootElement().getHandle();
    }

    @Override
    public void _copy(long handle, long inputContextHandle) throws MbException {
        try {
            PseudoNativeMbElement clone = getNative(inputContextHandle).getRootElement().clone();
            getNative(handle).setRootElement(clone);
        } catch (CloneNotSupportedException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public Object _evaluateXPath(long handle, long xpathHandle, MbXPath paramMbXPath) throws MbException {
        // TODO: XPath handle is it required what does it do?
        return getNative(handle).evaluateXPath(paramMbXPath);
    }

    @Override
    public Object _evaluateXPath(long handle, String paramString) throws MbException {
        return getNative(handle).evaluateXPath(paramString);
    }

    /*
     * Visitor interface
     */

    public void visit(MbMessageVisitor visitor) throws MbException {
        for (PseudoNativeMbMessage message : getAllocations()) {
            message.accept(visitor);
        }
    }

    public void visit(long handle, MbMessageVisitor visitor) throws MbException {
        getNative(handle).accept(visitor);
    }
}
