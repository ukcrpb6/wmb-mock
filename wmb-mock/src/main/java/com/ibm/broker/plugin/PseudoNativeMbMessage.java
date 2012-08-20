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


import com.google.common.base.Objects;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessage implements MbVisitable {

    private PseudoNativeMbElement rootElement;

    private boolean readOnly;

    public PseudoNativeMbMessage() {
        this.rootElement = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement(this);
    }

    private void checkMutable() {
        checkState(!readOnly, "Message is immutable");
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public byte[] getBuffer() throws MbException {
        throw new UnsupportedOperationException();
    }

    public void clearMessage() throws MbException {
        checkMutable();
        rootElement = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement(this);
    }

    public void finalizeMessage(int noneOrValidate) throws MbException {
        this.readOnly = true;
    }

    public PseudoNativeMbElement getRootElement() throws MbException {
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

    @Override
    public void accept(MbMessageVisitor visitor) throws MbException {
        visitor.visit(this);
        this.getRootElement().accept(visitor);
    }
}
