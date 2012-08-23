package com.ibm.broker.plugin;

import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessageAssembly extends AbstractPseudoNative<MbMessageAssembly> implements MbVisitable {

    private PseudoNativeMbMessage message;
    private PseudoNativeMbMessage localEnvironment;
    private PseudoNativeMbMessage globalEnvironment;
    private PseudoNativeMbMessage exceptionList;

    public PseudoNativeMbMessage getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(PseudoNativeMbMessage exceptionList) {
        this.exceptionList = exceptionList;
        this.exceptionList.setName("ExceptionList");
    }

    public PseudoNativeMbMessage getGlobalEnvironment() {
        return globalEnvironment;
    }

    public void setGlobalEnvironment(PseudoNativeMbMessage globalEnvironment) {
        this.globalEnvironment = globalEnvironment;
        this.globalEnvironment.setName("Environment");
    }

    public PseudoNativeMbMessage getLocalEnvironment() {
        return localEnvironment;
    }

    public void setLocalEnvironment(PseudoNativeMbMessage localEnvironment) {
        this.localEnvironment = localEnvironment;
        this.localEnvironment.setName("LocalEnvironment");
    }

    public PseudoNativeMbMessage getMessage() {
        return message;
    }

    public void setMessage(PseudoNativeMbMessage message) {
        this.message = message;
        this.message.setName("Root");
    }

    public void clearMessageAssembly() {
        this.message = null;
        this.globalEnvironment = null;
        this.localEnvironment = null;
        this.exceptionList = null;
    }

    /*
     * NativeFor Interface
     */

    @Override public MbMessageAssembly get() {
        return new MbMessageAssembly(this.getHandle());
    }

    @Override public boolean isManaged() {
        return PseudoNativeMbMessageAssemblyManager.getInstance().isManaged(this);
    }

    @Override public void accept(MbMessageVisitor visitor) throws MbException {
        visitor.visit(this);
        visitor.visit(localEnvironment);
        visitor.visit(globalEnvironment);
        visitor.visit(exceptionList);
        visitor.visit(message);
    }
}
