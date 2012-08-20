package com.ibm.broker.plugin;

/**
 * Created with IntelliJ IDEA.
 * User: bobb
 * Date: 20/08/2012
 * Time: 00:23
 * To change this template use File | Settings | File Templates.
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
