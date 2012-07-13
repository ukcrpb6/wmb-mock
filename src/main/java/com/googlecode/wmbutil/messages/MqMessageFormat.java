package com.googlecode.wmbutil.messages;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MqMessageFormat {

    public static final String BINARY = makeFormat("");

    public static final String MQSTR = makeFormat("MQSTR");

    public static final String RFH2 = makeFormat("MQHRF2");

    /**
     * Creates a format string that is valid for the MQMD header.
     *
     * @param format format value
     * @return String - 8 char padded representation of the format value
     */
    public static String makeFormat(String format) {
        checkArgument(format.length() <= 8, "Illegal format string length <" + format + ">");
        return Strings.padEnd(format, 8, ' ');
    }

}
