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
import com.google.common.collect.Lists;
import com.pressassociation.bus.NiceMbException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.XPath;
import org.jaxen.saxpath.SAXPathException;

import java.util.List;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbXPath extends AbstractPseudoNative<MbXPath> {

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
                    return new MbElement(input.getHandle());
                }
            });
        }
        return o;
    }

    Object evaluateNativeXPath(PseudoNativeMbElement nativeMbElement) throws MbException {
        try {
// TODO: what does contextHandle do, how does it effect the XPath engine in IBM implementation
//            PseudoNativeMbElement context = PseudoNativeMbElementManager.getInstance().getNative(contextHandle);
//            if (context == null) {
//                context = nativeMbElement.getNativeMbMessage().getRootElement().getLastChild();
//            }
//            if (context == null) {
//                // TODO: should this return an empty list or is null correct?
//                return null;
//            }
//            navigator.setDocumentNode(context);
            try {
                XPath xp = navigator.parseXPath(xpath);
                xp.setNamespaceContext(namespaceContext);
                xp.setVariableContext(variableContext);
                return xp.selectNodes(nativeMbElement);
            } finally {
                navigator.clearDocumentNode();
            }
        } catch (SAXPathException e) {
            throw NiceMbException.propagate(e);
        }
    }

    /*
     * NativeFor Interface
     */

    @Override public MbXPath get() {
        throw new UnsupportedOperationException();
    }

    @Override public boolean isManaged() {
        return PseudoNativeMbXPathManager.getInstance().isManaged(this);
    }
}
