package com.googlecode.wmbutil.messages;

import org.apache.log4j.Logger;

import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNS;

public class XmlBody extends Body {
	private static final Logger LOG = Logger.getLogger(XmlBody.class);

	private XmlElement docElm;
	
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
