package com.googlecode.wmbutil.messages;

import com.google.common.base.Objects;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public enum MbHttpHeaderType {

    INPUT("/HTTPInputHeader", "WSINPHDR"),
    REQUEST("/HTTPRequestHeader", "WSREQHDR"),
    RESPONSE("/HTTPResponseHeader", "WSRSPHDR"),
    REPLY("/HTTPReplyHeader", "WSREPHDR");

    private final String path;
    private final String parser;

    MbHttpHeaderType(String path, String parser) {
        this.path = path;
        this.parser = parser;
    }

    public String getPath() {
        return path;
    }

    public String getParser() {
        return parser;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("path", path)
                .add("parser", parser).toString();
    }
}
