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
import com.google.common.collect.Maps;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbElementManager implements NativeMbElementManager {

    Map<Long, PseudoNativeMbElement> nativeElements = Maps.newHashMap();

    private static class InstanceHolder {
        static final PseudoNativeMbElementManager instance = new PseudoNativeMbElementManager();
    }

    public static PseudoNativeMbElementManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbElementManager() {
    }

    private long getNativeHandle(PseudoNativeMbElement element) {
        return element == null ? 0L : element.hashCode();
    }

    long[] getHandlesFromPseudoNativeMbElementList(PseudoNativeMbElement[] elements) {
        long[] arrayOfHandles = new long[elements.length];
        for (int i = 0; i < elements.length; i++) {
            arrayOfHandles[i] = getNativeHandle(elements[i]);
        }
        return arrayOfHandles;
    }

    public long _createNativeMbElement() {
        PseudoNativeMbElement e = new PseudoNativeMbElement();
        nativeElements.put(getNativeHandle(e), e);
        return getNativeHandle(e);
    }

    public MbElement createMbElement() {
        PseudoNativeMbElement e = new PseudoNativeMbElement();
        long handle = getNativeHandle(e);
        nativeElements.put(handle, e);
        return new MbElement(handle);
    }

    PseudoNativeMbElement createPseudoNativeMbElement(PseudoNativeMbMessage message) {
        PseudoNativeMbElement e = new PseudoNativeMbElement(message);
        long handle = getNativeHandle(e);
        nativeElements.put(handle, e);
        return e;
    }

    PseudoNativeMbElement createPseudoNativeMbElement() {
        PseudoNativeMbElement e = new PseudoNativeMbElement();
        long handle = getNativeHandle(e);
        nativeElements.put(handle, e);
        return e;
    }

    PseudoNativeMbElement createPseudoNativeMbElement(String parserName) {
        PseudoNativeMbElement e = new PseudoNativeMbElement(parserName);
        long handle = (long) getNativeHandle(e);
        nativeElements.put(handle, e);
        return e;
    }

    PseudoNativeMbElement createPseudoNativeMbElement(int type) {
        PseudoNativeMbElement e = new PseudoNativeMbElement(type);
        long handle = (long) getNativeHandle(e);
        nativeElements.put(handle, e);
        return e;
    }

    PseudoNativeMbElement createPseudoNativeMbElement(int type, String name, Object value) {
        PseudoNativeMbElement e = new PseudoNativeMbElement(type, name, value);
        long handle = (long) getNativeHandle(e);
        nativeElements.put(handle, e);
        return e;
    }

    PseudoNativeMbElement getNativeMbElement(MbElement element) {
        return getNativeMbElement(element.getHandle());
    }

    PseudoNativeMbElement getNativeMbElement(long handle) {
        return nativeElements.get(handle);
    }

    @Override
    public long _createElementAfter(long handle) throws MbException {
        PseudoNativeMbElement child = createPseudoNativeMbElement();
        getNativeMbElement(handle).addAfter(child);
        return getNativeHandle(child);
    }

    @Override
    public long _createElementAfterUsingParser(long handle, String paramString) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAfter(paramString));
    }

    @Override
    public long _createElementBefore(long handle) throws MbException {
        PseudoNativeMbElement newMbElement = createPseudoNativeMbElement();
        getNativeMbElement(handle).addBefore(newMbElement);
        return getNativeHandle(newMbElement);
    }

    @Override
    public long _createElementBeforeUsingParser(long handle, String paramString) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementBefore(paramString));
    }

    @Override
    public long _createElementAsFirstChild(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAsFirstChild(MbElement.TYPE_UNKNOWN));
    }

    @Override
    public long _createElementAsFirstChildUsingParser(long handle, String paramString) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAsFirstChild(paramString));
    }

    @Override
    public long _createElementAsLastChild(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAsLastChild(MbElement.TYPE_UNKNOWN));
    }

    @Override
    public long _createElementAsLastChildAndSet(long handle, int paramInt, String paramString, Object paramObject) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAsLastChild(paramInt, paramString, paramObject));
    }

    @Override
    public long _createElementAsLastChildUsingParser(long handle, String paramString) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).createElementAsLastChild(paramString));
    }

    @Override
    public String _getName(long handle) throws MbException {
        return getNativeMbElement(handle).getName();
    }

    @Override
    public String _getNamespace(long handle) throws MbException {
        return getNativeMbElement(handle).getNamespace();
    }

    @Override
    public void _setName(long handle, String paramString) throws MbException {
        getNativeMbElement(handle).setName(paramString);
    }

    @Override
    public void _setNamespace(long handle, String paramString) throws MbException {
        getNativeMbElement(handle).setNamespace(paramString);
    }

    @Override
    public int _getType(long handle) throws MbException {
        return getNativeMbElement(handle).getType();
    }

    @Override
    public void _setType(long handle, int paramInt) throws MbException {
        getNativeMbElement(handle).setSpecificType(paramInt);
    }

    @Override
    public Object _getValue(long handle) throws MbException {
        return getNativeMbElement(handle).getValue();
    }

    @Override
    public String _getValueAsString(long handle) throws MbException {
        return getNativeMbElement(handle).getValueAsString();
    }

    @Override
    public void setNullValue(long handle) throws MbException {
        getNativeMbElement(handle).setValue(null);
    }

    @Override
    public void setIntValue(long handle, int paramInt) throws MbException {
        getNativeMbElement(handle).setValue(paramInt);
    }

    @Override
    public void setLongValue(long handle, long value) throws MbException {
        getNativeMbElement(handle).setValue(value);
    }

    @Override
    public void setDoubleValue(long handle, double paramDouble) throws MbException {
        getNativeMbElement(handle).setValue(paramDouble);
    }

    @Override
    public void setDecimalValue(long handle, String paramString) throws MbException {
        getNativeMbElement(handle).setValue(new BigDecimal(paramString));
    }

    @Override
    public void setBooleanValue(long handle, boolean paramBoolean) throws MbException {
        getNativeMbElement(handle).setValue(paramBoolean);
    }

    @Override
    public void setBytesValue(long handle, byte[] paramArrayOfByte) throws MbException {
        getNativeMbElement(handle).setValue(paramArrayOfByte);
    }

    @Override
    public void setStringValue(long handle, String paramString) throws MbException {
        getNativeMbElement(handle).setValue(paramString);
    }

    @Override
    public void setMbTimeValue(long handle, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean) throws MbException {
        MbTime t = new MbTime(paramInt1, paramInt2, (int) (paramLong1 / 1000000));
        if (paramBoolean) {
            t.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        getNativeMbElement(handle).setValue(t);
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
        getNativeMbElement(handle).setValue(duration);
    }

    @Override
    public void setMbTimeStampValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, boolean paramBoolean) throws MbException {
        MbTimestamp ts = new MbTimestamp(paramInt1, paramInt2 - 1, paramInt3, paramInt4, paramInt5, (int) (paramLong1 / 1000000));
        if (paramBoolean) {
            ts.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        getNativeMbElement(handle).setValue(ts);
    }

    @Override
    public void setMbDateValue(long handle, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        getNativeMbElement(handle).setValue(new MbDate(paramInt1, paramInt2 - 1, paramInt3));
    }

    @Override
    public void setBitSetValue(long handle, boolean[] paramArrayOfBoolean) throws MbException {
        BitSet set = new BitSet();
        for (int i = 0; i < paramArrayOfBoolean.length; i++) {
            set.set(i, paramArrayOfBoolean[i]);
        }
        getNativeMbElement(handle).setValue(set);
    }

    @Override
    public int _getValueState(long handle) throws MbException {
        return getNativeMbElement(handle).getValueState();
    }

    @Override
    public String _getParserClassName(long handle) throws MbException {
        return getNativeMbElement(handle).getParserClassName();
    }

    @Override
    public long _getParent(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getParent());
    }

    @Override
    public long _getNextSibling(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getNextSibling());
    }

    @Override
    public long _getPreviousSibling(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getPreviousSibling());
    }

    @Override
    public long _getFirstChild(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getFirstChild());
    }

    @Override
    public long _getLastChild(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getLastChild());
    }

    @Override
    public long _getFirstElementByPath(long handle, String paramString) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).getFirstElementByPath(paramString));
    }

    @Override
    public long[] _getAllElementsByPath(long handle, String paramString) throws MbException {
        return getHandlesFromPseudoNativeMbElementList(getNativeMbElement(handle).getAllElementsByPath(paramString));
    }

    @Override
    public void _addAfter(long handle, long childHandle) throws MbException {
        getNativeMbElement(handle).addAfter(getNativeMbElement(childHandle));
    }

    @Override
    public void _addBefore(long handle, long childHandle) throws MbException {
        getNativeMbElement(handle).addBefore(getNativeMbElement(childHandle));
    }

    @Override
    public void _addAsFirstChild(long handle, long childHandle) throws MbException {
        getNativeMbElement(handle).addAsFirstChild(getNativeMbElement(childHandle));
    }

    @Override
    public void _addAsLastChild(long handle, long childHandle) throws MbException {
        getNativeMbElement(handle).addAsLastChild(getNativeMbElement(childHandle));
    }

    @Override
    public void _copyElementTree(long handle, long destinationHandle) throws MbException {
        getNativeMbElement(handle).copyElementTree(getNativeMbElement(destinationHandle));
    }

    @Override
    public void _detach(long handle) throws MbException {
        getNativeMbElement(handle).detach();
    }

    @Override
    public byte[] _toBitstream(long handle, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        return getNativeMbElement(handle).toBitstream(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramInt3);
    }

    @Override
    public long _copy(long handle) throws MbException {
        return getNativeHandle(getNativeMbElement(handle).copy());
    }

    @Override
    public long _createElementAsLastChildFromBitstream(long handle, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException {
//        return getNativeMbElement(handle)._createElementAsLastChildFromBitstream(paramArrayOfByte, paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, paramInt3);
        throw new UnsupportedOperationException();
    }

    @Override
    public Object _evaluateXPath(long handle, long inputContextHandle, MbXPath xpath) throws MbException {
        return getNativeMbElement(handle).evaluateXPath(xpath);
    }

    @Override
    public Object _evaluateXPath(long handle, String xpath) throws MbException {
        return getNativeMbElement(handle).evaluateXPath(xpath);
    }
}
