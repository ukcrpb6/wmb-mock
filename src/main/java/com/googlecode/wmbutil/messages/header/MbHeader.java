package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbElement;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbHeader {
    MbElement getMbElement();

    MbHeaderType getType();
}
