package com.googlecode.wmbutil.messages;

import com.google.common.base.Objects;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbHeaderType {

    public static final MbHeaderType PROPERTIES = new MbHeaderType("PropertiesParser", "Properties");

    public static final MbHeaderType MQMD = new MbHeaderType("MQMD");

    public static final MbHeaderType MQRFH2 = new MbHeaderType("MQRFH2");
    public static final MbHeaderType MQRFH2C = new MbHeaderType("MQRFH2C");

    // HTTP Headers
    public static final MbHeaderType HTTP_INPUT = new MbHeaderType("WSINPHDR", "HTTPInputHeader");
    public static final MbHeaderType HTTP_REQUEST = new MbHeaderType("WSREQHDR", "HTTPRequestHeader");
    public static final MbHeaderType HTTP_RESPONSE = new MbHeaderType("WSRSPHDR", "HTTPResponseHeader");
    public static final MbHeaderType HTTP_REPLY = new MbHeaderType("WSREPHDR", "HTTPReplyHeader");

    private final String rootElementName;
    private final String parserName;

    MbHeaderType(String parserName) {
        this(parserName, parserName);
    }

    MbHeaderType(String parserName, String rootElementName) {
        this.rootElementName = rootElementName;
        this.parserName = parserName;
    }

    public String getRootElementName() {
        return rootElementName;
    }

    public String getRootPath() {
        return "/" + rootElementName;
    }

    public String getParserName() {
        return parserName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parserName, rootElementName);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof MbHeaderType &&
                Objects.equal(parserName, ((MbHeaderType) o).parserName) &&
                Objects.equal(rootElementName, ((MbHeaderType) o).rootElementName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("rootElementName", rootElementName)
                .add("parserName", parserName)
                .add("path", getRootPath()).toString();
    }

}