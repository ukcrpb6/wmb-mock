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
import com.google.common.base.Throwables;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.saxpath.SAXPathException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbXPath {

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
        try {
            PseudoNativeMbElement context = PseudoNativeMbElementManager.getInstance().getNativeMbElement(contextHandle);
            if( context == null ) {
                context = PseudoNativeMbElementManager.getInstance()
                        .getNativeMbElement(nativeMbElement.getMbMessage().getRootElement().getLastChild());
            }
            if(context == null) {
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
}
