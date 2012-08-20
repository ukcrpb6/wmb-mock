package com.ibm.broker.plugin;

import com.google.common.collect.Maps;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import org.jaxen.SimpleVariableContext;
import org.jaxen.VariableContext;
import org.jaxen.XPath;

import java.util.Map;

public class PseudoNativeMbXPathManager implements NativeMbXPathManager {

    Map<Long, PseudoNativeMbXPath> engines = Maps.newHashMap();

    private static class InstanceHolder {
        static final PseudoNativeMbXPathManager instance = new PseudoNativeMbXPathManager();
    }

    public static PseudoNativeMbXPathManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbXPathManager() {
    }

    private long getNativeMbXPathEngineHandle(PseudoNativeMbXPath engine) {
        return engine == null ? 0L : engine.hashCode();
    }

    public PseudoNativeMbXPath getNativeMbXPath(MbXPath xpath) {
        return engines.get(xpath.getHandle());
    }

    @Override
    public synchronized long _createXPathEngine(String s, long contextHandle) throws MbException {
        PseudoNativeMbXPath xpathEngine = new PseudoNativeMbXPath(s, contextHandle);
        long handle = getNativeMbXPathEngineHandle(xpathEngine);
        engines.put(handle, xpathEngine);
        return handle;
    }

    @Override
    public void _deleteXPathEngine(long handle) {
        engines.remove(handle);
    }

    @Override
    public void _assignVariable(long handle, String name, double value) {
        engines.get(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, boolean value) {
        engines.get(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, String value) {
        engines.get(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void _assignVariable(long handle, String name, long[] arrayOfLong, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void _removeVariable(long handle, String name) {
        engines.get(handle).removeVariableValue(name);
    }

    @Override
    public void _removeAllVariables(long handle) {
        engines.get(handle).removeAllVariables();
    }

    @Override
    public void _addNamespacePrefix(long handle, String prefix, String uri) {
        engines.get(handle).addNamespacePrefix(prefix, uri);
    }

    @Override
    public void _removeNamespacePrefix(long handle, String prefix) {
        engines.get(handle).removeNamespacePrefix(prefix);
    }

    @Override
    public void _setDefaultNamespace(long handle, String uri) {
        engines.get(handle).setDefaultNamespace(uri);
    }

}
