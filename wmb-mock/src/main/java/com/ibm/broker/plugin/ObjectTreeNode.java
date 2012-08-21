package com.ibm.broker.plugin;

import java.io.Serializable;
import java.util.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ObjectTreeNode<E> extends AbstractSequentialList<ObjectTreeNode<E>> implements Cloneable, Serializable {

    private transient Entry<ObjectTreeNode<E>> header = new Entry<ObjectTreeNode<E>>(null, null, null);
    private transient int size = 0;

    private transient ObjectTreeNode<E> parent = null;

    private E value;

    public ObjectTreeNode() {
        header.next = header.previous = header;
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param  c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ObjectTreeNode(Collection<? extends ObjectTreeNode<E>> c) {
        this();
        addAll(c);
    }

    /**
     * Returns the first element in this list.
     *
     * @return the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public ObjectTreeNode<E> getFirst() {
        if (size==0)
            throw new NoSuchElementException();

        return header.next.element;
    }

    /**
     * Returns the last element in this list.
     *
     * @return the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public ObjectTreeNode<E> getLast()  {
        if (size==0)
            throw new NoSuchElementException();

        return header.previous.element;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public ObjectTreeNode<E> removeFirst() {
        return remove(header.next);
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public ObjectTreeNode<E> removeLast() {
        return remove(header.previous);
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param e the element to add
     */
    public void addFirst(ObjectTreeNode<E> e) {
        addBefore(e, header.next);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>This method is equivalent to {@link #add}.
     *
     * @param e the element to add
     */
    public void addLast(ObjectTreeNode<E> e) {
        addBefore(e, header);
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(ObjectTreeNode<E> e) {
        addBefore(e, header);
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        if (o==null) {
            for (Entry<ObjectTreeNode<E>> e = header.next; e != header; e = e.next) {
                if (e.element==null) {
                    remove(e);
                    return true;
                }
            }
        } else {
            for (Entry<ObjectTreeNode<E>> e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator.  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in
     * progress.  (Note that this will occur if the specified collection is
     * this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(Collection<? extends ObjectTreeNode<E>> c) {
        return addAll(size, c);
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element
     *              from the specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends ObjectTreeNode<E>> c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+size);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew==0)
            return false;
        modCount++;

        Entry<ObjectTreeNode<E>> successor = (index==size ? header : entry(index));
        Entry<ObjectTreeNode<E>> predecessor = successor.previous;
        for (int i=0; i<numNew; i++) {
            Entry<ObjectTreeNode<E>> e = new Entry<ObjectTreeNode<E>>((ObjectTreeNode<E>)a[i], successor, predecessor);
            predecessor.next = e;
            predecessor = e;
        }
        successor.previous = predecessor;

        size += numNew;
        return true;
    }

    /**
     * Removes all of the elements from this list.
     */
    @Override
    public void clear() {
        Entry<ObjectTreeNode<E>> e = header.next;
        while (e != header) {
            Entry<ObjectTreeNode<E>> next = e.next;
            e.next = e.previous = null;
            e.element.parent = null;   //BB
            e.element = null;
            e = next;
        }
        header.next = header.previous = header;
        size = 0;
        modCount++;
    }


    // Positional Access Operations

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public ObjectTreeNode<E> get(int index) {
        return entry(index).element;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ObjectTreeNode<E> set(int index, ObjectTreeNode<E> element) {
        Entry<ObjectTreeNode<E>> e = entry(index);
        ObjectTreeNode<E> oldVal = e.element;
        e.element.parent = null; //BB
        e.element = element;
        e.element.parent = this;     //BB
        return oldVal;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, ObjectTreeNode<E> element) {
        addBefore(element, (index==size ? header : entry(index)));
    }

    /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ObjectTreeNode<E> remove(int index) {
        return remove(entry(index));
    }

    /**
     * Returns the indexed entry.
     */
    private Entry<ObjectTreeNode<E>> entry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+size);
        Entry<ObjectTreeNode<E>> e = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
                e = e.previous;
        }
        return e;
    }


    // Search Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o==null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element==null)
                    return index;
                index++;
            }
        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (o.equals(e.element))
                    return index;
                index++;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = size;
        if (o==null) {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (e.element==null)
                    return index;
            }
        } else {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (o.equals(e.element))
                    return index;
            }
        }
        return -1;
    }

    /**
     * Returns a list-iterator of the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * Obeys the general contract of <tt>List.listIterator(int)</tt>.<p>
     *
     * The list-iterator is <i>fail-fast</i>: if the list is structurally
     * modified at any time after the Iterator is created, in any way except
     * through the list-iterator's own <tt>remove</tt> or <tt>add</tt>
     * methods, the list-iterator will throw a
     * <tt>ConcurrentModificationException</tt>.  Thus, in the face of
     * concurrent modification, the iterator fails quickly and cleanly, rather
     * than risking arbitrary, non-deterministic behavior at an undetermined
     * time in the future.
     *
     * @param index index of the first element to be returned from the
     *              list-iterator (by a call to <tt>next</tt>)
     * @return a ListIterator of the elements in this list (in proper
     *         sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @see List#listIterator(int)
     */
    public ListIterator<ObjectTreeNode<E>> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<ObjectTreeNode<E>> {
        private Entry<ObjectTreeNode<E>> lastReturned = header;
        private Entry<ObjectTreeNode<E>> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("Index: "+index+
                                                    ", Size: "+size);
            if (index < (size >> 1)) {
                next = header.next;
                for (nextIndex=0; nextIndex<index; nextIndex++)
                    next = next.next;
            } else {
                next = header;
                for (nextIndex=size; nextIndex>index; nextIndex--)
                    next = next.previous;
            }
        }

        public boolean hasNext() {
            return nextIndex != size;
        }

        public ObjectTreeNode<E> next() {
            checkForComodification();
            if (nextIndex == size)
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }

        public boolean hasPrevious() {
            return nextIndex != 0;
        }

        public ObjectTreeNode<E> previous() {
            if (nextIndex == 0)
                throw new NoSuchElementException();

            lastReturned = next = next.previous;
            nextIndex--;
            checkForComodification();
            return lastReturned.element;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex-1;
        }

        public void remove() {
            checkForComodification();
            Entry<ObjectTreeNode<E>> lastNext = lastReturned.next;
            try {
                ObjectTreeNode.this.remove(lastReturned);
            } catch (NoSuchElementException e) {
                throw new IllegalStateException();
            }
            if (next==lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = header;
            expectedModCount++;
        }

        public void set(ObjectTreeNode<E> e) {
            if (lastReturned == header)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.element.parent = null; //BB
            lastReturned.element = e;
            e.parent = ObjectTreeNode.this;         //BB
        }

        public void add(ObjectTreeNode<E> e) {
            checkForComodification();
            lastReturned = header;
            addBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private static class Entry<E> {
        E element;
        Entry<E> next;
        Entry<E> previous;

        Entry(E element, Entry<E> next, Entry<E> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private Entry<ObjectTreeNode<E>> addBefore(ObjectTreeNode<E> e, Entry<ObjectTreeNode<E>> entry) {
        Entry<ObjectTreeNode<E>> newEntry = new Entry<ObjectTreeNode<E>>(e, entry, entry.previous);
        newEntry.element.parent = this;     // BB
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
        size++;
        modCount++;
        return newEntry;
    }

    private ObjectTreeNode<E> remove(Entry<ObjectTreeNode<E>> e) {
        if (e == header)
            throw new NoSuchElementException();

        ObjectTreeNode<E> result = e.element;
        e.previous.next = e.next;
        e.next.previous = e.previous;
        e.next = e.previous = null;
        e.element.parent = null;     //BB
        e.element = null;
        size--;
        modCount++;
        return result;
    }

    /**
     * @since 1.6
     */
    public Iterator<ObjectTreeNode<E>> descendingIterator() {
        return new DescendingIterator();
    }

    /** Adapter to provide descending iterators via ListItr.previous */
    private class DescendingIterator implements Iterator<ObjectTreeNode<E>> {
        final ListItr itr = new ListItr(size());
        public boolean hasNext() {
            return itr.hasPrevious();
        }
        public ObjectTreeNode<E> next() {
            return itr.previous();
        }
        public void remove() {
            itr.remove();
        }
    }

    /**
     * Returns a shallow copy of this <tt>LinkedList</tt>. (The elements
     * themselves are not cloned.)
     *
     * @return a shallow copy of this <tt>LinkedList</tt> instance
     */
    @SuppressWarnings("unchecked")
    public ObjectTreeNode<E> clone() throws CloneNotSupportedException {
        ObjectTreeNode<E> clone;
        try {
            clone = (ObjectTreeNode<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }

        // Put clone into "virgin" state
        clone.header = new Entry<ObjectTreeNode<E>>(null, null, null);
        clone.header.next = clone.header.previous = clone.header;
        clone.parent = this.parent;
        clone.size = 0;
        clone.modCount = 0;

        // Initialize clone with our elements
        for (Entry<ObjectTreeNode<E>> e = header.next; e != header; e = e.next)
            clone.add(e.element.clone());

        return clone;
    }

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list
     *         in proper sequence
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Entry<ObjectTreeNode<E>> e = header.next; e != header; e = e.next)
            result[i++] = e.element;
        return result;
    }

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to <tt>null</tt>.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for (Entry<ObjectTreeNode<E>> e = header.next; e != header; e = e.next)
            result[i++] = e.element;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    private static final long serialVersionUID = 876323262645176354L;

    /**
     * Save the state of this <tt>LinkedList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The size of the list (the number of elements it
     *             contains) is emitted (int), followed by all of its
     *             elements (each an Object) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out size
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (Entry e = header.next; e != header; e = e.next)
            s.writeObject(e.element);
    }

    /**
     * Reconstitute this <tt>LinkedList</tt> instance from a stream (that is
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Initialize header
        header = new Entry<ObjectTreeNode<E>>(null, null, null);
        header.next = header.previous = header;

        // Read in all elements in the proper order.
        for (int i=0; i<size; i++)
            addBefore((ObjectTreeNode<E>)s.readObject(), header);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public ObjectTreeNode<E> getParent() {
        return parent;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public ObjectTreeNode<E> getNextSibling() throws MbException {
        if(this.parent != null) {
            int index = this.parent.indexOf(this) + 1;
            if(index < this.parent.size()) {
                return this.parent.get(index);
            }
        }
        return null;
    }

    public ObjectTreeNode<E> getPreviousSibling() throws MbException {
        if(this.parent != null) {
            int index = this.parent.indexOf(this) - 1;
            if(index >= 0) {
                return this.parent.get(index);
            }
        }
        return null;
    }

    @Override public boolean equals(Object o) {
        return o instanceof ObjectTreeNode && o.hashCode() == this.hashCode();
    }

    @Override public int hashCode() {
        return System.identityHashCode(this);
    }
}
