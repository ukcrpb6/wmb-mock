package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbUserException;

public class NiceMbException extends MbUserException {

	private static final long serialVersionUID = -5760540385903728797L;

	public NiceMbException(Object source, String msg) {
		super(source.getClass().getName(), null, null, null, msg, null);
	}

	public NiceMbException(String msg) {
		
		super(null, null, null, null, msg, null);
	}


}
