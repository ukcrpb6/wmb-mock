package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class Rfh2Header extends Header {

	public static Rfh2Header wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");

		if(elm == null) {
			throw new NiceMbException("Failed to find Rfh2Header");
		}
		
		return new Rfh2Header(elm, readOnly);
	}

	public static Rfh2Header create(MbMessage msg) throws MbException {
		if(has(msg)) {
			throw new NiceMbException("Already have RFH2 header");
		}

		MbElement elm;
		
		MbElement mqmd = msg.getRootElement().getFirstElementByPath("/MQMD");
		
		if(mqmd != null) {
			elm = mqmd.createElementAfter("MQHRF2");
			 
			MbElement mqmdFormat = mqmd.getFirstElementByPath("Format");
			
			elm.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Format", mqmdFormat.getValue());
			mqmdFormat.setValue("MQHRF2  ");
			
			return new Rfh2Header(elm, false);
		} else {
			throw new NiceMbException("Can not find MQMD");
		}

	}
	
	public static Rfh2Header wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	public static Rfh2Header remove(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");
		
		if(elm != null) {
			elm.detach();
			return new Rfh2Header(elm, true);
		} else {
			throw new NiceMbException("Failed to find Rfh2Header");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");
		return elm != null;
	}
	
	private Rfh2Header(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}

	public String getStringProperty(String area, String name) throws MbException {
		MbElement valueElm = getMbElement().getFirstElementByPath(area + "/" + name);
		
		if(valueElm == null) {
			return null;
		} else {
			return valueElm.getValue().toString();
		}
		
	}

	private void checkReadOnly() throws MbException {
		if(isReadOnly()) {
			throw new NiceMbException(this, "Message is read-only, can not be changed");
		}
	}
	
	public void setStringProperty(String area, String name, String value) throws MbException {
		checkReadOnly();
		
		MbElement areaElm = getMbElement().getFirstElementByPath(area);
		if(areaElm == null) {
			areaElm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME);
			areaElm.setName(area);
		}
		
		MbElement valueElm = areaElm.getFirstElementByPath(name);
		
		if(valueElm == null) {
			valueElm = areaElm.createElementAsLastChild(MbElement.TYPE_NAME, name, value);
		} else {
			valueElm.setValue(value);
		}
	}
}
