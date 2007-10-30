package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class Rfh2Header extends Header {

	private static final String PROPERTY_ENCODING = "Encoding";
	private static final String PROPERTY_CODEDCHARSETID = "CodedCharSetId";
	private static final String PROPERTY_FORMAT = "Format";
	private static final String PROPERTY_FLAGS = "Flags";
	private static final String PROPERTY_NAME_VALUE_CODEDCHARSETID = "NameValueCCSID";

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

		if(MqmdHeader.has(msg)) {
			MqmdHeader mqmd = MqmdHeader.wrap(msg, false);
			mqmd.setFormat("MQHRF2  ");
			
			MbElement mqmdElm = mqmd.getMbElement();
			
			MbElement elm = mqmdElm.createElementAfter("MQHRF2");
			
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
	
	private Object getProperty(String area, String name) throws MbException {
		MbElement valueElm = getMbElement().getFirstElementByPath(area + "/" + name);
		
		if(valueElm == null) {
			return null;
		} else {
			return valueElm.getValue();
		}
		
	}
	
	private void setProperty(String area, String name, Object value) throws MbException {
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
	
	public String getStringProperty(String area, String name) throws MbException {
		return (String) getProperty(area, name);
	}
	
	public void setStringProperty(String area, String name, String value) throws MbException {
		setProperty(area, name, value);
	}
	
	
	public int getIntProperty(String area, String name) throws MbException {
		return ((Integer) getProperty(area, name)).intValue();
	}
	
	public void setIntProperty(String area, String name, int value) throws MbException {
		setProperty(area, name, new Integer(value));
	}
	
	/**
	 * @return Returns the codedCharSetId.
	 * @throws MbException
	 */
	public int getCodedCharSetId() throws MbException {
		return getIntValue(PROPERTY_CODEDCHARSETID);
	}
	/**
	 * @param codedCharSetId The codedCharSetId to set.
	 * @throws MbException
	 */
	public void setCodedCharSetId(int codedCharSetId) throws MbException {
		setIntValue(PROPERTY_CODEDCHARSETID, codedCharSetId);
	}
	/**
	 * @return Returns the encoding.
	 * @throws MbException
	 */
	public int getEncoding() throws MbException {
		return getIntValue(PROPERTY_ENCODING);
	}
	/**
	 * @param encoding The encoding to set.
	 * @throws MbException
	 */
	public void setEncoding(int encoding) throws MbException {
		setIntValue(PROPERTY_ENCODING, encoding);
	}
	/**
	 * @return Returns the flags.
	 * @throws MbException
	 */
	public int getFlags() throws MbException {
		return getIntValue(PROPERTY_FLAGS);
	}
	/**
	 * @param flags The flags to set.
	 * @throws MbException
	 */
	public void setFlags(int flags) throws MbException {
		setIntValue(PROPERTY_FLAGS, flags);
	}
	/**
	 * @return Returns the format.
	 * @throws MbException
	 */
	public String getFormat() throws MbException {
		return getStringValue(PROPERTY_FORMAT);
	}
	/**
	 * @param format The format to set.
	 * @throws MbException
	 */
	public void setFormat(String format) throws MbException {
		setStringValue(PROPERTY_FORMAT, format, 8);
	}
	/**
	 * @return Returns the nameValueCCSID.
	 * @throws MbException
	 */
	public int getNameValueCCSID() throws MbException {
		return getIntValue(PROPERTY_NAME_VALUE_CODEDCHARSETID);
	}
	/**
	 * @param nameValueCCSID The nameValueCCSID to set.
	 * @throws MbException
	 */
	public void setNameValueCCSID(int nameValueCCSID) throws MbException {
		setIntValue(PROPERTY_NAME_VALUE_CODEDCHARSETID, nameValueCCSID);
	}
}
