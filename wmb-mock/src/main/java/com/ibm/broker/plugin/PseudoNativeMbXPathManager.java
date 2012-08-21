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

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class PseudoNativeMbXPathManager extends AbstractNativeManager<MbXPath, PseudoNativeMbXPath> implements NativeMbXPathManager {

    private static class InstanceHolder {
        static final PseudoNativeMbXPathManager instance = new PseudoNativeMbXPathManager();
    }

    public static PseudoNativeMbXPathManager getInstance() {
        return InstanceHolder.instance;
    }

    private PseudoNativeMbXPathManager() {
    }

    @Override
    protected PseudoNativeMbXPath createNativeInstance(Object... parameters) {
        return new PseudoNativeMbXPath((String) parameters[0], (Long) parameters[1]);
    }

    /*
     * Native Interface
     */

    @Override
    public synchronized long _createXPathEngine(String xpath, long contextHandle) throws MbException {
        return allocate(xpath, contextHandle).getHandle();
    }

    @Override
    public void _deleteXPathEngine(long handle) {
        remove(handle);
    }

    @Override
    public void _assignVariable(long handle, String name, double value) {
        getNative(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, boolean value) {
        getNative(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, String value) {
        getNative(handle).setVariableValue(name, value);
    }

    @Override
    public void _assignVariable(long handle, String name, long l) {
        // TODO: What should this implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void _assignVariable(long handle, String name, long[] arrayOfLong, int i) {
        // TODO: What should this implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void _removeVariable(long handle, String name) {
        getNative(handle).removeVariableValue(name);
    }

    @Override
    public void _removeAllVariables(long handle) {
        getNative(handle).removeAllVariables();
    }

    @Override
    public void _addNamespacePrefix(long handle, String prefix, String uri) {
        getNative(handle).addNamespacePrefix(prefix, uri);
    }

    @Override
    public void _removeNamespacePrefix(long handle, String prefix) {
        getNative(handle).removeNamespacePrefix(prefix);
    }

    @Override
    public void _setDefaultNamespace(long handle, String uri) {
        getNative(handle).setDefaultNamespace(uri);
    }

}
