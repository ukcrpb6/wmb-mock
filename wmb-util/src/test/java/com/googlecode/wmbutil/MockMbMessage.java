package com.googlecode.wmbutil;

import com.google.common.base.Objects;
import com.ibm.broker.plugin.*;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MockMbMessage implements IMbMessage {

    private MbMessage mbMessage;

    private MbElement rootElement = MbMockFactory.getInstance().createMbElement(this);

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

    @Override public boolean isReadOnly() {
        return readOnly;
    }

    @Override public byte[] getBuffer() throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public void clearMessage() throws MbException {
        checkMutable();
        rootElement = MbMockFactory.getInstance().createMbElement();
    }

    @Override public void finalizeMessage(int arg0) throws MbException {
        this.readOnly = true;
    }

    @Override public MbElement getRootElement() throws MbException {
        return rootElement;
    }

    @Override public Object evaluateXPath(MbXPath xpath) throws MbException {
        return rootElement.evaluateXPath(xpath);
    }

    @Override public Object evaluateXPath(String xpath) throws MbException {
        return rootElement.evaluateXPath(xpath);
    }

    @Override public String toString() {
        return Objects.toStringHelper(this).add("read-only", readOnly).add("rootElement", rootElement).toString();
    }
}
