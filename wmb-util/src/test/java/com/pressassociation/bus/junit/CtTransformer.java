package com.pressassociation.bus.junit;

import javassist.CtClass;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface CtTransformer {
    void transform(CtClass klass);
}
