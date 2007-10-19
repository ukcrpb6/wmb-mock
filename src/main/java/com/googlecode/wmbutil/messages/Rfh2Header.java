package com.googlecode.wmbutil.messages;

import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class Rfh2Header extends Header {
	private static final Logger LOG = Logger.getLogger(Rfh2Header.class);

	public static boolean hasRfh2Header(WMQMessage msg) throws MbException {
		MbElement elm = msg.getMbMessage().getRootElement().getFirstElementByPath("/MQRFH2");
		return elm != null;
	}
	
	private static MbElement locateHeader(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");
		
		if(elm !=  null) {
			LOG.debug("RFH2 header element found");
			return elm;
		} else {
			LOG.debug("RFH2 header element not found, trying to locate MQMD");
			MbElement mqmd = msg.getRootElement().getFirstElementByPath("/MQMD");
			
			if(mqmd != null) {
				LOG.debug("Found MQMD, creating RFH2 element after");
				elm = mqmd.createElementAfter("MQHRF2");
				 
				MbElement mqmdFormat = mqmd.getFirstElementByPath("Format");
				
				elm.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Format", mqmdFormat.getValue());
				mqmdFormat.setValue("MQHRF2  ");
				
				return elm;
			} else {
				throw new RuntimeException("Can not find MQMD");
			}
		}
		
	}
	
	public Rfh2Header(MbMessage msg, boolean readOnly) throws MbException {
		super(locateHeader(msg, readOnly), readOnly);
	}

	public String getStringProperty(String area, String name) throws MbException {
		MbElement valueElm = getMbElement().getFirstElementByPath(area + "/" + name);
		
		if(valueElm == null) {
			return null;
		} else {
			return valueElm.getValue().toString();
		}
		
	}

	public void setStringProperty(String area, String name, String value) throws MbException {
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
