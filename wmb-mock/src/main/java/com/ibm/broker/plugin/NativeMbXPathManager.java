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
public interface NativeMbXPathManager {
    long _createXPathEngine(String s, long handle) throws MbException;

    void _deleteXPathEngine(long handle);

    void _assignVariable(long handle, String name, double value);

    void _assignVariable(long handle, String name, boolean value);

    void _assignVariable(long handle, String name, String value);

    void _assignVariable(long handle, String name, long l);

    void _assignVariable(long handle, String name, long[] arrayOfLong, int i);

    void _removeVariable(long handle, String name);

    void _removeAllVariables(long handle);

    void _addNamespacePrefix(long handle, String prefix, String uri);

    void _removeNamespacePrefix(long handle, String prefix);

    void _setDefaultNamespace(long handle, String uri);
}
