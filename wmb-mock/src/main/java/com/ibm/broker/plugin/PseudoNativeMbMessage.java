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

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessage extends AbstractPseudoNative<MbMessage> implements MbVisitable, Cloneable {

    private PseudoNativeMbElement rootElement;

    public PseudoNativeMbMessage() {
        this.rootElement = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement(this);
    }

    public byte[] getBuffer() throws MbException {
        throw new UnsupportedOperationException();
    }

    public void clearMessage() throws MbException {
        rootElement = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement(this);
    }

    public void finalizeMessage(int noneOrValidate) throws MbException {
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
        return Objects.toStringHelper(this).toString();
    }

    @Override
    public void accept(MbMessageVisitor visitor) throws MbException {
        visitor.visit(this);
        this.getRootElement().accept(visitor);
    }

    @Override public MbMessage get() {
        return new MbMessage(this.getHandle());
    }

    @Override public boolean isManaged() {
        return PseudoNativeMbMessageManager.getInstance().isManaged(this);
    }

    @Override public Object clone() throws CloneNotSupportedException {
        PseudoNativeMbMessage clone = (PseudoNativeMbMessage) super.clone();
        clone.rootElement = this.rootElement.clone();
        clone.rootElement.setMessage(this);
        return clone;
    }

    public void setRootElement(PseudoNativeMbElement rootElement) {
        this.rootElement = rootElement;
        this.rootElement.setMessage(this);
    }
}
