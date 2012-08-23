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

import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import org.jaxen.*;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.SingleObjectIterator;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoMbMessageNavigator extends DefaultNavigator {

    class FauxDocumentNode {
        PseudoNativeMbElement root;

        FauxDocumentNode(PseudoNativeMbElement root) {
            this.root = root;
        }
    }

    FauxDocumentNode documentNode;

    public void setDocumentNode(PseudoNativeMbElement root) {
        documentNode = new FauxDocumentNode(root);
    }

    public void clearDocumentNode() {
        documentNode = null;
    }

    private static PseudoNativeMbElement checkNativeMbElement(MbElement element) {
        long handle = element.getHandle();
        PseudoNativeMbElement nativeMbElement = PseudoNativeMbElementManager.getInstance().getNative(handle);
        if (nativeMbElement == null) {
            throw new NoSuchElementException("No native element found matching handle #" + handle);
        }
        return nativeMbElement;
    }

    private static MbElement checkMockMbElement(Object element) {
        if (element instanceof MbElement) {
            return (MbElement) element;
        }
        if (element instanceof PseudoNativeMbElement) {
            return ((PseudoNativeMbElement) element).get();
        }
        throw new IllegalArgumentException("Unsupported type " + element.getClass());
    }

    @Override
    public String getElementNamespaceUri(Object element) {
        try {
            return checkMockMbElement(element).getNamespace();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String getElementName(Object element) {
        try {
            return checkMockMbElement(element).getName();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String getElementQName(Object element) {
        return new QName(getElementNamespaceUri(element), getElementName(element)).toString();
    }

    @Override
    public String getAttributeNamespaceUri(Object attr) {
        return null;
    }

    @Override
    public String getAttributeName(Object attr) {
        return null;
    }

    @Override
    public String getAttributeQName(Object attr) {
        return null;
    }

    @Override
    public boolean isDocument(Object object) {
        return object instanceof MbMessage || (documentNode != null && documentNode.equals(object));
    }

    @Override
    public boolean isElement(Object object) {
        return true;
    }

    @Override
    public boolean isAttribute(Object object) {
        return false;
    }

    @Override
    public boolean isNamespace(Object object) {
        return false;
    }

    @Override
    public boolean isComment(Object object) {
        return false;
    }

    @Override
    public boolean isText(Object object) {
        return false;
    }

    @Override
    public boolean isProcessingInstruction(Object object) {
        return false;
    }

    @Override
    public String getCommentStringValue(Object comment) {
        return null;
    }

    @Override
    public String getElementStringValue(Object element) {
        try {
            return checkMockMbElement(element).getValueAsString();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String getAttributeStringValue(Object attr) {
        return null;
    }

    @Override
    public String getNamespaceStringValue(Object ns) {
        return null;
    }

    @Override
    public String getTextStringValue(Object text) {
        return null;
    }

    @Override
    public String getNamespacePrefix(Object ns) {
        return null;
    }

    @Override
    public XPath parseXPath(String xpath) throws SAXPathException {
        return new BaseXPath(xpath, this);
    }

    @Override
    public Object getDocumentNode(Object contextNode) {
        if (documentNode != null) {
            return documentNode;
        }
        if (contextNode instanceof MbMessage) {
            return contextNode;
        }
        if (contextNode instanceof MbElement) {
            PseudoNativeMbElement nativeMbElement = checkNativeMbElement(checkMockMbElement(contextNode));
            return nativeMbElement.getMbMessage();
        }
        if (contextNode instanceof PseudoNativeMbElement) {
            return ((PseudoNativeMbElement) contextNode).getMbMessage();
        }
        throw new IllegalArgumentException("Element type " + contextNode.getClass() + " not supported.");
    }

    @Override
    public Iterator getChildAxisIterator(Object contextNode) throws UnsupportedAxisException {
        if (contextNode instanceof FauxDocumentNode) {
            return getChildAxisIterator(((FauxDocumentNode) contextNode).root);
        }
        if (contextNode instanceof MbMessage) {
            try {
                return getChildAxisIterator(((MbMessage) contextNode).getRootElement());
            } catch (MbException e) {
                throw new IllegalStateException("Message node with no root element");
            }
        }
        if (contextNode instanceof MbElement) {
            return createChildAxisIterator(checkNativeMbElement(checkMockMbElement(contextNode)));
        }
        if (contextNode instanceof PseudoNativeMbElement) {
            return createChildAxisIterator((PseudoNativeMbElement) contextNode);
        }
        throw new IllegalArgumentException("Element type " + contextNode.getClass() + " not supported.");
    }

    private Iterator createChildAxisIterator(final PseudoNativeMbElement nativeMbElement) {
        return new AbstractIterator() {
            private PseudoNativeMbElement child = nativeMbElement.firstChild;

            @Override
            protected Object computeNext() {
                if (child != null) {
                    PseudoNativeMbElement result = child;
                    child = child.nextSibling;
                    return result;
                }
                return endOfData();
            }
        };
    }

    @Override
    public Iterator getParentAxisIterator(Object contextNode) throws UnsupportedAxisException {
        if (contextNode instanceof FauxDocumentNode) {
            return JaxenConstants.EMPTY_ITERATOR;
        }
        if (contextNode instanceof MbMessage) {
            return JaxenConstants.EMPTY_ITERATOR;
        }
        if (contextNode instanceof MbElement) {
            PseudoNativeMbElement nativeMbElement = checkNativeMbElement(checkMockMbElement(contextNode));
            return nativeMbElement.parent != null ? new SingleObjectIterator(nativeMbElement.parent) : null;
        }
        if (contextNode instanceof PseudoNativeMbElement) {
            return ((PseudoNativeMbElement) contextNode).parent != null ? new SingleObjectIterator(((PseudoNativeMbElement) contextNode).parent) : null;
        }
        throw new IllegalArgumentException("Element type " + contextNode.getClass() + " not supported.");
    }

    @Override
    public Iterator getAttributeAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return JaxenConstants.EMPTY_ITERATOR;
    }

    private PseudoNativeMbElement getCurrentElement(Object contextNode) {
        if (contextNode instanceof FauxDocumentNode) {
            return ((FauxDocumentNode) contextNode).root;
        }
        if (contextNode instanceof MbMessage) {
            try {
                long _handle = PseudoNativeMbMessageManager.getInstance()._getRootElement(((MbMessage) contextNode).getHandle());
                return PseudoNativeMbElementManager.getInstance().getNative(_handle);
            } catch (MbException e) {
                throw Throwables.propagate(e);
            }
        }
        if (contextNode instanceof MbElement) {
            return checkNativeMbElement((MbElement) contextNode);
        }
        if (contextNode instanceof PseudoNativeMbElement) {
            return (PseudoNativeMbElement) contextNode;
        }

        throw new IllegalArgumentException("Element type " + contextNode.getClass() + " not supported.");
    }
    @Override
    public Object createAfter(Object contextNode, String localName, String namespacePrefix, String namespaceURI) throws UnsupportedAxisException {
        try {
            PseudoNativeMbElement newElement = getCurrentElement(contextNode).createElementAfter(MbElement.TYPE_UNKNOWN);
            newElement.setName(localName);
            newElement.setNamespace(namespaceURI);
            return newElement;
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public Object createBefore(Object contextNode, String localName, String namespacePrefix, String namespaceURI) throws UnsupportedAxisException {
        try {
            PseudoNativeMbElement newElement = getCurrentElement(contextNode).createElementBefore(MbElement.TYPE_UNKNOWN);
            newElement.setName(localName);
            newElement.setNamespace(namespaceURI);
            return newElement;
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public Object createAsFirstChild(Object contextNode, String localName, String namespacePrefix, String namespaceURI) throws UnsupportedAxisException {
        try {
            PseudoNativeMbElement newElement = getCurrentElement(contextNode).createElementAsFirstChild(MbElement.TYPE_UNKNOWN);
            newElement.setName(localName);
            newElement.setNamespace(namespaceURI);
            return newElement;
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public Object createAsLastChild(Object contextNode, String localName, String namespacePrefix, String namespaceURI) throws UnsupportedAxisException {
        try {
            PseudoNativeMbElement newElement = getCurrentElement(contextNode).createElementAsLastChild(MbElement.TYPE_UNKNOWN);
            newElement.setName(localName);
            newElement.setNamespace(namespaceURI);
            return newElement;
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }
}
