package com.ibm.broker.plugin;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.saxpath.SAXPathException;

public class PseudoNativeMbXPath {

    private String xpath;

    private final long contextHandle;

    private PseudoMbMessageNavigator navigator = new PseudoMbMessageNavigator();

    private SimpleNamespaceContext namespaceContext;

    private SimpleVariableContext variableContext;

    public PseudoNativeMbXPath(String xpath, long contextHandle) {
        this.xpath = xpath;
        this.contextHandle = contextHandle;
        this.variableContext = new SimpleVariableContext();
        this.namespaceContext = new SimpleNamespaceContext();
    }

    public void setVariableValue(String name, Object value) {
        variableContext.setVariableValue(name, value);
    }

    public void removeVariableValue(String name) {
        variableContext.setVariableValue(name, null);
    }

    public void removeAllVariables() {
        variableContext = new SimpleVariableContext();
    }

    public void setDefaultNamespace(String uri) {
        namespaceContext.addNamespace("", uri);
    }

    public void removeNamespacePrefix(String prefix) {
        throw new UnsupportedOperationException();
    }

    public void addNamespacePrefix(String prefix, String uri) {
        namespaceContext.addNamespace(prefix, uri);
    }

    public void setXPath(String s) {
        this.xpath = s;
    }

    public PseudoMbMessageNavigator getNavigator() {
        return navigator;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("xpath", xpath)
                .add("namespaceContext", namespaceContext)
                .add("variableContext", variableContext)
                .add("navigator", navigator)
                .add("localContext", contextHandle).toString();
    }

    public Object evaluateXPath(PseudoNativeMbElement nativeMbElement) throws MbException {
        try {
            return navigator.parseXPath(xpath).selectNodes(nativeMbElement);
        } catch (SAXPathException e) {
            throw Throwables.propagate(e);
        }
    }
}
