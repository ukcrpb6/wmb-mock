package com.pressassociation.bus.junit;

import javassist.CtClass;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class CtTransformerChain extends LinkedList<CtTransformer> implements CtTransformer {

    public CtTransformerChain(CtTransformer a, CtTransformer b) {
        add(a);
        add(b);
    }

    public boolean add(CtTransformer transformer) {
        if(transformer instanceof CtTransformerChain) {
            return this.addAll((CtTransformerChain) transformer);
        } else {
            return super.add(transformer);
        }
    }

    @Override
    public void transform(CtClass klass) {
        ListIterator<CtTransformer> iterator = listIterator();
        while(iterator.hasNext()) {
            iterator.next().transform(klass);
        }
    }
}
