package com.ibm.broker.plugin;

import com.google.common.base.*;
import com.google.common.collect.AbstractIterator;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;

import java.util.Iterator;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbElement implements MbVisitable {

    private static final int TYPE_MASK_GENERIC = 251658240;

    private static final int TYPE_MASK_SPECIFIC = 16777215;

    PseudoNativeMbElement parent;

    PseudoNativeMbElement firstChild;

    PseudoNativeMbElement lastChild;

    PseudoNativeMbElement previousSibling;

    PseudoNativeMbElement nextSibling;

    private String name = "";

    private Object value;

    private boolean readOnly;

    private String namespace = "";

    private int type;

    private String parserClassName;

    private PseudoNativeMbMessage message;

    PseudoNativeMbElement() {
    }

    PseudoNativeMbElement(PseudoNativeMbMessage message) {
        this.message = message;
    }

    public PseudoNativeMbElement(int type) {
        this.type = type;
    }

    public PseudoNativeMbElement(String parserClassName) {
        this.parserClassName = parserClassName;
    }

    public PseudoNativeMbElement(int type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() throws MbException {
        return name;
    }

    public Object getValue() throws MbException {
        return value;
    }

    public PseudoNativeMbElement getParent() throws MbException {
        return parent;
    }

    public void setName(String arg0) throws MbException {
        this.name = arg0;
    }

    public PseudoNativeMbElement copy() throws MbException {
        return null;
    }

    public int getType() throws MbException {
        return type & TYPE_MASK_GENERIC;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setValue(Object arg0) throws MbException {
        this.value = arg0;
    }

    private static PseudoNativeMbElement checkMockMbElement(PseudoNativeMbElement element) {
        return Preconditions.checkNotNull(element);
    }

    public String getNamespace() throws MbException {
        return namespace;
    }

    public PseudoNativeMbElement createElementAfter(int arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAfter(e);
        return e;
    }

    public PseudoNativeMbElement createElementAfter(String arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setParserClassName(arg0);
        addAfter(e);
        return e;
    }

    /**
     * @param arg0 The type of syntax element to be created.
     *             This must be either a valid specific type value for the associated parser or one of the following generic types:
     *             + TYPE_NAME
     *             + TYPE_VALUE
     *             + TYPE_NAME_VALUE
     */
    public PseudoNativeMbElement createElementAfter(int arg0, String arg1, Object arg2) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAfter(e);
        return e;
    }

    public PseudoNativeMbElement createElementBefore(int arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addBefore(e);
        return e;
    }

    public PseudoNativeMbElement createElementBefore(int arg0, String arg1, Object arg2) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addBefore(e);
        return e;
    }

    public PseudoNativeMbElement createElementBefore(String arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setParserClassName(arg0);
        addBefore(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsFirstChild(int arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAsFirstChild(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsFirstChild(String arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setParserClassName(arg0);
        addAsFirstChild(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsFirstChild(int arg0, String arg1, Object arg2) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAsFirstChild(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsLastChild(int arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAsLastChild(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsLastChild(int arg0, String arg1, Object arg2) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAsLastChild(e);
        return e;
    }

    public PseudoNativeMbElement createElementAsLastChild(String arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setName(arg0);
        addAsLastChild(e);
        return e;
    }

    public void setNamespace(String arg0) throws MbException {
        this.namespace = arg0;
    }

    @Deprecated
    public int getParserContext() throws MbException {
        return getSpecificType();
    }

    public int getSpecificType() throws MbException {
        return this.type;
    }

    public void setSpecificType(int arg0) throws MbException {
        this.type = arg0;
    }

    public String getValueAsString() throws MbException {
        return String.valueOf(value);
    }

    public int getValueState() throws MbException {
        throw new UnsupportedOperationException();
    }

    public String getParserClassName() throws MbException {
        return (parserClassName == null) ? parent.getParserClassName() : parserClassName;
    }

    public PseudoNativeMbElement getNextSibling() throws MbException {
        return nextSibling; // == null ? null : nextSibling.getMbElement();
    }

    public PseudoNativeMbElement getPreviousSibling() throws MbException {
        return previousSibling; // == null ? null : previousSibling.getMbElement();
    }

    public PseudoNativeMbElement getFirstChild() throws MbException {
        return firstChild; // == null ? null : firstChild.getMbElement();
    }

    public PseudoNativeMbElement getLastChild() throws MbException {
        return lastChild; // == null ? null : lastChild.getMbElement();
    }

    public PseudoNativeMbElement getFirstElementByPath(String arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    public PseudoNativeMbElement[] getAllElementsByPath(String arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    public void addBefore(PseudoNativeMbElement element) throws MbException {
        checkValidParent();
        element.parent = this.parent;
        if (this.parent.firstChild == this) this.parent.firstChild = element;
        if (this.previousSibling != null) this.previousSibling.nextSibling = element;
        element.previousSibling = this.previousSibling;
        element.nextSibling = this;
        this.previousSibling = element;
    }

    public void addAfter(PseudoNativeMbElement element) throws MbException {
        checkValidParent();
        element.parent = this.parent;
        if (this.parent.lastChild == this) this.parent.lastChild = element;
        if (this.nextSibling != null) this.nextSibling.previousSibling = element;
        element.nextSibling = this.nextSibling;
        element.previousSibling = this;
        this.nextSibling = element;
    }

    private void checkValidParent() {
        Preconditions.checkState(this.parent != null, "Elements cannot be added as siblings of root element");
    }

    public void addAsFirstChild(PseudoNativeMbElement arg0) throws MbException {
        PseudoNativeMbElement newMbElement = checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.firstChild != null) this.firstChild.previousSibling = newMbElement;
        newMbElement.nextSibling = this.firstChild;
        this.firstChild = newMbElement;
        if (lastChild == null) lastChild = firstChild;
    }

    public void addAsLastChild(PseudoNativeMbElement arg0) throws MbException {
        PseudoNativeMbElement newMbElement = checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.lastChild != null) this.lastChild.nextSibling = newMbElement;
        newMbElement.previousSibling = this.lastChild;
        this.lastChild = newMbElement;
        if (firstChild == null) firstChild = lastChild;
    }

    public void copyElementTree(PseudoNativeMbElement arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    public void detach() throws MbException {
        previousSibling.nextSibling = nextSibling;
        nextSibling.previousSibling = previousSibling;
        if (parent.firstChild == this) parent.firstChild = this.nextSibling;
        if (parent.lastChild == this) parent.lastChild = this.previousSibling;
    }

    public byte[] toBitstream(String arg0, String arg1, String arg2, int arg3, int arg4, int arg5) throws MbException {
        throw new UnsupportedOperationException();
    }

    public PseudoNativeMbElement createElementAsLastChildFromBitstream(byte[] arg0, String arg1, String arg2, String arg3, String arg4, int arg5, int arg6, int arg7) throws MbException {
        throw new UnsupportedOperationException();
    }

    public Object evaluateXPath(MbXPath xpath) throws MbException {
        return PseudoNativeMbXPathManager.getInstance().getNativeMbXPath(xpath).evaluateXPath(this);
    }

    public Object evaluateXPath(String xpath) throws MbException {
        return PseudoNativeMbXPathManager.getInstance()
                .getNativeMbXPath(new MbXPath(xpath, new MbElement(this.hashCode()))).evaluateXPath(this);
    }

    public void setParserClassName(String parserClassName) {
        this.parserClassName = parserClassName;
        this.name = parserClassName;  // Hash lookup know values ie PropertiesParser -> Properties?
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("parent", Optional.fromNullable(parent).transform(new Function<PseudoNativeMbElement, Integer>() {
                    @Override
                    public Integer apply(PseudoNativeMbElement input) {
                        return input.hashCode();
                    }
                }).orNull())
                .add("namespace", namespace)
                .add("name", name)
                .add("parserClassName", parserClassName)
                .add("type", type)
                .add("genericType", type & TYPE_MASK_GENERIC)
                .add("specificType", type & TYPE_MASK_SPECIFIC)
                .add("value", value).omitNullValues().toString();
    }

    public PseudoNativeMbMessage getNativeMbMessage() {
        if(message == null) {
            message = Preconditions.checkNotNull(parent,
                    "Root elements should always be associated with a message").getNativeMbMessage();
        }
        return message;
    }

    public MbMessage getMbMessage() {
        // TODO: Check validity of native message - retrieve from PseudoNativeMbMessageManager
        return new MbMessage(getNativeMbMessage().hashCode());
    }

    @Override
    public void accept(MbMessageVisitor visitor) {
        visitor.visit(this);
        Iterator<PseudoNativeMbElement> iter = new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = firstChild;

            @Override
            protected PseudoNativeMbElement computeNext() {
                if (next != null) {
                    PseudoNativeMbElement current = next;
                    try {
                        next = next.getNextSibling();
                    } catch (MbException e) {
                        throw Throwables.propagate(e);
                    }
                    return current;
                }
                return endOfData();
            }
        };

        while (iter.hasNext()) {
            iter.next().accept(visitor);
        }
    }
}
