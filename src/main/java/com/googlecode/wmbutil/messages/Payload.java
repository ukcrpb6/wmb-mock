package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public abstract class Payload extends MbElementWrapper {

	public Payload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}
}
