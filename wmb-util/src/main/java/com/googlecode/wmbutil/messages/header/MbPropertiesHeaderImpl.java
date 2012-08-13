package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbPropertiesHeaderImpl extends AbstractMbHeader implements MbPropertiesHeader {

    MbPropertiesHeaderImpl(MbElement elm) throws MbException {
        super(elm, MbHeaderType.PROPERTIES);
    }

    @Override
    public String getMessageFormat() throws MbException {
        return getValue(FORMAT);
    }

    @Override
    public void setMessageFormat(String messageFormat) throws MbException {
        setValue(FORMAT, messageFormat);
    }

    @Override
    public String getMessageSet() throws MbException {
        return getValue(MSG_SET);
    }

    @Override
    public void setMessageSet(String messageSet) throws MbException {
        setValue(MSG_SET, messageSet);
    }

    @Override
    public String getMessageType() throws MbException {
        return getValue(MSG_TYPE);
    }

    @Override
    public void setMessageType(String messageType) throws MbException {
        setValue(MSG_TYPE, messageType);
    }

    @Override
    public String getTopic() throws MbException {
        return getValue(TOPIC);
    }

    @Override
    public void setTopic(String topic) throws MbException {
        setValue(TOPIC, topic);
    }
}
