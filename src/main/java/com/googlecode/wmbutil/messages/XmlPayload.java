package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNS;

public class XmlPayload extends Payload {

	private static final String DEFAULT_PARSER = "XMLNS";
	
	private XmlElement docElm;
	
	public static XmlPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locateXmlBody(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find XML payload");
		}
		
		return new XmlPayload(elm, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static XmlPayload create(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild(DEFAULT_PARSER);
		return new XmlPayload(elm, false);
	}
	
	public static XmlPayload wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	/** 
	 * Removes the first XML payload
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static XmlPayload remove(MbMessage msg) throws MbException {
		MbElement elm = locateXmlBody(msg);
		
		if(elm != null) {
			elm.detach();
			return new XmlPayload(elm, true);
		} else {
			throw new NiceMbException("Failed to find XML payload");
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

		return elm;
	}
	
	private XmlPayload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);

		MbElement child = getMbElement().getFirstChild();
		
		while(child != null) {
			// find first and only element
			if(child.getType() == MbXMLNS.ELEMENT) {
				docElm = new XmlElement(child, isReadOnly());
			}
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
		
		MbElement elm = getMbElement().createElementAsLastChild(XmlUtil.getFolderElementType(getMbElement()));
		elm.setName(name);
		if(ns != null) {
			elm.setNamespace(ns);
		}
		
		docElm = new XmlElement(elm, isReadOnly());
		
		return docElm;
	}

	public void declareNamespace(String prefix, String ns) throws MbException {
		checkReadOnly();
		
		docElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns).setNamespace("xmlns");
	}
}
