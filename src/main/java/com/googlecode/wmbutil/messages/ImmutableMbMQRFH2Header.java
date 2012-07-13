package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ImmutableMbMQRFH2Header extends MutableMbMQRFH2Header {
    ImmutableMbMQRFH2Header(MbElement elm) throws MbException {
        super(elm);
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEncoding(int encoding) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFlags(int flags) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFormat(String format) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIntProperty(String area, String name, int value) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNameValueCCSID(int nameValueCCSID) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStringProperty(String area, String name, String value) throws MbException {
        throw new UnsupportedOperationException();
    }
}
