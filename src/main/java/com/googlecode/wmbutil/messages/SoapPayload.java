/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.util.ElementUtil;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNS;

public class SoapPayload extends Payload {

	private static final String NS_SOAP_ENV = "http://www.w3.org/2001/12/soap-envelope";
	private static final String DEFAULT_PARSER = "XMLNS";
	
	private XmlElement envElm;
	private XmlElement bodyElm;
	private XmlElement docElm;
	
	public static SoapPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locateXmlBody(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find SOAP payload");
		}
		
		return new SoapPayload(elm, false, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static SoapPayload create(MbMessage msg) throws MbException {
		return create(msg, DEFAULT_PARSER);
	}

	public static SoapPayload create(MbMessage msg, String parser) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild(parser);
		return new SoapPayload(elm, true, false);
	}
	
	public static SoapPayload wrapOrCreate(MbMessage msg) throws MbException {
		return wrapOrCreate(msg, DEFAULT_PARSER);
	}

	public static SoapPayload wrapOrCreate(MbMessage msg, String parser) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg, parser);
		}
	}

	
	/** 
	 * Removes the first XML payload
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static SoapPayload remove(MbMessage msg) throws MbException {
		MbElement elm = locateXmlBody(msg);
		
		if(elm != null) {
			elm.detach();
			return new SoapPayload(elm, false, true);
		} else {
			throw new NiceMbException("Failed to find SOAP payload");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = locateXmlBody(msg);
		return elm != null;
	}
	
	private static MbElement locateXmlBody(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/XMLNSC");

		if(elm == null) {
			elm = msg.getRootElement().getFirstElementByPath("/XMLNS");
		}
		if(elm == null) {
			elm = msg.getRootElement().getFirstElementByPath("/XML");
		}
		if(elm == null) {
			elm = msg.getRootElement().getFirstElementByPath("/MRM");
		}

		return elm;
	}
	
	private SoapPayload(MbElement elm, boolean create, boolean readOnly) throws MbException {
		super(elm, readOnly);

		if(!create) {
			if(ElementUtil.isMRM(getMbElement())) {
				envElm = new XmlElement(getMbElement(), isReadOnly());
			} else {
				MbElement child = getMbElement().getFirstChild();
				
				while(child != null) {
					// find first and only element
					if(child.getType() == XmlUtil.getFolderElementType(child)) {
						envElm = new XmlElement(child, isReadOnly());
						break;
					}
					
					child = child.getNextSibling();
				}
				
				bodyElm = envElm.getFirstChildByName(NS_SOAP_ENV, "Body");
				docElm = bodyElm.getFirstChild();
			}
		} else  {
			if(ElementUtil.isMRM(getMbElement())) {
				// skip first level
				envElm = new XmlElement(elm, readOnly);
			} else {
				MbElement tmpElm = elm.createElementAsLastChild(XmlUtil.getFolderElementType(elm), "Envelope", null);
				tmpElm.setNamespace(NS_SOAP_ENV);
				envElm = new XmlElement(tmpElm, readOnly);
			}
		
			bodyElm = envElm.createLastChild(NS_SOAP_ENV, "Body");
		}

		if(!isReadOnly()) {
			// declare SOAP namespace
			declareNamespace("soap", NS_SOAP_ENV);
		}
	
	}

	public XmlElement getDocumentElement() {
		return docElm;
	}

	public XmlElement createDocumentElement(String name) throws MbException {
		return createDocumentElement(null, name);
	}

	public XmlElement createDocumentElement(String ns, String name) throws MbException {
		checkReadOnly();
		
		docElm = bodyElm.createLastChild(ns, name);
		
		return docElm;
	}

	public void declareNamespace(String prefix, String ns) throws MbException {
		checkReadOnly();
		
		if(ElementUtil.isXML(envElm.getMbElement()) ||
				ElementUtil.isXMLNS(envElm.getMbElement()) ||
				ElementUtil.isXMLNSC(envElm.getMbElement())) {
			envElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns).setNamespace("xmlns");
		}
	}
}
