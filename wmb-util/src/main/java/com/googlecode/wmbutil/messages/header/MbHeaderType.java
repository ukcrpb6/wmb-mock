package com.googlecode.wmbutil.messages.header;

import com.google.common.base.Objects;
import com.ibm.broker.plugin.MbMQMD;
import com.ibm.broker.plugin.MbRFH2C;

/**
 * Fixed set of Message Broker header types.
 * <p/>
 * Note: might be better as an enum type
 *
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public final class MbHeaderType {

    public static final MbHeaderType PROPERTIES = new MbHeaderType("PropertiesParser", "Properties");

    public static final MbHeaderType MQMD = new MbHeaderType(MbMQMD.PARSER_NAME);

    public static final MbHeaderType MQRFH2 = new MbHeaderType("MQRFH2");
    public static final MbHeaderType MQRFH2C = new MbHeaderType(MbRFH2C.PARSER_NAME);

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