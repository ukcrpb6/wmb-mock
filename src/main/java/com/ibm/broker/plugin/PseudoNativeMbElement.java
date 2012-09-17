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
import com.google.common.collect.*;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;
import com.pressassociation.bus.NiceMbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.xml.datatype.Duration;
import java.io.Serializable;
import java.util.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbElement extends AbstractPseudoNative<MbElement> implements MbVisitable, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PseudoNativeMbElement.class);

    private static final Set<Class<?>> IMMUTABLE_VALUES = ImmutableSet.of(Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class, String.class, Duration.class);

    private static final int TYPE_MASK_GENERIC = 251658240;

    private static final int TYPE_MASK_SPECIFIC = 16777215;

    // Package Public Fields

    transient PseudoNativeMbElement parent;

    transient PseudoNativeMbElement firstChild;

    transient PseudoNativeMbElement lastChild;

    transient PseudoNativeMbElement previousSibling;

    transient PseudoNativeMbElement nextSibling;

    // Private Fields

    private transient volatile int modcount;

    private transient String path = null;

    private String name = "";

    private Optional<Object> value = Optional.absent();

    private String namespace = "";

    private int type;

    private Optional<String> parserClassName = Optional.absent();

    private transient PseudoNativeMbMessage message;

    // Constructors

    PseudoNativeMbElement() {
    }

    private PseudoNativeMbElement(int type) {
        this.type = type;
        // TODO: What should name be set to?
        PseudoNativeMbElementManager.getInstance().register(this);
    }

    private PseudoNativeMbElement(String parserClassName) {
        this.parserClassName = Optional.fromNullable(parserClassName);
        this.name = parserClassName;
        PseudoNativeMbElementManager.getInstance().register(this);
    }

    private PseudoNativeMbElement(int type, String name, @Nullable Object value) {
        this.type = type;
        this.name = name;
        this.value = Optional.fromNullable(value);
        PseudoNativeMbElementManager.getInstance().register(this);
    }

    /*
     * Copy Constructor
     */
    public PseudoNativeMbElement(PseudoNativeMbElement original) throws MbException {

        PseudoNativeMbElementManager.getInstance().register(this);

        this.parserClassName = Optional.fromNullable(original.parserClassName.orNull());
        this.type = original.type;
        this.name = original.name;
        this.namespace = original.namespace;

        if (original.value.isPresent()) {
            final Object realValue = original.value.get();
            final Class<?> klass = realValue.getClass();
            if (klass.isPrimitive() || IMMUTABLE_VALUES.contains(klass)) {
                this.value = Optional.fromNullable(realValue);
            } else {
                if (realValue instanceof Calendar) {
                    this.value = Optional.of(((Calendar) realValue).clone());
                } else if (realValue instanceof BitSet) {
                    this.value = Optional.of(((BitSet) realValue).clone());
                } else {
                    throw new NiceMbException("Unsupported type " + klass);
                }
            }
        }

        Iterator<PseudoNativeMbElement> iter = original.childIterator();
        while (iter.hasNext()) {
            this.addAsLastChild(new PseudoNativeMbElement(iter.next()));
        }
    }

    // Methods

    void setMessage(PseudoNativeMbMessage message) {
        this.message = message;
    }

    public String getName() throws MbException {
        return name;
    }

    public Object getValue() throws MbException {
        return value.orNull();
    }

    public PseudoNativeMbElement getParent() throws MbException {
        return parent;
    }

    public void setName(String arg0) throws MbException {
        this.name = arg0;
    }

    public PseudoNativeMbElement copy() throws MbException {
        return new PseudoNativeMbElement(this);
    }

    public int getType() throws MbException {
        return type & TYPE_MASK_GENERIC;
    }

    public void setValue(@Nullable Object value) throws MbException {
        this.value = Optional.fromNullable(value);
    }

    // TODO: If null should this delegate to parent?
    public String getNamespace() throws MbException {
        return namespace;
    }

    public PseudoNativeMbElement createElementAfter(int type) throws MbException {
        return addAfter(new PseudoNativeMbElement(type));
    }

    public PseudoNativeMbElement createElementAfter(int type, String name, Object value) throws MbException {
        return addAfter(new PseudoNativeMbElement(type, name, value));
    }

    public PseudoNativeMbElement createElementAfter(String parserClassName) throws MbException {
        return addAfter(new PseudoNativeMbElement(parserClassName));
    }

    public PseudoNativeMbElement createElementBefore(int type) throws MbException {
        return addBefore(new PseudoNativeMbElement(type));
    }

    public PseudoNativeMbElement createElementBefore(int type, String name, Object value) throws MbException {
        return addBefore(new PseudoNativeMbElement(type, name, value));
    }

    public PseudoNativeMbElement createElementBefore(String parserClassName) throws MbException {
        return addBefore(new PseudoNativeMbElement(parserClassName));
    }

    public PseudoNativeMbElement createElementAsFirstChild(int type) throws MbException {
        return addAsFirstChild(new PseudoNativeMbElement(type));
    }

    public PseudoNativeMbElement createElementAsFirstChild(String parserClassName) throws MbException {
        return addAsFirstChild(new PseudoNativeMbElement(parserClassName));
    }

    public PseudoNativeMbElement createElementAsFirstChild(int type, String name, Object value) throws MbException {
        return addAsFirstChild(new PseudoNativeMbElement(type, name, value));
    }

    public PseudoNativeMbElement createElementAsLastChild(int type) throws MbException {
        return addAsLastChild(new PseudoNativeMbElement(type));
    }

    public PseudoNativeMbElement createElementAsLastChild(int type, String name, Object value) throws MbException {
        return addAsLastChild(new PseudoNativeMbElement(type, name, value));
    }

    public PseudoNativeMbElement createElementAsLastChild(String parserClassName) throws MbException {
        return addAsLastChild(new PseudoNativeMbElement(parserClassName));
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
        return String.valueOf(value.orNull());
    }

    /**
     * TODO: Evaluate whether this is the correct implementation
     *
     * @return
     * @throws MbException
     */
    public int getValueState() throws MbException {
        switch (getType()) {
            case MbElement.TYPE_VALUE:
            case MbElement.TYPE_NAME_VALUE:
                return value.isPresent() ? MbElement.VALUE_STATE_VALID : MbElement.VALUE_STATE_INVALID;
            case MbElement.TYPE_NAME:
            case MbElement.TYPE_SPECIAL:
                return value.isPresent() ? MbElement.VALUE_STATE_INVALID : MbElement.VALUE_STATE_VALID;
            default:
                return MbElement.VALUE_STATE_UNDEFINED;
        }
    }

    public String getParserClassName() throws MbException {
        return parserClassName.isPresent() ? parserClassName.get() : parent == null ? null : parent.getParserClassName();
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
        return Preconditions.checkNotNull(parent).childIterator(nextSibling, null);
    }

    public Iterator<PseudoNativeMbElement> followingSiblingIteratoInclusive() {
        return Preconditions.checkNotNull(parent).childIterator(this, null);
    }

    public Iterator<PseudoNativeMbElement> precedingSiblingIterator() {
        return Preconditions.checkNotNull(parent).childIterator(parent.firstChild, this);
    }

    public Iterator<PseudoNativeMbElement> precedingSiblingIteratorInclusive() {
        return Preconditions.checkNotNull(parent).childIterator(parent.firstChild, nextSibling);
    }

    public Iterator<PseudoNativeMbElement> childIterator() {
        return childIterator(firstChild, null);
    }

    public Iterator<PseudoNativeMbElement> childIterator(final PseudoNativeMbElement start, final PseudoNativeMbElement end) {
        final int currentModCount = modcount;
        return new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = start;

            @Override protected PseudoNativeMbElement computeNext() {
                if (currentModCount != modcount) {
                    throw new ConcurrentModificationException();
                }
                if (next != end && next != null) {
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
        String parentPath = parent.getPath();
        return parentPath.charAt(parentPath.length() - 1) == '/' ? parentPath + name : parentPath + "/" + name;
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
        logger.warn("WARNING: Uses XPath behaviour to evaluate may not be entirely accurate");
        List<PseudoNativeMbElement> children = checkListType(evaluateNativeXPath(arg0), PseudoNativeMbElement.class);
        return children.toArray(new PseudoNativeMbElement[children.size()]);
    }

    public PseudoNativeMbElement addBefore(PseudoNativeMbElement element) throws MbException {
        checkValidParent();
        element.parent = this.parent;
        if (this.parent.firstChild == this) this.parent.firstChild = element;
        if (this.previousSibling != null) this.previousSibling.nextSibling = element;
        element.previousSibling = this.previousSibling;
        element.nextSibling = this;
        this.previousSibling = element;
        this.parent.modcount++;
        return element;
    }

    public PseudoNativeMbElement addAfter(PseudoNativeMbElement element) throws MbException {
        checkValidParent();
        element.parent = this.parent;
        if (this.parent.lastChild == this) this.parent.lastChild = element;
        if (this.nextSibling != null) this.nextSibling.previousSibling = element;
        element.nextSibling = this.nextSibling;
        element.previousSibling = this;
        this.nextSibling = element;
        this.parent.modcount++;
        return element;
    }

    private void checkValidParent() {
        Preconditions.checkState(this.parent != null, "Elements cannot be added as siblings of root element");
    }

    public PseudoNativeMbElement addAsFirstChild(PseudoNativeMbElement element) throws MbException {
        Preconditions.checkNotNull(element);
        Preconditions.checkState((firstChild != null && lastChild != null) || firstChild == lastChild);

        element.parent = this;
        if (firstChild != null) firstChild.previousSibling = element;
        element.nextSibling = firstChild;
        firstChild = element;
        if (lastChild == null) lastChild = firstChild;
        modcount++;
        return element;
    }

    public PseudoNativeMbElement addAsLastChild(PseudoNativeMbElement element) throws MbException {
        Preconditions.checkNotNull(element);
        element.parent = this;
        if (this.lastChild != null) this.lastChild.nextSibling = element;
        element.previousSibling = this.lastChild;
        this.lastChild = element;
        if (firstChild == null) firstChild = lastChild;
        modcount++;
        return element;
    }

    public void copyElementTree(PseudoNativeMbElement original) throws MbException {
        while (this.firstChild != null) {
            this.firstChild.detach();
        }
        this.firstChild = this.lastChild = null;

        Iterator<PseudoNativeMbElement> iter = original.childIterator();
        while (iter.hasNext()) {
            addAsLastChild(new PseudoNativeMbElement(iter.next()));
        }
    }

    public void detach() throws MbException {
        if (previousSibling != null) previousSibling.nextSibling = nextSibling;
        if (nextSibling != null) nextSibling.previousSibling = previousSibling;
        if (parent.firstChild == this) parent.firstChild = this.nextSibling;
        if (parent.lastChild == this) parent.lastChild = this.previousSibling;
        this.path = null;
        this.parent = null;
        this.message = null;
        modcount++;
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
        this.parserClassName = Optional.of(parserClassName);
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("handle", getHandle())
                .add("parent", Optional.fromNullable(parent).transform(new Function<PseudoNativeMbElement, Long>() {
                    @Override
                    public Long apply(PseudoNativeMbElement input) {
                        return input.getHandle();
                    }
                }).orNull())
                .add("path", getPath())
                .add("namespace", namespace)
                .add("name", name)
                .add("parserClassName", parserClassName.orNull())
                .add("type", type)
                .add("genericType", type & TYPE_MASK_GENERIC)
                .add("specificType", type & TYPE_MASK_SPECIFIC)
                .add("value", value.orNull()).omitNullValues().toString();
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
