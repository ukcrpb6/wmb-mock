package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ImmutableMbPropertiesHeader extends MutableMbPropertiesHeader {
    ImmutableMbPropertiesHeader(MbElement elm) throws MbException {
        super(elm);
    }

    @Override
    public void setMessageFormat(String messageFormat) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMessageSet(String messageSet) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMessageType(String messageType) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTopic(String topic) throws MbException {
        throw new UnsupportedOperationException();
    }
}
