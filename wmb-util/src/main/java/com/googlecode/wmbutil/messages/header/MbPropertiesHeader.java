package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbPropertiesHeader extends MbHeader {

    public static final String MSG_SET = "MessageSet";
    public static final String MSG_TYPE = "MessageType";
    public static final String FORMAT = "MessageFormat";
    public static final String TOPIC = "Topic";

    String getMessageFormat() throws MbException;

    void setMessageFormat(String messageFormat) throws MbException;

    String getMessageSet() throws MbException;

    void setMessageSet(String messageSet) throws MbException;

    String getMessageType() throws MbException;

    void setMessageType(String messageType) throws MbException;

    String getTopic() throws MbException;

    void setTopic(String topic) throws MbException;
}
