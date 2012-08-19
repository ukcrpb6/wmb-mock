package com.ibm.broker.plugin;


import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkState;

public class PseudoNativeMbMessage {

    private MbMessage mbMessage;

    private MbElement rootElement = //;

    private boolean readOnly;

    private void checkMutable() {
        checkState(!readOnly, "Message is immutable");
    }

    void setMbMessage(MbMessage mbMessage) {
        this.mbMessage = mbMessage;
    }

    MbMessage getMbMessage() {
        return this.mbMessage;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public byte[] getBuffer() throws MbException {
        throw new UnsupportedOperationException();
    }

    public void clearMessage() throws MbException {
        checkMutable();
        rootElement = new PseudoNativeMbElement();
    }

    public void finalizeMessage(int arg0) throws MbException {
        this.readOnly = true;
    }

    public MbElement getRootElement() throws MbException {
        return rootElement;
    }

    public Object evaluateXPath(MbXPath xpath) throws MbException {
        return rootElement.evaluateXPath(xpath);
    }

    public Object evaluateXPath(String xpath) throws MbException {
        return rootElement.evaluateXPath(xpath);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("read-only", readOnly).add("rootElement", rootElement).toString();
    }
    
}
