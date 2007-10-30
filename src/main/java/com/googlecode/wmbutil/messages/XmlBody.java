package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNS;

public class XmlBody extends Body {

	private XmlElement docElm;
	
	public static XmlBody wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");

		if(elm == null) {
			throw new NiceMbException("Failed to find Rfh2Header");
		}
		
		return new XmlBody(elm, readOnly);
	}

	public static XmlBody create(MbMessage msg) throws MbException {
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
			
			return new XmlBody(elm, false);
		} else {
			throw new NiceMbException("Can not find MQMD");
		}

	}
	
	public static XmlBody wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	public static XmlBody remove(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");
		
		if(elm != null) {
			elm.detach();
			return new XmlBody(elm, true);
		} else {
			throw new NiceMbException("Failed to find Rfh2Header");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MQRFH2");
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
			elm = msg.getRootElement().createElementAsLastChild("XMLNS");
		}
		
		return elm;
	}
	
	public XmlBody(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);

		MbElement child = getMbElement().getFirstChild();
		
		while(child != null) {
			// find first and only element
			if(child.getType() == MbXMLNS.ELEMENT) {
				docElm = new XmlElement(child, isReadOnly());
			}
		}
	}
	
	public XmlBody(MbMessage msg, boolean readOnly) throws MbException {
		this(locateXmlBody(msg), readOnly);
	}

	public XmlElement getDocumentElement() {
		return docElm;
	}

	public XmlElement createDocumentElement(String name) throws MbException {
		return createDocumentElement(null, name);
	}

	public XmlElement createDocumentElement(String ns, String name) throws MbException {
		MbElement elm = getMbElement().createElementAsLastChild(XmlUtil.getFolderElementType(getMbElement()));
		elm.setName(name);
		if(ns != null) {
			elm.setNamespace(ns);
		}
		
		docElm = new XmlElement(elm, isReadOnly());
		
		return docElm;
	}

	public void declareNamespace(String prefix, String ns) throws MbException {
		docElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns).setNamespace("xmlns");
	}
}
