package com.ibm.broker.plugin;

import com.ibm.broker.trace.Trace;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;

public class MbElement {
    private static final String copyright = "Licensed Material - Property of IBM \n5648-C63 (c) Copyright IBM Corp. 2001 - All Rights Reserved. \nUS Government Users Restricted Rights - Use,duplication or disclosure \nrestricted by GSA ADP Schedule Contract with IBM Corp.";
    private long handle_;
    private boolean isReadOnly = false;
    private boolean isAttached = true;
    public static final int VALUE_STATE_UNDEFINED = 0;
    public static final int VALUE_STATE_VALID = 1;
    public static final int VALUE_STATE_INVALID = 2;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_NAME = 16777216;
    public static final int TYPE_VALUE = 33554432;
    public static final int TYPE_NAME_VALUE = 50331648;
    static final int TYPE_SPECIAL = 67108864;
    private static final int TYPE_MASK_GENERIC = 251658240;
    private static final int TYPE_MASK_SPECIFIC = 16777215;

    MbElement(long handle) {
        String str = "MbElement";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            this.handle_ = handle;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    MbElement(long handle, int paramInt) throws MbException {
        this(handle);
        String str = "MbElement";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);
            setType(paramInt);
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    MbElement(long handle, int paramInt, String paramString, Object paramObject) throws MbException {
        this(handle);
        String str = "MbElement";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);
            setType(paramInt);
            if (paramString != null) setName(paramString);
            if (paramObject != null) setValue(paramObject);
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    MbElement(long handle, boolean paramBoolean) {
        this(handle);
        String str = "MbElement";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            this.isReadOnly = paramBoolean;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    long getHandle() {
        return this.handle_;
    }

    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    boolean isAttached() {
        return this.isAttached;
    }

    void setAttached() {
        this.isAttached = true;
    }

    public MbElement createElementAfter(int paramInt) throws MbException {
        String str = "createElementAfter";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAfter(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAfter(String parserName) throws MbException {
        String str = "createElementAfter2";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAfterUsingParser(this.handle_, parserName);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAfter(int paramInt, String paramString, Object paramObject) throws MbException {
        String str = "createElementAfter3";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAfter(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt, paramString, paramObject);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementBefore(int paramInt) throws MbException {
        String str = "createElementBefore";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementBefore(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementBefore(String paramString) throws MbException {
        String str = "createElementBefore2";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementBeforeUsingParser(this.handle_, paramString);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementBefore(int paramInt, String paramString, Object paramObject) throws MbException {
        String str = "createElementBefore3";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementBefore(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt, paramString, paramObject);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsFirstChild(int paramInt) throws MbException {
        String str = "createElementAsFirstChild";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAsFirstChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsFirstChild(String paramString) throws MbException {
        String str = "createElementAsFirstChild2";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAsFirstChildUsingParser(this.handle_, paramString);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsFirstChild(int paramInt, String paramString, Object paramObject) throws MbException {
        String str = "createElementAsFirstChild3";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAsFirstChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt, paramString, paramObject);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsLastChild(int paramInt) throws MbException {
        String str = "createElementAsLastChild";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAsLastChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsLastChild(String paramString) throws MbException {
        String str = "createElementAsLastChild2";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            long l = _createElementAsLastChildUsingParser(this.handle_, paramString);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement createElementAsLastChild(int paramInt, String paramString, Object paramObject) throws MbException {
        String str = "createElementAsLastChild3";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if ((paramString != null) && ((paramObject instanceof String))) {
                long l = _createElementAsLastChildAndSet(this.handle_, paramInt, paramString, paramObject);

                if (l == 0L) {
                    return null;
                }
                return new MbElement(l);
            }
            long l = _createElementAsLastChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, paramInt, paramString, paramObject);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public String getName() throws MbException {
        String str1 = "getName";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str1);

            return _getName(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str1, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str1);
        }
    }

    public void setName(String paramString) throws MbException {
        String str = "setName";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "name[ " + paramString + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }

            if (paramString == null) {
                throw new NullPointerException();
            }

            _setName(this.handle_, paramString);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public String getNamespace() throws MbException {
        String str1 = "getNamespace";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str1);

            return _getNamespace(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str1, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str1);
        }
    }

    public void setNamespace(String paramString) throws MbException {
        String str = "setNamespace";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "namespace[ " + paramString + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }

            if (paramString == null) {
                throw new NullPointerException();
            }

            _setNamespace(this.handle_, paramString);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public int getType() throws MbException {
        String str = "getType";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            return _getType(this.handle_) & TYPE_MASK_GENERIC;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    /**
     * @deprecated
     */
    public int getParserContext() throws MbException {
        String str = "getParserContext";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            return _getType(this.handle_) & TYPE_MASK_SPECIFIC;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public int getSpecificType() throws MbException {
        String str = "getSpecificType";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            return _getType(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void setSpecificType(int paramInt) throws MbException {
        setType(paramInt);
    }

    private void setType(int paramInt) throws MbException {
        String str = "setType";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "type[ " + paramInt + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            _setType(this.handle_, paramInt);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public Object getValue() throws MbException {
        String str = "getValue";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            return _getValue(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public String getValueAsString() throws MbException {
        String str1 = "getValueAsString";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str1);

            return _getValueAsString(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str1, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str1);
        }
    }

    public void setValue(Object paramObject) throws MbException {
        String str = "setValue";
        try {
            if (Trace.isOn) {
                if (paramObject == null) {
                    Trace.logNamedEntryData(this, str, "value[ null]");
                } else {
                    Trace.logNamedEntryData(this, str, "value[ " + paramObject.toString() + "]");
                }
            }

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }

            if (paramObject == null) {
                setNullValue(this.handle_);
                return;
            }

            if ((paramObject instanceof String)) {
                setStringValue(this.handle_, (String) paramObject);
            } else if ((paramObject instanceof Integer)) {
                setIntValue(this.handle_, (Integer) paramObject);
            } else if ((paramObject instanceof Long)) {
                setLongValue(this.handle_, (Long) paramObject);
            } else if ((paramObject instanceof Double)) {
                setDoubleValue(this.handle_, (Double) paramObject);
            } else if ((paramObject instanceof BigDecimal)) {
                setDecimalValue(this.handle_, paramObject.toString());
            } else if ((paramObject instanceof Boolean)) {
                setBooleanValue(this.handle_, (Boolean) paramObject);
            } else {
                Class componentType = paramObject.getClass().getComponentType();
                if ((componentType != null) && (componentType.equals(Byte.TYPE))) {
                    byte[] o = (byte[]) paramObject;
                    byte[] arrayOfByte = new byte[o.length];
                    System.arraycopy(o, 0, arrayOfByte, 0, o.length);
                    setBytesValue(this.handle_, arrayOfByte);
                } else {
                    boolean bool;
                    if ((paramObject instanceof MbTime)) {
                        MbTime o = (MbTime) paramObject;
                        bool = "GMT".equals(o.getTimeZone().getID());
                        setMbTimeValue(this.handle_, o.get(Calendar.HOUR), o.get(Calendar.MINUTE), getMicroSeconds(o), bool);
                    } else if ((paramObject instanceof MbTimestamp)) {
                        MbTimestamp o = (MbTimestamp) paramObject;
                        bool = "GMT".equals(o.getTimeZone().getID());
                        setMbTimeStampValue(this.handle_, o.get(Calendar.YEAR), o.get(Calendar.MONTH) + 1, o.get(Calendar.DAY_OF_MONTH), o.get(Calendar.HOUR), o.get(Calendar.MINUTE), getMicroSeconds(o), bool);
                    } else if ((paramObject instanceof Duration)) {
                        Duration o = (Duration) paramObject;
                        setDurationValue(this.handle_, o.getSign(), o.getYears(), o.getMonths(), o.getDays(), o.getHours(), o.getMinutes(), o.getSeconds());
                    } else if ((paramObject instanceof MbInterval)) {
                        MbInterval o = (MbInterval) paramObject;
                        setDurationValue(this.handle_, o.getSign(), o.getYears(), o.getMonths(), o.getDays(), o.getHours(), o.getMinutes(), o.getSeconds());
                    } else if ((paramObject instanceof MbDate)) {
                        MbDate o = (MbDate) paramObject;
                        setMbDateValue(this.handle_, o.get(Calendar.YEAR), o.get(Calendar.MONTH) + 1, o.get(Calendar.DAY_OF_MONTH));
                    } else if ((paramObject instanceof BitSet)) {
                        BitSet o = (BitSet) paramObject;
                        boolean[] arrayOfBoolean = new boolean[o.length()];

                        for (int j = 0; j < o.length(); j++) {
                            arrayOfBoolean[j] = o.get(j);
                        }

                        setBitSetValue(this.handle_, arrayOfBoolean);
                    } else {
                        throw new MbRecoverableException(this, str, 3221229845L, "Invalid element value type", new String[]{paramObject.getClass().getName()});
                    }

                }

            }

        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public int getValueState() throws MbException {
        String str = "getValueState";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            return _getValueState(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public String getParserClassName() throws MbException {
        String str1 = "getParserClassName";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str1);

            return _getParserClassName(this.handle_);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str1, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str1);
        }
    }

    public MbElement getParent() throws MbException {
        String str = "getParent";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            long l = _getParent(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement getNextSibling() throws MbException {
        String str = "getNextSibling";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            long l = _getNextSibling(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement getPreviousSibling() throws MbException {
        String str = "getPreviousSibling";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            long l = _getPreviousSibling(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement getFirstChild() throws MbException {
        String str = "getFirstChild";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            long l = _getFirstChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement getLastChild() throws MbException {
        String str = "getLastChild";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            long l = _getLastChild(this.handle_);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public Object evaluateXPath(String paramString) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "evaluateXPath");

            Object o = _evaluateXPath(this.handle_, paramString);

            if ((o instanceof long[])) {
                long[] arr = (long[]) o;

                MbElement[] arrayOfMbElement = new MbElement[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    arrayOfMbElement[i] = new MbElement(arr[i], isReadOnly());
                }
                return Arrays.asList(arrayOfMbElement);
            }
            return o;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, "evaluateXPath", ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "evaluateXPath");
        }
    }

    public Object evaluateXPath(MbXPath paramMbXPath) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "evaluateXPath");

            Object o = _evaluateXPath(this.handle_, paramMbXPath.getHandle(), paramMbXPath);

            if ((o instanceof long[])) {
                long[] arr = (long[]) o;

                MbElement[] arrayOfMbElement = new MbElement[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    arrayOfMbElement[i] = new MbElement(arr[i], isReadOnly());
                }
                return Arrays.asList(arrayOfMbElement);
            }
            return o;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, "evaluateXPath", ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "evaluateXPath");
        }
    }

    public MbElement getFirstElementByPath(String paramString) throws MbException {
        String str = "getFirstElementByPath";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (paramString == null) {
                throw new NullPointerException();
            }
            long l = _getFirstElementByPath(this.handle_, paramString);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l, isReadOnly());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    /**
     * @deprecated
     */
    public MbElement[] getAllElementsByPath(String paramString) throws MbException {
        String str = "getAllElementsByPath";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (paramString == null) {
                throw new NullPointerException();
            }
            long[] arrayOfLong = _getAllElementsByPath(this.handle_, paramString);

            MbElement[] arrayOfMbElement1 = new MbElement[arrayOfLong.length];
            for (int i = 0; i < arrayOfLong.length; i++) {
                arrayOfMbElement1[i] = new MbElement(arrayOfLong[i], isReadOnly());
            }
            return arrayOfMbElement1;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void addAfter(MbElement paramMbElement) throws MbException {
        String str = "addAfter";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "element[" + paramMbElement.toString() + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (paramMbElement == null) {
                throw new NullPointerException();
            }

            _addAfter(this.handle_, paramMbElement.getHandle());
            paramMbElement.setAttached();
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void addBefore(MbElement paramMbElement) throws MbException {
        String str = "addBefore";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "element[" + paramMbElement.toString() + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (paramMbElement == null) {
                throw new NullPointerException();
            }

            _addBefore(this.handle_, paramMbElement.getHandle());
            paramMbElement.setAttached();
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void addAsFirstChild(MbElement paramMbElement) throws MbException {
        String str = "addAsFirstChild";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "element[" + paramMbElement.toString() + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (paramMbElement == null) {
                throw new NullPointerException();
            }

            _addAsFirstChild(this.handle_, paramMbElement.getHandle());
            paramMbElement.setAttached();
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void addAsLastChild(MbElement paramMbElement) throws MbException {
        String str = "addAsLastChild";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "element[" + paramMbElement.toString() + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (paramMbElement == null) {
                throw new NullPointerException();
            }

            _addAsLastChild(this.handle_, paramMbElement.getHandle());
            paramMbElement.setAttached();
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public MbElement copy() throws MbException {
        return new MbElement(_copy(this.handle_));
    }

    public void copyElementTree(MbElement paramMbElement) throws MbException {
        String str = "copyElementTree";
        try {
            if (Trace.isOn) Trace.logNamedEntryData(this, str, "element[" + paramMbElement.toString() + "]");

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (paramMbElement == null) {
                throw new NullPointerException();
            }

            _copyElementTree(this.handle_, paramMbElement.getHandle());
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public void detach() throws MbException {
        String str = "detach";
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, str);

            if (isReadOnly()) {
                throw new MbReadOnlyMessageException();
            }
            if (isAttached()) {
                _detach(this.handle_);
                this.isAttached = false;
            }
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, str, ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, str);
        }
    }

    public boolean is(MbElement paramMbElement) {
        if (paramMbElement == null) {
            throw new NullPointerException();
        }

        return this.handle_ == paramMbElement.getHandle();
    }

    public byte[] toBitstream(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "toBitStream");
            return _toBitstream(getHandle(), paramString1, paramString2, paramString3, paramInt1, paramInt2, paramInt3);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, "toBitStream", ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "toBitStream");
        }
    }

    public MbElement createElementAsLastChildFromBitstream(byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "createElementAsLastChildFromBitstream");

            if (paramArrayOfByte == null) {
                throw new NullPointerException("bitstream can't be null.");
            }

            if (paramString1 == null) {
                throw new NullPointerException("parserName can't be null.");
            }

            if (paramString2 == null) {
                paramString2 = "";
            }

            if (paramString3 == null) {
                paramString3 = "";
            }

            if (paramString4 == null) {
                paramString4 = "";
            }

            long l = _createElementAsLastChildFromBitstream(getHandle(), paramArrayOfByte, paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, paramInt3);

            if (l == 0L) {
                return null;
            }
            return new MbElement(l);
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, "createElementAsLastChildFromBitstream", ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "createElementAsLastChildFromBitstream");
        }
    }

    public String toString() {
        String str;
        try {
            int i = getType();

            str = "MbElement( type: " + Integer.toHexString(i);

            if ((i & 0x1000000) == TYPE_NAME) {
                str = str + " name: " + getName();
            }
            if ((i & 0x2000000) == TYPE_VALUE) {
                str = str + " value: " + getValue();
            }
            str = str + " )";
        } catch (Exception ex) {
            str = super.toString();
        }

        return str;
    }

    private long getMicroSeconds(Calendar paramCalendar) {
        return paramCalendar.get(Calendar.SECOND) * 1000000L + paramCalendar.get(Calendar.MILLISECOND) * 1000L;
    }

    private native long _createElementAfter(long handle) throws MbException;

    private native long _createElementAfterUsingParser(long handle, String paramString) throws MbException;

    private native long _createElementBefore(long handle) throws MbException;

    private native long _createElementBeforeUsingParser(long handle, String paramString) throws MbException;

    private native long _createElementAsFirstChild(long handle) throws MbException;

    private native long _createElementAsFirstChildUsingParser(long handle, String paramString) throws MbException;

    private native long _createElementAsLastChild(long handle) throws MbException;

    private native long _createElementAsLastChildAndSet(long handle, int paramInt, String paramString, Object paramObject) throws MbException;

    private native long _createElementAsLastChildUsingParser(long handle, String paramString) throws MbException;

    private native String _getName(long handle) throws MbException;

    private native String _getNamespace(long handle) throws MbException;

    private native void _setName(long handle, String paramString) throws MbException;

    private native void _setNamespace(long handle, String paramString) throws MbException;

    private native int _getType(long handle) throws MbException;

    private native void _setType(long handle, int paramInt) throws MbException;

    private native Object _getValue(long handle) throws MbException;

    private native String _getValueAsString(long handle) throws MbException;

    private native void setNullValue(long handle) throws MbException;

    private native void setIntValue(long handle, int paramInt) throws MbException;

    private native void setLongValue(long handle, long value) throws MbException;

    private native void setDoubleValue(long handle, double paramDouble) throws MbException;

    private native void setDecimalValue(long handle, String paramString) throws MbException;

    private native void setBooleanValue(long handle, boolean paramBoolean) throws MbException;

    private native void setBytesValue(long handle, byte[] paramArrayOfByte) throws MbException;

    private native void setStringValue(long handle, String paramString) throws MbException;

    private native void setMbTimeValue(long handle, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean) throws MbException;

    private native void setDurationValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws MbException;

    private native void setMbTimeStampValue(long handle, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, boolean paramBoolean) throws MbException;

    private native void setMbDateValue(long handle, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    private native void setBitSetValue(long handle, boolean[] paramArrayOfBoolean) throws MbException;

    private native int _getValueState(long handle) throws MbException;

    private native String _getParserClassName(long handle) throws MbException;

    private native long _getParent(long handle) throws MbException;

    private native long _getNextSibling(long handle) throws MbException;

    private native long _getPreviousSibling(long handle) throws MbException;

    private native long _getFirstChild(long handle) throws MbException;

    private native long _getLastChild(long handle) throws MbException;

    private native long _getFirstElementByPath(long handle, String paramString) throws MbException;

    private native long[] _getAllElementsByPath(long handle, String paramString) throws MbException;

    private native void _addAfter(long handle, long childHandle) throws MbException;

    private native void _addBefore(long handle, long childHandle) throws MbException;

    private native void _addAsFirstChild(long handle, long childHandle) throws MbException;

    private native void _addAsLastChild(long handle, long childHandle) throws MbException;

    private native void _copyElementTree(long handle, long childHandle) throws MbException;

    private native void _detach(long handle) throws MbException;

    private native byte[] _toBitstream(long handle, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    private native long _copy(long handle) throws MbException;

    private native long _createElementAsLastChildFromBitstream(long handle, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3) throws MbException;

    private native Object _evaluateXPath(long handle, long inputContextHandle, MbXPath xpath) throws MbException;

    private native Object _evaluateXPath(long handle, String xpath) throws MbException;

    class MbElementToStringFormater {
        private MbElement element = null;

        private int indentLevel = 0;

        MbElementToStringFormater(MbElement arg2) {
            this.element = arg2;
        }

        String getFormatedString(int paramInt) throws MbException {
            this.indentLevel = paramInt;

            return traverseDown(this.element);
        }

        String traverseDown(MbElement paramMbElement) throws MbException {
            String str = "";

            int i = paramMbElement.getType();

            if (i == TYPE_NAME) {
                str = str + indent() + "MbElement( name: " + paramMbElement.getName();
            } else if (i == TYPE_VALUE) {
                str = str + indent() + "MbElement( value: " + paramMbElement.getValue();
            } else if (i == TYPE_NAME_VALUE) {
                str = str + indent() + "MbElement( name: " + paramMbElement.getName() + ", " + paramMbElement.getValue();
            } else {
                str = str + indent() + "MbElement( Unknown";
            }

            MbElement el = paramMbElement.getFirstChild();
            if (el != null) {
                this.indentLevel += 1;
                str = str + newLine() + traverse(el) + " )";
                this.indentLevel -= 1;
            } else {
                str = str + " )";
            }

            return str;
        }

        String traverse(MbElement paramMbElement) throws MbException {
            String str = "";

            str = str + traverseDown(paramMbElement);

            MbElement el = paramMbElement.getNextSibling();
            if (el != null) {
                str = str + newLine() + traverse(el);
            }

            return str;
        }

        String indent() {
            String str = "";

            for (int i = 0; i < this.indentLevel; i++) {
                str = str + "  ";
            }
            return str;
        }

        String newLine() {
            return "\n";
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MbElement && handle_ == ((MbElement) o).handle_;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(handle_).hashCode();
    }
}