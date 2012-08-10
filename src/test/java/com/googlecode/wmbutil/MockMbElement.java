package com.googlecode.wmbutil;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.ibm.broker.plugin.IMbElement;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXPath;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MockMbElement implements IMbElement {

    private static final int TYPE_MASK_GENERIC = 251658240;

    private static final int TYPE_MASK_SPECIFIC = 16777215;

    private MockMbMessage message;

    MockMbElement parent;

    MockMbElement firstChild;

    MockMbElement lastChild;

    MockMbElement previousSibling;

    MockMbElement nextSibling;

    private String name;

    private Object value;

    private boolean readOnly;

    // Proxy
    private MbElement mbElement;

    private String namespace;

    private int type;

    private String parserClassName;

    MockMbElement() {
    }

    @Override public String getName() throws MbException {
        return name;
    }

    @Override public Object getValue() throws MbException {
        return value;
    }

    @Override public MbElement getParent() throws MbException {
        return parent.getMbElement();
    }

    public MockMbElement getMockParent() {
        return parent;
    }

    public MockMbMessage getMockMessage() {
        return message == null ? ((parent != null) ? parent.getMockMessage() : null) : message;
    }

    public void setMockMessage(MockMbMessage message) {
        this.message = message;
    }

    @Override public void setName(String arg0) throws MbException {
        this.name = arg0;
    }

    @Override public MbElement copy() throws MbException {
        return null;
    }

    @Override public int getType() throws MbException {
        return type & TYPE_MASK_GENERIC;
    }

    @Override public boolean isReadOnly() {
        return readOnly;
    }

    @Override public void setValue(Object arg0) throws MbException {
        this.value = arg0;
    }

    private static MockMbElement checkMockMbElement(MbElement element) {
        return ((MbMockFactory.IMockMbElement) element).getMockMbElement();
    }

    @Override public boolean is(MbElement arg0) {
        return this.equals(checkMockMbElement(arg0));
    }

    @Override public String getNamespace() throws MbException {
        return namespace;
    }

    @Override public MbElement createElementAfter(int arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAfter(e);
        return e;
    }

    @Override public MbElement createElementAfter(String arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
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
    @Override public MbElement createElementAfter(int arg0, String arg1, Object arg2) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAfter(e);
        return e;
    }

    @Override public MbElement createElementBefore(int arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addBefore(e);
        return e;
    }

    @Override public MbElement createElementBefore(int arg0, String arg1, Object arg2) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addBefore(e);
        return e;
    }

    @Override public MbElement createElementBefore(String arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setParserClassName(arg0);
        addBefore(e);
        return e;
    }

    @Override public MbElement createElementAsFirstChild(int arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAsFirstChild(e);
        return e;
    }

    @Override public MbElement createElementAsFirstChild(String arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setParserClassName(arg0);
        addAsFirstChild(e);
        return e;
    }

    @Override public MbElement createElementAsFirstChild(int arg0, String arg1, Object arg2) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAsFirstChild(e);
        return e;
    }

    @Override public MbElement createElementAsLastChild(int arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addAsLastChild(e);
        return e;
    }

    @Override public MbElement createElementAsLastChild(int arg0, String arg1, Object arg2) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        mockMbElement.setName(arg1);
        mockMbElement.setValue(arg2);
        addAsLastChild(e);
        return e;
    }

    @Override public MbElement createElementAsLastChild(String arg0) throws MbException {
        MbElement e = MbMockFactory.getInstance().createMbElement();
        MockMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.setName(arg0);
        addAsLastChild(e);
        return e;
    }

    @Override public void setNamespace(String arg0) throws MbException {
        this.namespace = arg0;
    }

    @Deprecated
    @Override public int getParserContext() throws MbException {
        return getSpecificType();
    }

    @Override public int getSpecificType() throws MbException {
        return this.type;
    }

    @Override public void setSpecificType(int arg0) throws MbException {
        this.type = arg0;
    }

    @Override public String getValueAsString() throws MbException {
        return String.valueOf(value);
    }

    @Override public int getValueState() throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public String getParserClassName() throws MbException {
        return (parserClassName == null) ? parent.getParserClassName() : parserClassName;
    }

    @Override public MbElement getNextSibling() throws MbException {
        return nextSibling == null ? null : nextSibling.getMbElement();
    }

    @Override public MbElement getPreviousSibling() throws MbException {
        return previousSibling == null ? null : previousSibling.getMbElement();
    }

    @Override public MbElement getFirstChild() throws MbException {
        return firstChild == null ? null : firstChild.getMbElement();
    }

    @Override public MbElement getLastChild() throws MbException {
        return lastChild == null ? null : lastChild.getMbElement();
    }

    @Override public MbElement getFirstElementByPath(String arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public MbElement[] getAllElementsByPath(String arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public void addBefore(MbElement element) throws MbException {
        MockMbElement newMbElement = checkMockMbElement(element);
        newMbElement.parent = this.parent;
        if (this.parent.firstChild == this) this.parent.firstChild = newMbElement;
        if (this.previousSibling != null) this.previousSibling.nextSibling = newMbElement;
        newMbElement.previousSibling = this.previousSibling;
        newMbElement.nextSibling = this;
        this.previousSibling = newMbElement;
    }

    @Override public void addAfter(MbElement element) throws MbException {
        MockMbElement newMbElement = checkMockMbElement(element);
        newMbElement.parent = this.parent;
        if (this.parent.lastChild == this) this.parent.lastChild = newMbElement;
        if (this.nextSibling != null) this.nextSibling.previousSibling = newMbElement;
        newMbElement.nextSibling = this.nextSibling;
        newMbElement.previousSibling = this;
        this.nextSibling = newMbElement;
    }

    @Override public void addAsFirstChild(MbElement arg0) throws MbException {
        MockMbElement newMbElement = checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.firstChild != null) this.firstChild.previousSibling = newMbElement;
        newMbElement.nextSibling = this.firstChild;
        this.firstChild = newMbElement;
        if (lastChild == null) lastChild = firstChild;
    }

    @Override public void addAsLastChild(MbElement arg0) throws MbException {
        MockMbElement newMbElement = checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.lastChild != null) this.lastChild.nextSibling = newMbElement;
        newMbElement.previousSibling = this.lastChild;
        this.lastChild = newMbElement;
        if (firstChild == null) firstChild = lastChild;
    }

    @Override public void copyElementTree(MbElement arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public void detach() throws MbException {
        previousSibling.nextSibling = nextSibling;
        nextSibling.previousSibling = previousSibling;
        if (parent.firstChild == this) parent.firstChild = this.nextSibling;
        if (parent.lastChild == this) parent.lastChild = this.previousSibling;
    }

    @Override
    public byte[] toBitstream(String arg0, String arg1, String arg2, int arg3, int arg4, int arg5) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public MbElement createElementAsLastChildFromBitstream(byte[] arg0, String arg1, String arg2, String arg3, String arg4, int arg5, int arg6, int arg7) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public Object evaluateXPath(MbXPath arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override public Object evaluateXPath(String arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    public void setMbElement(MbElement mbElement) {
        this.mbElement = mbElement;
    }

    public MbElement getMbElement() {
        return mbElement;
    }

    public void setParserClassName(String parserClassName) {
        this.parserClassName = parserClassName;
        this.name = parserClassName;  // Hash lookup know values ie PropertiesParser -> Properties?
    }

    @Override public String toString() {
        return Objects.toStringHelper(this)
                .add("parent", Optional.fromNullable(parent).transform(new Function<MockMbElement, Integer>() {
                    @Override public Integer apply(MockMbElement input) {
                        return input.getMbElement().hashCode();
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
}
