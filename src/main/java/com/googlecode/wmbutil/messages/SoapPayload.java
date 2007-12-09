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

/**
 * Helper class for working with SOAP messages. If automatically handles the
 * required Envelope and Body elements. A developer typically
 * only needs to deal with the document element (the element inside of the 
 * Body element).
 */
public class SoapPayload extends Payload {

	private static final String NS_SOAP_ENV = "http://www.w3.org/2001/12/soap-envelope";
	private static final String DEFAULT_PARSER = "XMLNS";
	
	private XmlElement envElm;
	private XmlElement bodyElm;
	private XmlElement docElm;

	/**
	 * Wrap an message containing a SOAP message with the helper class.
	 * Automatically locates the XML tree.
	 * @param msg The message to wrap.
	 * @param readOnly Indicates whether the message is read-only (input message) 
	 * 	   or not.
	 * @return The helper class
	 * @throws MbException
	 */
	public static SoapPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locateXmlBody(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find SOAP payload");
		}
		
		return new SoapPayload(elm, false, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg The message on which the payload should be created
	 * @return The helper class
	 * @throws MbException
	 */
	public static SoapPayload create(MbMessage msg) throws MbException {
		return create(msg, DEFAULT_PARSER);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg The message on which the payload should be created
	 * @param parser The name of the parser to use, for exampel XMSNS or MRM.
	 * @return The helper class
	 * @throws MbException
	 */
	public static SoapPayload create(MbMessage msg, String parser) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild(parser);
		return new SoapPayload(elm, true, false);
	}
	
	/**
	 * Wraps if payload already exists, of creates payload otherwise.
	 * @param msg The message on which to wrap the payload
	 * @return The helper class
	 * @throws MbException
	 */
	public static SoapPayload wrapOrCreate(MbMessage msg) throws MbException {
		return wrapOrCreate(msg, DEFAULT_PARSER);
	}

	/**
	 * Wraps if payload already exists, of creates payload otherwise.
	 * @param msg The message on which to wrap the payload
	 * @param parser The name of the parser to use, for exampel XMSNS or MRM.
	 * @return The helper class
	 * @throws MbException
	 */
	public static SoapPayload wrapOrCreate(MbMessage msg, String parser) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg, parser);
		}
	}

	
	/** 
	 * Removes the first SOAP payload
	 * @param msg The message on which to remove the payload
	 * @return The helper class for the removed payload
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

	/**
	 * Checks if the message has a payload of this type
	 * @param msg The message to check
	 * @return true if the payload exists, false otherwise.
	 * @throws MbException
	 */
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

	/**
	 * Get the XML element for the part inside the SOAP Body element
	 * @return The XML element inside of the SOAP Body, or null if such an element
	 *   does not exist ({@link #createDocumentElement(String)}
	 */
	public XmlElement getDocumentElement() {
		return docElm;
	}

	/**
	 * Create the XML element for the part inside the SOAP Body element
	 * @param name The local name to use for the element
	 * @return The XML element inside of the SOAP Body
	 * @throws MbException
	 */
	public XmlElement createDocumentElement(String name) throws MbException {
		return createDocumentElement(null, name);
	}

	/**
	 * Create the XML element for the part inside the SOAP Body element
	 * @param name The local name to use for the element
	 * @param ns The namespace URI to use for the element
	 * @return The XML element inside of the SOAP Body
	 * @throws MbException
	 */
	public XmlElement createDocumentElement(String ns, String name) throws MbException {
		checkReadOnly();
		
		docElm = bodyElm.createLastChild(ns, name);
		
		return docElm;
	}

	/**
	 * Declare a nice prefix for a namespace URI. If not called, WMB will generate a prefix automatically.
	 * @param prefix The prefix to use for the namespace
	 * @param ns The namespace URI
	 * @throws MbException
	 */
	public void declareNamespace(String prefix, String ns) throws MbException {
		checkReadOnly();
		
		if(ElementUtil.isXML(envElm.getMbElement()) ||
				ElementUtil.isXMLNS(envElm.getMbElement()) ||
				ElementUtil.isXMLNSC(envElm.getMbElement())) {
			envElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns).setNamespace("xmlns");
		}
	}
}
