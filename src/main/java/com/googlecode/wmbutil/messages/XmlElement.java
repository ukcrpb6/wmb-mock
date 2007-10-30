package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXMLNS;

public class XmlElement extends MbElementWrapper {

	public XmlElement(MbElement wrappedElm, boolean readOnly) throws MbException {
		super(wrappedElm, readOnly);
	}
	
	public String getAttribute(String name) {
		return null;
	}

	public String getAttribute(String ns, String name) {
		return null;
	}
	
	public void setAttribute(String name, String value) throws MbException {
		MbElement attr = getMbElement().getFirstElementByPath("@" + name);
		
		if(attr == null) {
			attr = getMbElement().createElementAsFirstChild(XmlUtil.getAttributeType(getMbElement()), name, value);
		} else {
			attr.setValue(value);
		}
	}

	public void setAttribute(String ns, String name, String value) {
	}

	public String[] getAttributeNames() {
		return null;
	}
	
	public XmlElement createLastChild(String name) throws MbException {
		MbElement elm = getMbElement().createElementAsLastChild(MbXMLNS.ELEMENT, name, null);
		return new XmlElement(elm, isReadOnly());
	}

	public XmlElement createLastChild(String ns, String name) {
		return null;
	}
	
	public XmlElement getElementByXPath(String xpath) {
		return null;
	}

	public XmlElement[] getElementsByXPath(String xpath) {
		return null;
	}

}
