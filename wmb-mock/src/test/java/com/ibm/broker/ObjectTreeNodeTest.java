package com.ibm.broker;

import com.ibm.broker.plugin.ObjectTreeNode;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ObjectTreeNodeTest {
    @Test
    public void testAddHierarchy() throws Exception {
        ObjectTreeNode<Integer> tree = new ObjectTreeNode<Integer>();
        tree.add(objectTreeNode(1));
        tree.add(objectTreeNode(2));
        tree.add(objectTreeNode(3));
        tree.add(objectTreeNode(4));
        tree.add(objectTreeNode(5));

        ObjectTreeNode<Integer> newNode = new ObjectTreeNode<Integer>();
        newNode.add(objectTreeNode(1));
        newNode.add(objectTreeNode(2));
        newNode.add(objectTreeNode(3));
        newNode.add(objectTreeNode(4));
        newNode.add(objectTreeNode(5));
        tree.add(newNode);

        Assert.assertEquals(6, tree.size());
        Assert.assertEquals(5, newNode.size());
    }

    public static <E> ObjectTreeNode<E> objectTreeNode(E value) {
        ObjectTreeNode<E> node = new ObjectTreeNode<E>();
        node.setValue(value);
        return node;
    }
}
