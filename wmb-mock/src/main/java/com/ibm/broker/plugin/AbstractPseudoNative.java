package com.ibm.broker.plugin;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public abstract class AbstractPseudoNative<T> implements NativeFor<T> {

    private long handle;

    protected AbstractPseudoNative() {
        this.handle = hashCode();
    }

    protected AbstractPseudoNative(long handle) {
        this.handle = handle;
    }

    protected void setHandle(long handle) {
        this.handle = handle;
    }

    @Override public long getHandle() {
        return handle;
    }

    @Override protected Object clone() throws CloneNotSupportedException {
        AbstractPseudoNative clone = (AbstractPseudoNative) super.clone();
        clone.handle = clone.hashCode();
        return clone;
    }
}
