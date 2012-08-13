package com.googlecode.wmbutil;

import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import org.jaxen.*;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.SingleObjectIterator;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MockMbNavigator extends DefaultNavigator {

    private static MockMbElement checkMockMbElement(Object element) {
        try {
            if (element instanceof MockMbElement) {
                return (MockMbElement) element;
            } else if (element instanceof MbMockFactory.IMockMbElement) {
                return ((MbMockFactory.IMockMbElement) element).getMockMbElement();
            } else if (element instanceof MockMbMessage) {
                element = ((MockMbMessage) element).getRootElement();
                return ((MbMockFactory.IMockMbElement) element).getMockMbElement();
            } else if (element instanceof MbMockFactory.IMockMbMessage) {
                element = ((MbMockFactory.IMockMbMessage) element).getMockMbMessage().getRootElement();
                return ((MbMockFactory.IMockMbElement) element).getMockMbElement();
            }
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
        throw new IllegalArgumentException("Unsupported type " + element.getClass());
    }

    @Override public String getElementNamespaceUri(Object element) {
        try {
            return checkMockMbElement(element).getNamespace();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override public String getElementName(Object element) {
        try {
            return checkMockMbElement(element).getName();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override public String getElementQName(Object element) {
        return new QName(getElementNamespaceUri(element), getElementName(element)).toString();
    }

    @Override public String getAttributeNamespaceUri(Object attr) {
        return null;
    }

    @Override public String getAttributeName(Object attr) {
        return null;
    }

    @Override public String getAttributeQName(Object attr) {
        return null;
    }

    @Override public boolean isDocument(Object object) {
        return object instanceof MbMessage;
    }

    @Override public boolean isElement(Object object) {
        return true;
    }

    @Override public boolean isAttribute(Object object) {
        return false;
    }

    @Override public boolean isNamespace(Object object) {
        return false;
    }

    @Override public boolean isComment(Object object) {
        return false;
    }

    @Override public boolean isText(Object object) {
        return false;
    }

    @Override public boolean isProcessingInstruction(Object object) {
        return false;
    }

    @Override public String getCommentStringValue(Object comment) {
        return null;
    }

    @Override public String getElementStringValue(Object element) {
        try {
            return checkMockMbElement(element).getValueAsString();
        } catch (MbException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override public String getAttributeStringValue(Object attr) {
        return null;
    }

    @Override public String getNamespaceStringValue(Object ns) {
        return null;
    }

    @Override public String getTextStringValue(Object text) {
        return null;
    }

    @Override public String getNamespacePrefix(Object ns) {
        return null;
    }

    @Override public XPath parseXPath(String xpath) throws SAXPathException {
        return new BaseXPath(xpath, this);
    }

    @Override public Object getDocumentNode(Object contextNode) {
        return contextNode instanceof MbMessage ? contextNode : checkMockMbElement(contextNode).getMockMessage();
    }

    @Override public Iterator getChildAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new AbstractIterator() {
            MockMbElement child = checkMockMbElement(contextNode).firstChild;

            @Override protected Object computeNext() {
                if (child != null) {
                    MockMbElement result = child;
                    child = child.nextSibling;
                    return result;
                }
                return endOfData();
            }
        };
    }

    @Override public Iterator getParentAxisIterator(Object contextNode) throws UnsupportedAxisException {
        MockMbElement node = checkMockMbElement(contextNode);
        return node.parent != null ? new SingleObjectIterator(node.parent) : null;
    }

    @Override public Iterator getAttributeAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return JaxenConstants.EMPTY_ITERATOR;
    }

}
