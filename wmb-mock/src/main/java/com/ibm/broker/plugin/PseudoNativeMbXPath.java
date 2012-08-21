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

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.saxpath.SAXPathException;

import java.util.List;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbXPath implements NativeFor<MbXPath> {

    private String xpath;

    private final long contextHandle;

    private PseudoMbMessageNavigator navigator = new PseudoMbMessageNavigator();

    private SimpleNamespaceContext namespaceContext;

    private SimpleVariableContext variableContext;

    public PseudoNativeMbXPath(String xpath, long contextHandle) {
        this.xpath = xpath;
        this.contextHandle = contextHandle;
        this.variableContext = new SimpleVariableContext();
        this.namespaceContext = new SimpleNamespaceContext();
    }

    public void setVariableValue(String name, Object value) {
        variableContext.setVariableValue(name, value);
    }

    public void removeVariableValue(String name) {
        variableContext.setVariableValue(name, null);
    }

    public void removeAllVariables() {
        variableContext = new SimpleVariableContext();
    }

    public void setDefaultNamespace(String uri) {
        namespaceContext.addNamespace("", uri);
    }

    public void removeNamespacePrefix(String prefix) {
        throw new UnsupportedOperationException();
    }

    public void addNamespacePrefix(String prefix, String uri) {
        namespaceContext.addNamespace(prefix, uri);
    }

    public void setXPath(String s) {
        this.xpath = s;
    }

    public PseudoMbMessageNavigator getNavigator() {
        return navigator;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("xpath", xpath)
                .add("namespaceContext", namespaceContext)
                .add("variableContext", variableContext)
                .add("navigator", navigator)
                .add("localContext", contextHandle).toString();
    }

    public Object evaluateXPath(PseudoNativeMbElement nativeMbElement) throws MbException {
        Object o = evaluateNativeXPath(nativeMbElement);
        if(o instanceof List) {
            //noinspection unchecked
            return Lists.transform((List<PseudoNativeMbElement>) o, new Function<PseudoNativeMbElement, MbElement>() {
                @Override public MbElement apply(PseudoNativeMbElement input) {
                    return new MbElement(input.hashCode());
                }
            });
        }
        return o;
    }

    Object evaluateNativeXPath(PseudoNativeMbElement nativeMbElement) throws MbException {
        try {
            PseudoNativeMbElement context = PseudoNativeMbElementManager.getInstance().getNative(contextHandle);
            if (context == null) {
                context = PseudoNativeMbElementManager.getInstance()
                        .getNative(nativeMbElement.getMbMessage().getRootElement().getLastChild().getHandle());
            }
            if (context == null) {
                throw new IllegalStateException("No XPath context provided and message has no children");
            }
            System.out.println("Evaluating " + xpath + " with context " + context);
            navigator.setDocumentNode(context);
            try {
                return navigator.parseXPath(xpath).selectNodes(context);
            } finally {
                navigator.clearDocumentNode();
            }
        } catch (SAXPathException e) {
            throw Throwables.propagate(e);
        }
    }

    /*
     * NativeFor Interface
     */

    @Override public MbXPath get() {
        throw new UnsupportedOperationException();
    }

    @Override public long getHandle() {
        return hashCode();
    }

    @Override public boolean isManaged() {
        return PseudoNativeMbXPathManager.getInstance().isManaged(this);
    }
}
