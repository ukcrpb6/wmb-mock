package com.ibm.broker.plugin;

import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbMessageAssemblyManager
        extends AbstractNativeManager<MbMessageAssembly, PseudoNativeMbMessageAssembly>
        implements NativeMbMessageAssemblyManager, MbVisitable {

    private static class InstanceHolder {
        static final PseudoNativeMbMessageAssemblyManager instance = new PseudoNativeMbMessageAssemblyManager();
    }

    public static PseudoNativeMbMessageAssemblyManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbMessageAssemblyManager() {
    }

    @Override public long getHandle(MbMessageAssembly object) {
        return object.getHandle();
    }

    @Override protected PseudoNativeMbMessageAssembly createNativeInstance(Object... parameters) {
        return new PseudoNativeMbMessageAssembly();
    }

    private static PseudoNativeMbMessage lookupPseudoNativeMbMessage(long handle) {
        return PseudoNativeMbMessageManager.getInstance().getNative(handle);
    }

    private PseudoNativeMbMessageAssembly copy(long handle) {
        PseudoNativeMbMessageAssembly original = getNative(handle);
        PseudoNativeMbMessageAssembly copy = allocate();
        copy.setLocalEnvironment(original.getLocalEnvironment());
        copy.setGlobalEnvironment(original.getGlobalEnvironment());
        copy.setExceptionList(original.getExceptionList());
        copy.setMessage(original.getMessage());
        return copy;
    }

    private static PseudoNativeMbMessage createBlankMessage() {
        return PseudoNativeMbMessageManager.getInstance().allocate();
    }

    public MbMessageAssembly createBlankReadOnlyMessageAssembly() throws MbException {
        PseudoNativeMbMessageAssembly assembly = allocate();
        assembly.setLocalEnvironment(createBlankMessage());
        assembly.setGlobalEnvironment(createBlankMessage());
        assembly.setExceptionList(createBlankMessage());
        assembly.setMessage(createBlankMessage());
        return new MbMessageAssembly(assembly.getHandle(), true);
    }

    /*
     * Native Interface
     */

    @Override public long _createMessageAssembly(long handle, long messageHandle) throws MbException {
        PseudoNativeMbMessageAssembly assembly = copy(handle);
        assembly.setMessage(lookupPseudoNativeMbMessage(messageHandle));
        return assembly.getHandle();
    }

    @Override
    public long _createMessageAssembly2(long handle, long localEnvironmentHandle, long globalEnvironmentHandle, long exceptionListHandle, long messageHandle) throws MbException {
        PseudoNativeMbMessageAssembly assembly = copy(handle);
        assembly.setLocalEnvironment(lookupPseudoNativeMbMessage(localEnvironmentHandle));
        assembly.setGlobalEnvironment(lookupPseudoNativeMbMessage(globalEnvironmentHandle));
        assembly.setExceptionList(lookupPseudoNativeMbMessage(exceptionListHandle));
        assembly.setMessage(lookupPseudoNativeMbMessage(messageHandle));
        return assembly.getHandle();
    }

    @Override
    public long _createMessageAssembly(long handle, long localEnvironmentHandle, long exceptionListHandle, long messageHandle) throws MbException {
        PseudoNativeMbMessageAssembly assembly = copy(handle);
        assembly.setLocalEnvironment(lookupPseudoNativeMbMessage(localEnvironmentHandle));
        assembly.setExceptionList(lookupPseudoNativeMbMessage(exceptionListHandle));
        assembly.setMessage(lookupPseudoNativeMbMessage(messageHandle));
        return assembly.getHandle();
    }

    @Override public long _getMessage(long handle) throws MbException {
        return getNative(handle).getMessage().getHandle();
    }

    @Override public long _getLocalEnvironment(long handle) throws MbException {
        return getNative(handle).getLocalEnvironment().getHandle();
    }

    @Override public long _getGlobalEnvironment(long handle) throws MbException {
        return getNative(handle).getGlobalEnvironment().getHandle();
    }

    @Override public long _getExceptionList(long handle) throws MbException {
        return getNative(handle).getExceptionList().getHandle();
    }

    @Override public void _clearMessageAssembly(long handle) throws MbException {
        getNative(handle).clearMessageAssembly();
    }

    /*
     * Visitable Interface
     */

    @Override public void accept(MbMessageVisitor visitor) throws MbException {
        for(PseudoNativeMbMessageAssembly a : getAllocations()) {
            a.accept(visitor);
        }
    }

    public void accept(long handle, MbMessageVisitor visitor) throws MbException {
        getNative(handle).accept(visitor);
    }

}
