package com.ibm.broker.plugin;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessageAssembly extends AbstractPseudoNative<MbMessageAssembly> {

    private PseudoNativeMbMessage message;
    private PseudoNativeMbMessage localEnvironment;
    private PseudoNativeMbMessage globalEnvironment;
    private PseudoNativeMbMessage exceptionList;

    public PseudoNativeMbMessage getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(PseudoNativeMbMessage exceptionList) {
        this.exceptionList = exceptionList;
    }

    public PseudoNativeMbMessage getGlobalEnvironment() {
        return globalEnvironment;
    }

    public void setGlobalEnvironment(PseudoNativeMbMessage globalEnvironment) {
        this.globalEnvironment = globalEnvironment;
    }

    public PseudoNativeMbMessage getLocalEnvironment() {
        return localEnvironment;
    }

    public void setLocalEnvironment(PseudoNativeMbMessage localEnvironment) {
        this.localEnvironment = localEnvironment;
    }

    public PseudoNativeMbMessage getMessage() {
        return message;
    }

    public void setMessage(PseudoNativeMbMessage message) {
        this.message = message;
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

}
