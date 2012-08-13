package com.googlecode.wmbutil.messages;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@SuppressWarnings("UnusedDeclaration")
public class MqFormat {

    public static final String MQFMT_ADMIN = makeFormat("MQADMIN");
    public static final String MQFMT_CHANNEL_COMPLETED = makeFormat("MQCHCOM");
    public static final String MQFMT_CICS = makeFormat("MQCICS");
    public static final String MQFMT_COMMAND_1 = makeFormat("MQCMD1");
    public static final String MQFMT_COMMAND_2 = makeFormat("MQCMD2");
    public static final String MQFMT_DEAD_LETTER_HEADER = makeFormat("MQDEAD");
    public static final String MQFMT_DIST_HEADER = makeFormat("MQHDIST");
    public static final String MQFMT_EMBEDDED_PCF = makeFormat("MQHEPCF");
    public static final String MQFMT_EVENT = makeFormat("MQEVENT");
    public static final String MQFMT_IMS = makeFormat("MQIMS");
    public static final String MQFMT_IMS_VAR_STRING = makeFormat("MQIMSVS");
    public static final String MQFMT_MD_EXTENSION = makeFormat("MQHMDE");
    public static final String MQFMT_NONE = makeFormat("");
    public static final String MQFMT_PCF = makeFormat("MQPCF");
    public static final String MQFMT_REF_MSG_HEADER = makeFormat("MQHREF");
    public static final String MQFMT_RF_HEADER = makeFormat("MQRFH");
    public static final String MQFMT_RF_HEADER_1 = makeFormat("MQHRF");
    public static final String MQFMT_RF_HEADER_2 = makeFormat("MQHRF2");
    public static final String MQFMT_STRING = makeFormat("MQSTR");
    public static final String MQFMT_TRIGGER = makeFormat("MQTRIG");
    public static final String MQFMT_WORK_INFO_HEADER = makeFormat("MQHWIH");
    public static final String MQFMT_XMIT_Q_HEADER = makeFormat("MQXMIT");

    private static String makeFormat(String value) {
        checkArgument(value.length() <= 8, "Illegal format string length <" + value + ">");
        return Strings.padEnd(value, 8, ' ');
    }
}
