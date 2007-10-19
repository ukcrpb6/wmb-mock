package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public abstract class Body extends MbElementWrapper {

	public Body(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}
}
