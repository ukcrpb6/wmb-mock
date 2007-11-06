package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class PropertiesHeader extends Header {

	private static final String PARSER_NAME = "MQPROPERTYPARSER";

	private static final String PROPERTY_MSG_SET = "MessageSet";
	private static final String PROPERTY_MSG_TYPE = "MessageType";
	private static final String PROPERTY_FORMAT = "MessageFormat";

   	
	public static PropertiesHeader wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/Properties");

		if(elm == null) {
			throw new NiceMbException("Failed to find Properties");
		}
		
		return new PropertiesHeader(elm, readOnly);
	}

	public static PropertiesHeader create(MbMessage msg) throws MbException {
		if(has(msg)) {
			throw new NiceMbException("Already have Properties header");
		}

		MbElement elm = msg.getRootElement().createElementAsLastChild(PARSER_NAME);
		
		PropertiesHeader properties = new PropertiesHeader(elm, false); 
		
		
		return properties;
	}
	
	public static PropertiesHeader wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	public static PropertiesHeader remove(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/Properties");
		
		if(elm != null) {
			elm.detach();
			return new PropertiesHeader(elm, true);
		} else {
			throw new NiceMbException("Failed to find Properties header");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/Properties");
		return elm != null;
	}
	
	private PropertiesHeader(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}

	public String getMessageFormat() throws MbException {
		return getStringValue(PROPERTY_FORMAT);
	}
	public void setMessageFormat(String messageFormat) throws MbException {
		setStringValue(PROPERTY_FORMAT, messageFormat);
	}
	public String getMessageSet() throws MbException {
		return getStringValue(PROPERTY_MSG_SET);
	}
	public void setMessageSet(String messageSet) throws MbException {
		setStringValue(PROPERTY_MSG_SET, messageSet);
	}
	public String getMessageType() throws MbException {
		return getStringValue(PROPERTY_MSG_TYPE);
	}
	public void setMessageType(String messageType) throws MbException {
		setStringValue(PROPERTY_MSG_TYPE, messageType);
	}
}

