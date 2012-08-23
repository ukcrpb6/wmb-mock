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
import com.ibm.broker.plugin.visitor.MbVisitable;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.TimeZone;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbElementManager extends AbstractNativeManager<MbElement, PseudoNativeMbElement> implements NativeMbElementManager, MbVisitable {

    private static class InstanceHolder {
        static final PseudoNativeMbElementManager instance = new PseudoNativeMbElementManager();
    }

    public static PseudoNativeMbElementManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbElementManager() {
    }

    @Override
    protected PseudoNativeMbElement createNativeInstance(Object... parameters) {
        return new PseudoNativeMbElement();
    }

    @Override
    public long getHandle(MbElement object) {
        return object.getHandle();
    }

    /*
     * Helper functions
     */

    private static long getNativeCheckedHandle(PseudoNativeMbElement element) {
        return element == null ? 0L : element.getHandle();
    }

    private static long[] getHandlesFromPseudoNativeMbElementList(PseudoNativeMbElement[] elements) {
        long[] arrayOfHandles = new long[elements.length];
        for (int i = 0; i < elements.length; i++) {
            arrayOfHandles[i] = getNativeCheckedHandle(elements[i]);
        }
        return arrayOfHandles;
    }

    PseudoNativeMbElement createPseudoNativeMbElement(PseudoNativeMbMessage message) {
        PseudoNativeMbElement messageRoot = allocate();
        messageRoot.setMessage(message);
        return messageRoot;
    }

    PseudoNativeMbElement createPseudoNativeMbElement() {
        return allocate();
    }

    /*
     * Native Interface
     */

    @Override
    public long _createElementAfter(long handle) throws MbException {
        final PseudoNativeMbElement child = createPseudoNativeMbElement();
        getNative(handle).addAfter(child);
        return getNativeCheckedHandle(child);
    }

    @Override
    public long _createElementAfterUsingParser(long handle, String paramString) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAfter(paramString));
    }

    @Override
    public long _createElementBefore(long handle) throws MbException {
        final PseudoNativeMbElement newMbElement = createPseudoNativeMbElement();
        getNative(handle).addBefore(newMbElement);
        return getNativeCheckedHandle(newMbElement);
    }

    @Override
    public long _createElementBeforeUsingParser(long handle, String paramString) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementBefore(paramString));
    }

    @Override
    public long _createElementAsFirstChild(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAsFirstChild(MbElement.TYPE_UNKNOWN));
    }

    @Override
    public long _createElementAsFirstChildUsingParser(long handle, String paramString) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAsFirstChild(paramString));
    }

    @Override
    public long _createElementAsLastChild(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAsLastChild(MbElement.TYPE_UNKNOWN));
    }

    @Override
    public long _createElementAsLastChildAndSet(long handle, int paramInt, String paramString, Object paramObject) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAsLastChild(paramInt, paramString, paramObject));
    }

    @Override
    public long _createElementAsLastChildUsingParser(long handle, String paramString) throws MbException {
        return getNativeCheckedHandle(getNative(handle).createElementAsLastChild(paramString));
    }

    @Override
    public String _getName(long handle) throws MbException {
        return getNative(handle).getName();
    }

    @Override
    public String _getNamespace(long handle) throws MbException {
        return getNative(handle).getNamespace();
    }

    @Override
    public void _setName(long handle, String paramString) throws MbException {
        getNative(handle).setName(paramString);
    }

    @Override
    public void _setNamespace(long handle, String paramString) throws MbException {
        getNative(handle).setNamespace(paramString);
    }

    @Override
    public int _getType(long handle) throws MbException {
        return getNative(handle).getType();
    }

    @Override
    public void _setType(long handle, int paramInt) throws MbException {
        getNative(handle).setSpecificType(paramInt);
    }

    @Override
    public Object _getValue(long handle) throws MbException {
        return getNative(handle).getValue();
    }

    @Override
    public String _getValueAsString(long handle) throws MbException {
        return getNative(handle).getValueAsString();
    }

    @Override
    public void setNullValue(long handle) throws MbException {
        getNative(handle).setValue(null);
    }

    @Override
    public void setIntValue(long handle, int paramInt) throws MbException {
        getNative(handle).setValue(paramInt);
    }

    @Override
    public void setLongValue(long handle, long value) throws MbException {
        getNative(handle).setValue(value);
    }

    @Override
    public void setDoubleValue(long handle, double paramDouble) throws MbException {
        getNative(handle).setValue(paramDouble);
    }

    @Override
    public void setDecimalValue(long handle, String paramString) throws MbException {
        getNative(handle).setValue(new BigDecimal(paramString));
    }

    @Override
    public void setBooleanValue(long handle, boolean paramBoolean) throws MbException {
        getNative(handle).setValue(paramBoolean);
    }

    @Override
    public void setBytesValue(long handle, byte[] paramArrayOfByte) throws MbException {
        getNative(handle).setValue(paramArrayOfByte);
    }

    @Override
    public void setStringValue(long handle, String paramString) throws MbException {
        getNative(handle).setValue(paramString);
    }

    @Override
    public void setMbTimeValue(long handle, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean) throws MbException {
        MbTime t = new MbTime(paramInt1, paramInt2, (int) (paramLong1 / 1000000));
        if (paramBoolean) {
            t.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        getNative(handle).setValue(t);
    }

    @Override
    public void setDurationValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws MbException {
        Duration duration;
        try {
            duration = DatatypeFactory.newInstance().newDuration(paramInt1 == 1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
        } catch (DatatypeConfigurationException e) {
            // TODO: Wrap as MbException
            throw Throwables.propagate(e);
        }
        getNative(handle).setValue(duration);
    }

    @Override
    public void setMbTimeStampValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, boolean paramBoolean) throws MbException {
        MbTimestamp ts = new MbTimestamp(paramInt1, paramInt2 - 1, paramInt3, paramInt4, paramInt5, (int) (paramLong1 / 1000000));
        if (paramBoolean) {
            ts.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        getNative(handle).setValue(ts);
    }

    @Override
    public void setMbDateValue(long handle, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        getNative(handle).setValue(new MbDate(paramInt1, paramInt2 - 1, paramInt3));
    }

    @Override
    public void setBitSetValue(long handle, boolean[] paramArrayOfBoolean) throws MbException {
        BitSet set = new BitSet();
        for (int i = 0; i < paramArrayOfBoolean.length; i++) {
            set.set(i, paramArrayOfBoolean[i]);
        }
        getNative(handle).setValue(set);
    }

    @Override
    public int _getValueState(long handle) throws MbException {
        return getNative(handle).getValueState();
    }

    @Override
    public String _getParserClassName(long handle) throws MbException {
        return getNative(handle).getParserClassName();
    }

    @Override
    public long _getParent(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getParent());
    }

    @Override
    public long _getNextSibling(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getNextSibling());
    }

    @Override
    public long _getPreviousSibling(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getPreviousSibling());
    }

    @Override
    public long _getFirstChild(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getFirstChild());
    }

    @Override
    public long _getLastChild(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getLastChild());
    }

    @Override
    public long _getFirstElementByPath(long handle, String paramString) throws MbException {
        return getNativeCheckedHandle(getNative(handle).getFirstElementByPath(paramString));
    }

    @Override
    public long[] _getAllElementsByPath(long handle, String paramString) throws MbException {
        return getHandlesFromPseudoNativeMbElementList(getNative(handle).getAllElementsByPath(paramString));
    }

    @Override
    public void _addAfter(long handle, long childHandle) throws MbException {
        getNative(handle).addAfter(getNative(childHandle));
    }

    @Override
    public void _addBefore(long handle, long childHandle) throws MbException {
        getNative(handle).addBefore(getNative(childHandle));
    }

    @Override
    public void _addAsFirstChild(long handle, long childHandle) throws MbException {
        getNative(handle).addAsFirstChild(getNative(childHandle));
    }

    @Override
    public void _addAsLastChild(long handle, long childHandle) throws MbException {
        getNative(handle).addAsLastChild(getNative(childHandle));
    }

    @Override
    public void _copyElementTree(long handle, long destinationHandle) throws MbException {
        getNative(handle).copyElementTree(getNative(destinationHandle));
    }

    @Override
    public void _detach(long handle) throws MbException {
        getNative(handle).detach();
    }

    @Override
    public byte[] _toBitstream(long handle, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        return getNative(handle).toBitstream(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramInt3);
    }

    @Override
    public long _copy(long handle) throws MbException {
        return getNativeCheckedHandle(getNative(handle).copy());
    }

    @Override
    public long _createElementAsLastChildFromBitstream(long handle, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        // TODO: Add handling of create from bitstream
        // return getNative(handle)._createElementAsLastChildFromBitstream(paramArrayOfByte, paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, paramInt3);
        throw new UnsupportedOperationException();
    }

    @Override
    public Object _evaluateXPath(long handle, long inputContextHandle, MbXPath xpath) throws MbException {
        return getNative(handle).evaluateXPath(xpath);
    }

    @Override
    public Object _evaluateXPath(long handle, String xpath) throws MbException {
        return getNative(handle).evaluateXPath(xpath);
    }

    /*
     * Visitable Interface
     */

    @Override public void accept(MbMessageVisitor visitor) throws MbException {
        for(PseudoNativeMbElement a : getAllocations()) {
            a.accept(visitor);
        }
    }

    public void accept(long handle, MbMessageVisitor visitor) throws MbException {
        getNative(handle).accept(visitor);
    }
}
