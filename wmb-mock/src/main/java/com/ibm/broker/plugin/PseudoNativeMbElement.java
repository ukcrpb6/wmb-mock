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

import com.google.common.base.*;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbElement extends AbstractPseudoNative<MbElement> implements MbVisitable {

    private static final int TYPE_MASK_GENERIC = 251658240;

    private static final int TYPE_MASK_SPECIFIC = 16777215;

    PseudoNativeMbElement parent;

    PseudoNativeMbElement firstChild;

    PseudoNativeMbElement lastChild;

    PseudoNativeMbElement previousSibling;

    PseudoNativeMbElement nextSibling;

    private String path = null;

    private String name = "";

    private Object value;

    private String namespace = "";

    private int type;

    private String parserClassName;

    private PseudoNativeMbMessage message;

    PseudoNativeMbElement() {
    }

    PseudoNativeMbElement(PseudoNativeMbElement element) {
        this.name = element.name;
        this.value = element.value;
        this.type = element.type;
        this.parserClassName = element.parserClassName;
    }

    void setMessage(PseudoNativeMbMessage message) {
        this.message = message;
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

//    /**
//     * @param arg0 The type of syntax element to be created.
//     *             This must be either a valid specific type value for the associated parser or one of the following generic types:
//     *             + TYPE_NAME
//     *             + TYPE_VALUE
//     *             + TYPE_NAME_VALUE
//     */
//    public PseudoNativeMbElement createElementAfter(int arg0, String arg1, Object arg2) throws MbException {
//        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
//        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
//        mockMbElement.type = arg0;
//        mockMbElement.setName(arg1);
//        mockMbElement.setValue(arg2);
//        addAfter(e);
//        return e;
//    }

    public PseudoNativeMbElement createElementBefore(int arg0) throws MbException {
        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
        mockMbElement.type = arg0;
        addBefore(e);
        return e;
    }

//    public PseudoNativeMbElement createElementBefore(int arg0, String arg1, Object arg2) throws MbException {
//        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
//        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
//        mockMbElement.type = arg0;
//        mockMbElement.setName(arg1);
//        mockMbElement.setValue(arg2);
//        addBefore(e);
//        return e;
//    }

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

//    public PseudoNativeMbElement createElementAsFirstChild(int arg0, String arg1, Object arg2) throws MbException {
//        PseudoNativeMbElement e = PseudoNativeMbElementManager.getInstance().createPseudoNativeMbElement();
//        PseudoNativeMbElement mockMbElement = checkMockMbElement(e);
//        mockMbElement.type = arg0;
//        mockMbElement.setName(arg1);
//        mockMbElement.setValue(arg2);
//        addAsFirstChild(e);
//        return e;
//    }

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
        return nextSibling;
    }

    public PseudoNativeMbElement getPreviousSibling() throws MbException {
        return previousSibling;
    }

    public PseudoNativeMbElement getFirstChild() throws MbException {
        return firstChild;
    }

    public PseudoNativeMbElement getLastChild() throws MbException {
        return lastChild;
    }

    private <T> List<T> checkListType(Object o, Class<T> klass) {
        if (o instanceof List) {
            return ImmutableList.copyOf(Iterables.filter((List) o, klass));
        }
        throw new IllegalArgumentException("Expected a List<" + klass.getSimpleName() + "> but got " + o.getClass());
    }

    public Iterator<PseudoNativeMbElement> followingSiblingIterator() {
        return followingSiblingIterator(false);
    }

    public Iterator<PseudoNativeMbElement> followingSiblingIterator(final boolean inclusive) {
        return new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = inclusive ? PseudoNativeMbElement.this : nextSibling;

            @Override protected PseudoNativeMbElement computeNext() {
                if (next != null) {
                    PseudoNativeMbElement current = next;
                    next = next.nextSibling;
                    return current;
                }
                return endOfData();
            }
        };
    }
    public Iterator<PseudoNativeMbElement> precedingSiblingIterator() {
        return precedingSiblingIterator(false);
    }

    public Iterator<PseudoNativeMbElement> precedingSiblingIterator(boolean inclusive) {
        final PseudoNativeMbElement stopCondition = inclusive ? nextSibling : this;
        return new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = parent.firstChild;

            @Override protected PseudoNativeMbElement computeNext() {
                if (next != null && next != stopCondition) {
                    PseudoNativeMbElement current = next;
                    next = next.nextSibling;
                    return current;
                }
                return endOfData();
            }
        };
    }

    public Iterator<PseudoNativeMbElement> childIterator() {
        return new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = firstChild;

            @Override protected PseudoNativeMbElement computeNext() {
                if (next != null) {
                    PseudoNativeMbElement current = next;
                    next = next.nextSibling;
                    return current;
                }
                return endOfData();
            }
        };
    }

    private PseudoNativeMbElement findChildByName(final String childName) {
        return Iterators.find(childIterator(), new Predicate<PseudoNativeMbElement>() {
            @Override public boolean apply(PseudoNativeMbElement input) {
                try {
                    return input.getName().equals(childName);
                } catch (MbException e) {
                    throw Throwables.propagate(e);
                }
            }
        }, null);
    }

    public String getPath() {
        if (path == null) {
            path = cachePath();
        }
        return path;
    }

    private String cachePath() {
        if (parent == null) {
            return name == null ? "/" : "/" + name;
        }
        return parent.getPath() + "/" + name;
    }

    public PseudoNativeMbElement getFirstElementByPath(String arg0) throws MbException {
        final String path = getPath();
        if (arg0.startsWith(path)) {
            if (arg0.length() > path.length()) {
                arg0 = arg0.substring(path.length(), arg0.length());
            } else {
                arg0 = arg0.substring(path.length(), arg0.length() - 1);
            }
        }
        return getFirstElementByPath(Preconditions.checkNotNull(arg0).split("/"));
    }

    private PseudoNativeMbElement getFirstElementByPath(String[] pathSegments) throws MbException {
        if ("".equals(pathSegments[0])) {
            return getNativeMbMessage().getRootElement()
                    .getFirstElementByPath(Arrays.copyOfRange(pathSegments, 1, pathSegments.length));
        }

        PseudoNativeMbElement child = findChildByName(pathSegments[0]);
        if (pathSegments.length > 1) {
            return child.getFirstElementByPath(Arrays.copyOfRange(pathSegments, 1, pathSegments.length));
        }
        return child;

    }

    public PseudoNativeMbElement[] getAllElementsByPath(String arg0) throws MbException {
        System.out.println("WARNING: Uses XPath behaviour to evaluate may not be entirely accurate");
        List<PseudoNativeMbElement> children = checkListType(evaluateNativeXPath(arg0), PseudoNativeMbElement.class);
        return children.toArray(new PseudoNativeMbElement[children.size()]);
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

    // TODO: Handle copying of element
    public void copyElementTree(PseudoNativeMbElement arg0) throws MbException {
        throw new UnsupportedOperationException();
    }

    public void detach() throws MbException {
        previousSibling.nextSibling = nextSibling;
        nextSibling.previousSibling = previousSibling;
        if (parent.firstChild == this) parent.firstChild = this.nextSibling;
        if (parent.lastChild == this) parent.lastChild = this.previousSibling;
        this.path = null;
        this.parent = null;
        this.message = null;
    }

    // TODO: Handle bitstream
    public byte[] toBitstream(String arg0, String arg1, String arg2, int arg3, int arg4, int arg5) throws MbException {
        throw new UnsupportedOperationException();
    }

    // TODO: Handle bitstream
    public PseudoNativeMbElement createElementAsLastChildFromBitstream(byte[] arg0, String arg1, String arg2, String arg3, String arg4, int arg5, int arg6, int arg7) throws MbException {
        throw new UnsupportedOperationException();
    }

    public Object evaluateXPath(MbXPath xpath) throws MbException {
        return PseudoNativeMbXPathManager.getInstance().getNative(xpath).evaluateXPath(this);
    }

    public Object evaluateNativeXPath(String xpath) throws MbException {
        return PseudoNativeMbXPathManager.getInstance()
                .getNative(new MbXPath(xpath, new MbElement(this.getHandle()))).evaluateNativeXPath(this);
    }

    public Object evaluateXPath(String xpath) throws MbException {
        return PseudoNativeMbXPathManager.getInstance()
                .getNative(new MbXPath(xpath, new MbElement(this.getHandle()))).evaluateXPath(this);
    }

    public void setParserClassName(String parserClassName) {
        this.parserClassName = parserClassName;
        this.name = parserClassName;  // TODO Hash lookup know values ie PropertiesParser -> Properties?
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("parent", Optional.fromNullable(parent).transform(new Function<PseudoNativeMbElement, Long>() {
                    @Override
                    public Long apply(PseudoNativeMbElement input) {
                        return input.getHandle();
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
        if (message == null) {
            if (parent == null) {
                return null; // Detached message
            }
            message = parent.getNativeMbMessage();
        }
        return message;
    }

    public MbMessage getMbMessage() {
        PseudoNativeMbMessage message = getNativeMbMessage();
        return message != null && message.isManaged() ? new MbMessage(message.getHandle()) : null;
    }

    /*
     * Visitable Interface
     */

    @Override
    public void accept(MbMessageVisitor visitor) {
        visitor.visit(this);
        Iterator<PseudoNativeMbElement> iter = childIterator();
        while (iter.hasNext()) {
            iter.next().accept(visitor);
        }
    }

    /*
     * NativeFor Interface
     */

    @Override public MbElement get() {
        return new MbElement(this.getHandle());
    }

    @Override public boolean isManaged() {
        return PseudoNativeMbElementManager.getInstance().isManaged(this);
    }
}
