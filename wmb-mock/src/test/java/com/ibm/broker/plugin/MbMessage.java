package com.ibm.broker.plugin;

import com.ibm.broker.trace.Trace;

import java.util.Arrays;
import java.util.List;

public class MbMessage {
    private static final String copyright = "Licensed Material - Property of IBM \n5648-C63 (c) Copyright IBM Corp. 2001 - All Rights Reserved. \nUS Government Users Restricted Rights - Use,duplication or disclosure \nrestricted by GSA ADP Schedule Contract with IBM Corp.";
    private long handle_;
    private long inputContextHandle_;
    private boolean readOnly = false;
    private boolean mustFinalize = false;
    private boolean cleared = false;
    public static final int FINALIZE_NONE = 0;
    public static final int FINALIZE_VALIDATE = 1;

    MbMessage(long handle) {
        if (Trace.isOn) Trace.logNamedEntryData(this, "MbMessage", "handle[" + handle + "]");
        this.handle_ = handle;
        if (Trace.isOn) Trace.logNamedExit(this, "MbMessage");
    }

    MbMessage(long handle, int paramInt) {
        if (Trace.isOn) Trace.logNamedEntryData(this, "MbMessage", "handle[" + handle + "] + 1");
        this.handle_ = handle;
        this.mustFinalize = true;
        if (Trace.isOn) Trace.logNamedExit(this, "MbMessage");
    }

    MbMessage(long handle, boolean readOnly) {
        this(handle);

        if (Trace.isOn)
            Trace.logNamedEntryData(this, "MbMessage", "handle[" + handle + "]" + "isReadOnly[" + readOnly + "]");

        this.readOnly = readOnly;

        if (Trace.isOn) Trace.logNamedExit(this, "MbMessage");
    }

    public MbMessage() throws MbException {
        if (Trace.isOn) Trace.logNamedEntry(this, "MbMessage");

        long[] arrayOfLong = new long[2];

        _createMessage(arrayOfLong);

        this.handle_ = arrayOfLong[0];
        this.inputContextHandle_ = arrayOfLong[1];
        this.mustFinalize = true;

        if (Trace.isOn) Trace.logNamedExit(this, "MbMessage");
    }

    public MbMessage(MbMessage paramMbMessage) throws MbException {
        this();
        copy(paramMbMessage);
    }

    long getHandle() {
        return this.handle_;
    }

    void setHandle(long handle) {
        this.handle_ = handle;
    }

    void setMustFinalize(boolean paramBoolean) {
        this.mustFinalize = paramBoolean;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    boolean checkNotCleared(String methodName) throws MbRecoverableException {
        if (this.handle_ == 0L) {
            throw new MbRecoverableException(this, methodName, 3221229850L, "Message has been cleared.", new String[]{getClass().getName()});
        }
        return true;
    }

    boolean checkWritable() throws MbReadOnlyMessageException {
        if (isReadOnly()) {
            throw new MbReadOnlyMessageException();
        }
        return true;
    }

    List<MbElement> createMbElementList(long[] handles) {
        MbElement[] arrayOfMbElement = new MbElement[handles.length];
        for (int i = 0; i < handles.length; i++) {
            arrayOfMbElement[i] = new MbElement(handles[i], isReadOnly());
        }
        return Arrays.asList(arrayOfMbElement);
    }

    public void clearMessage() throws MbException {
        if (Trace.isOn) Trace.logNamedEntry(this, "clearMessage");

        checkWritable();
        if ((this.handle_ != 0L) && (this.mustFinalize)) {
            _clearMessage(this.handle_, this.inputContextHandle_);
            this.handle_ = 0L;
            this.mustFinalize = false;
            this.inputContextHandle_ = 0L;
            this.cleared = true;
        }

        if (Trace.isOn) Trace.logNamedExit(this, "clearMessage");
    }

    public void finalizeMessage(int noneOrValidate) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "finalizeMessage");

            checkNotCleared("finalizeMessage");
            checkWritable();

