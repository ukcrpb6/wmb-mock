package com.ibm.broker;

import com.ibm.broker.plugin.ObjectTreeNode;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ListIterator;

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

        Assert.assertNull(tree.getParent());

        for (ObjectTreeNode<Integer> node : tree) {
            Assert.assertEquals(tree, node.getParent());
        }

        for (ObjectTreeNode<Integer> node : newNode) {
            Assert.assertEquals(newNode, node.getParent());
        }

        Assert.assertNull(tree.getPreviousSibling());
        Assert.assertNull(tree.getNextSibling());

        Assert.assertNotNull(newNode.getPreviousSibling());
        Assert.assertNull(newNode.getNextSibling());

        Assert.assertNull(newNode.get(0).getPreviousSibling());
        Assert.assertNotNull(newNode.get(0).getNextSibling());

        int i = 0;
        ObjectTreeNode<Integer> testNode = newNode.get(i);
        while(testNode != null && ++i < 6) {
            testNode = testNode.getNextSibling();
        }
        Assert.assertEquals(5, i);

        testNode = newNode.get(4);
        while(testNode != null && i-- > 0) {
            testNode = testNode.getPreviousSibling();
        }
        Assert.assertEquals(0, i);

        ListIterator<ObjectTreeNode<Integer>> iter = newNode.listIterator();
        while(iter.hasNext()) {
            ObjectTreeNode<Integer> o = iter.next();
            int index = newNode.indexOf(o);
            System.out.println(index + " -> " + o.getValue());
            if(index == 2 && o.getValue() == 3) {
                System.out.println("Adding after value " + o.getValue());
                ObjectTreeNode<Integer> newNode99 = new ObjectTreeNode<Integer>();
                newNode99.setValue(99);
                iter.add(newNode99);
                iter.previous();
            }
        }
        ObjectTreeNode<Integer> newNode99 = new ObjectTreeNode<Integer>();
        newNode99.setValue(99);
        iter.add(newNode99);
        Assert.assertEquals(99, (int) newNode.get(3).getValue());
        Assert.assertEquals(99, (int) newNode.getLast().getValue());
    }

    public static <E> ObjectTreeNode<E> objectTreeNode(E value) {
        ObjectTreeNode<E> node = new ObjectTreeNode<E>();
        node.setValue(value);
        return node;
    }
}
