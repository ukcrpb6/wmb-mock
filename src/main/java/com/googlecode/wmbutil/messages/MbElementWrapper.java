package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class MbElementWrapper {

	private MbElement wrappedElm;
	private boolean readOnly;
	
	public MbElementWrapper(MbElement elm, boolean readOnly) throws MbException {
		this.wrappedElm = elm;
		this.readOnly = readOnly;
	}
	
	public MbElement getMbElement() {
		return wrappedElm;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
}