            _finalizeMessage(this.handle_, noneOrValidate);
        } catch (MbException localMbException) {
            if (Trace.isOn) Trace.logStackTrace(this, "finalizeMessage", localMbException);
            throw localMbException;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "finalizeMessage");
        }
    }

    public byte[] getBuffer() throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "getBuffer");

            checkNotCleared("getBuffer");

            return _getBuffer(this.handle_);
        } catch (MbException localMbException) {
            if (Trace.isOn) Trace.logStackTrace(this, "getBuffer", localMbException);
            throw localMbException;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "getBuffer");
        }
    }

    private void writeBuffer() throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "writeBuffer");

            checkNotCleared("writeBuffer");

            _writeBuffer(this.handle_);
        } catch (MbException localMbException) {
            if (Trace.isOn) Trace.logStackTrace(this, "writeBuffer", localMbException);
            throw localMbException;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "writeBuffer");
        }
    }

    public MbElement getRootElement() throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "getRootElement");

            checkNotCleared("getRootElement");

            return new MbElement(_getRootElement(this.handle_), isReadOnly());
        } catch (MbException localMbException) {
            if (Trace.isOn) Trace.logStackTrace(this, "getRootElement", localMbException);
            throw localMbException;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "getRootElement");
        }
    }

    void copy(MbMessage paramMbMessage) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "copy");

            checkNotCleared("copy");
            checkWritable();

            if (paramMbMessage == null) {
                throw new NullPointerException("copy: sourceMessage is null");
            }
            _copy(this.handle_, paramMbMessage.getHandle());
        } catch (MbException localMbException) {
            if (Trace.isOn) Trace.logStackTrace(this, "copy", localMbException);
            throw localMbException;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "copy");
        }
    }

    public Object evaluateXPath(String paramString) throws MbException {
        try {
            if (Trace.isOn) Trace.logNamedEntry(this, "evaluateXPath");

            Object result = _evaluateXPath(this.handle_, paramString);

            if ((result instanceof long[])) {
                return createMbElementList((long[]) result);
            }
            return result;
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

            Object result = _evaluateXPath(this.handle_, paramMbXPath.getHandle(), paramMbXPath);

            if ((result instanceof long[])) {
                return createMbElementList((long[]) result);
            }
            return result;
        } catch (MbException ex) {
            if (Trace.isOn) Trace.logStackTrace(this, "evaluateXPath", ex);
            throw ex;
        } finally {
            if (Trace.isOn) Trace.logNamedExit(this, "evaluateXPath");
        }
    }

    void setInputEvaluationContextHandle(long handle) {
        this.inputContextHandle_ = handle;
    }

    long getInputEvaluationContextHandle() {
        return this.inputContextHandle_;
    }

    boolean hasBeenCleared() {
        return this.cleared;
    }

    public String toString() {
        return toString(0);
    }

    String toString(int paramInt) {
        return indent(paramInt) + super.toString();
    }

    private String indent(int paramInt) {
        String str = "";

        for (int i = 0; i < paramInt; i++) {
            str = str + "  ";
        }
        return str;
    }

    private native long _createMessage(long handle) throws MbException;

    private native void _createMessage(long[] paramArrayOfLong) throws MbException;

    private native void _clearMessage(long handle, long inputContextHandle) throws MbException;

    private native void _finalizeMessage(long handle, int noneOrValidate) throws MbException;

    private native byte[] _getBuffer(long handle) throws MbException;

    private native void _writeBuffer(long handle) throws MbException;

    private native long _getRootElement(long handle) throws MbException;

    private native void _copy(long handle, long inputContextHandle) throws MbException;

    private native Object _evaluateXPath(long handle, long inputContextHandle, MbXPath paramMbXPath) throws MbException;

    private native Object _evaluateXPath(long handle, String paramString) throws MbException;

    @Override
    public boolean equals(Object o) {
        return o instanceof MbMessage && handle_ == ((MbMessage) o).handle_;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(handle_).hashCode();
    }
}