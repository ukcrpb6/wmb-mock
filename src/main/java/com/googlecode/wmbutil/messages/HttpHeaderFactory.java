package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public final class HttpHeaderFactory {

    private HttpHeaderFactory() {}

    public static MbElement getHeaderElement(MbMessage msg, MbHttpHeaderType type) throws MbException {
        return msg.getRootElement().getFirstElementByPath(type.getPath());
    }

    public static HttpHeader wrap(MbMessage msg, MbHttpHeaderType type, boolean readOnly) throws MbException {
        MbElement elm = getHeaderElement(msg, type);
        if (elm == null) {
            throw new NiceMbException("Failed to find " + type);
        }

        return new HttpHeader(elm, type, readOnly);
    }

    public static HttpHeader create(MbMessage msg, MbHttpHeaderType type) throws MbException {
        if (has(msg, type)) {
            throw new NiceMbException("Already have " + type + " header");
        }
        MbElement elm = msg.getRootElement().createElementAsLastChild(type.getParser());
        return new HttpHeader(elm, type, false);
    }

    public static HttpHeader wrapOrCreate(MbMessage msg, MbHttpHeaderType type) throws MbException {
        return has(msg, type) ? wrap(msg, type, false) : create(msg, type);
    }

    public static HttpHeader remove(MbMessage msg, MbHttpHeaderType type) throws MbException {
        MbElement elm = getHeaderElement(msg, type);
        if (elm != null) {
            elm.detach();
            return new HttpHeader(elm, type, true);
        } else {
            throw new NiceMbException("Failed to find " + type + " header");
        }
    }

    public static boolean has(MbMessage msg, MbHttpHeaderType type) throws MbException {
        return getHeaderElement(msg, type) != null;
    }

}
