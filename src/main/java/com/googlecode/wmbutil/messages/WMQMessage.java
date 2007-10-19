package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class WMQMessage extends ExtendedMessage {

	public WMQMessage() throws MbException {
		super();
	}
	
	public WMQMessage(MbMessage msg, boolean readOnly) throws MbException {
		super(msg, readOnly);
	}

	public Rfh2Header getRfh2Header() throws MbException {
		if(isReadOnly()) {
			if(Rfh2Header.hasRfh2Header(this)) {
				return new Rfh2Header(this.getMbMessage(), true);
			} else {
				return null;
			}
		} else {
			return new Rfh2Header(this.getMbMessage(), isReadOnly());
		}
	}
}
