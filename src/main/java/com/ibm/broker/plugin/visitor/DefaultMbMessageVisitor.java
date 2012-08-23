package com.ibm.broker.plugin.visitor;

import com.ibm.broker.plugin.PseudoNativeMbElement;
import com.ibm.broker.plugin.PseudoNativeMbMessage;
import com.ibm.broker.plugin.PseudoNativeMbMessageAssembly;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class DefaultMbMessageVisitor implements MbMessageVisitor {
    @Override public void visit(PseudoNativeMbMessage message) {}

    @Override public void visit(PseudoNativeMbElement element) {}

    @Override public void visit(PseudoNativeMbMessageAssembly assembly) {}
}
