package com.ibm.broker.plugin.visitor;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.PseudoNativeMbElement;
import com.ibm.broker.plugin.PseudoNativeMbMessage;

/**
 * Created with IntelliJ IDEA.
 * User: bobb
 * Date: 19/08/2012
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public interface MbMessageVisitor {
    void visit(PseudoNativeMbMessage message);
    void visit(PseudoNativeMbElement element);
}
