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
import com.google.common.base.Preconditions;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessage extends AbstractPseudoNative<MbMessage> implements MbVisitable, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PseudoNativeMbMessage.class);

    private PseudoNativeMbElement rootElement;

    private String name = null;

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
        logger.info("Finalization of message invoked with " + noneOrValidate);
        logger.info("No mock implementation for finalization exists at present");
    }

    public PseudoNativeMbElement getRootElement() throws MbException {
        return rootElement;
    }

    public Object evaluateXPath(MbXPath xpath) throws MbException {
        PseudoNativeMbElement contextNode = rootElement.getLastChild();
        return contextNode == null ? null : contextNode.evaluateXPath(xpath);
    }

    public Object evaluateXPath(String xpath) throws MbException {
        PseudoNativeMbElement contextNode = rootElement.getLastChild();
        return contextNode == null ? null : contextNode.evaluateXPath(xpath);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", name).omitNullValues().toString();
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

    public void setRootElement(PseudoNativeMbElement rootElement) {
        this.rootElement = rootElement;
        this.rootElement.setMessage(this);
    }

    public void setName(String name) {
        this.name = Preconditions.checkNotNull(name);
    }
}
