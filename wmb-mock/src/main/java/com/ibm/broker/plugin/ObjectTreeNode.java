package com.ibm.broker.plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ObjectTreeNode {
    PseudoNativeMbElement parent;
    PseudoNativeMbElement firstChild;
    PseudoNativeMbElement lastChild;
    PseudoNativeMbElement previousSibling;
    PseudoNativeMbElement nextSibling;

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

    public Iterator<PseudoNativeMbElement> followingSiblingIterator() {
        return followingSiblingIterator(false);
    }

    public Iterator<PseudoNativeMbElement> followingSiblingIterator(final boolean inclusive) {
        return new AbstractIterator<PseudoNativeMbElement>() {
            PseudoNativeMbElement next = inclusive ? ObjectTreeNode.this : nextSibling;

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
        PseudoNativeMbElement newMbElement = PseudoNativeMbElement.checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.firstChild != null) this.firstChild.previousSibling = newMbElement;
        newMbElement.nextSibling = this.firstChild;
        this.firstChild = newMbElement;
        if (lastChild == null) lastChild = firstChild;
    }

    public void addAsLastChild(PseudoNativeMbElement arg0) throws MbException {
        PseudoNativeMbElement newMbElement = PseudoNativeMbElement.checkMockMbElement(arg0);
        newMbElement.parent = this;
        if (this.lastChild != null) this.lastChild.nextSibling = newMbElement;
        newMbElement.previousSibling = this.lastChild;
        this.lastChild = newMbElement;
        if (firstChild == null) firstChild = lastChild;
    }
}
