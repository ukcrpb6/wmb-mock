/**
 * Copyright 2012 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.broker.plugin;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
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

    public void clear() {
        engines.clear();
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
