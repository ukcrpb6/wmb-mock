package com.googlecode.wmbutil.messages;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@SuppressWarnings("UnusedDeclaration")
public interface MqMessageType {

    public static final int MQMT_APPL_FIRST = 65536;
    public static final int MQMT_APPL_LAST = 999999999;
    public static final int MQMT_DATAGRAM = 8;
    public static final int MQMT_MQE_FIELDS = 113;
    public static final int MQMT_MQE_FIELDS_FROM_MQE = 112;
    public static final int MQMT_REPLY = 2;
    public static final int MQMT_REPORT = 4;
    public static final int MQMT_REQUEST = 1;
    public static final int MQMT_SYSTEM_FIRST = 1;
    public static final int MQMT_SYSTEM_LAST = 65535;

}
