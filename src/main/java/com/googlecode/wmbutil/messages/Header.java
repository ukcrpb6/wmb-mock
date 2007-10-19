package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public abstract class Header extends MbElementWrapper {

	public Header(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}
}
