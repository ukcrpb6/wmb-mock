package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class TdsRecord extends MbElementWrapper {

	public TdsRecord(MbElement wrappedElm, boolean readOnly) throws MbException {
		super(wrappedElm, readOnly);
	}
	
	private Object getField(String name) throws MbException {
		MbElement elm = getMbElement().getFirstElementByPath(name);

		if(elm != null) {
			return elm.getValue();
		} else {
			throw new NiceMbException("Unknown field: " + name);
		}
	}

	private void setField(String name, Object value) throws MbException {
		checkReadOnly();
		
		MbElement elm = getMbElement().getFirstElementByPath(name);
		if(elm == null) {
			elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, name, value);
		} else {
			elm.setValue(value);
		}
	}
	
	public String getStringField(String name) throws MbException {
		return (String) getField(name);
	}

	public void setStringField(String name, String value) throws MbException {
		setField(name, value);
	}
	
	
	public int getIntField(String name) throws MbException {
		return ((Integer) getField(name)).intValue();
	}

	public void setIntField(String name, int value) throws MbException {
		setField(name, new Integer(value));
	}
}
