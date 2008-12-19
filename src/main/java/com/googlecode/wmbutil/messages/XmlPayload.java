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
 * Helper class for working with XML messages.
 * 
 */
public class XmlPayload extends Payload {

    private static final String DEFAULT_PARSER = "XMLNS";

    private XmlElement docElm;

    /**
     * Wraps a payload
     * 
     * @param msg The message containing the XML payload
     * @param readOnly Specifies whether the payload will be wrapped as read only or not.
     * @return XML payload found in the message
     * @throws MbException
     */
    public static XmlPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
        MbElement elm = locateXmlBody(msg);

        if (elm == null) {
            throw new NiceMbException("Failed to find XML payload");
        }

        return new XmlPayload(elm, readOnly);
    }

    /**
     * Creates an XMLNS payload as the last child, even if one already exists
     * 
     * @param msg The message where to create an XML payload
     * @return A newly created XML payload
     * @throws MbException
     */
    public static XmlPayload create(MbMessage msg) throws MbException {
        return create(msg, DEFAULT_PARSER);
    }
    
    /**
     * Creates a payload as the last child, even if one already exists
     * 
     * @param msg The message where to create an XML payload
     * @param parser Specifies the payload parser
     * @return A newly created XML payload
     * @throws MbException
     */
    public static XmlPayload create(MbMessage msg, String parser) throws MbException {
        MbElement elm = msg.getRootElement().createElementAsLastChild(parser);
        return new XmlPayload(elm, false);
    }

    /**
     * Wraps or creates a payload as the last child, even if one already exists
     * 
     * @param msg The message where to look for/create an XML payload
     * @return An XML payload, existent or newly created
     * @throws MbException
     */
    public static XmlPayload wrapOrCreate(MbMessage msg) throws MbException {
        return wrapOrCreate(msg, DEFAULT_PARSER);
    }

    /**
     * Wraps or creates a payload as the last child, even if one already exists
     * 
     * @param msg The message where to look for/create an XML payload
     * @param parser Specifies the parser when creating a new payload
     * @return An XML payload, existent or newly created
     * @throws MbException
     */    
    public static XmlPayload wrapOrCreate(MbMessage msg, String parser) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg, parser);
        }
    }

    /**
     * Removes (detaches) the first XML payload
     * 
     * @param msg The message containing the XML payload
     * @return The detached payload
     * @throws MbException
     */
    public static XmlPayload remove(MbMessage msg) throws MbException {
        MbElement elm = locateXmlBody(msg);

        if (elm != null) {
            elm.detach();
            return new XmlPayload(elm, true);
        } else {
            throw new NiceMbException("Failed to find XML payload");
        }
    }
    
    /**
     * Checks if a message contains an XML payload
     * 
     * @param msg The message to check
     * @return True if there's an XML payload in the message
     * @throws MbException
     */
    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = locateXmlBody(msg);
        return elm != null;
    }

    /**
     * Locates the XML body in a message
     * 
     * @param msg The message to check
     * @return The XML body element of the message 
     * @throws MbException
     */
    private static MbElement locateXmlBody(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/XMLNSC");

        if (elm == null) {
            elm = msg.getRootElement().getFirstElementByPath("/XMLNS");
        }
        if (elm == null) {
            elm = msg.getRootElement().getFirstElementByPath("/XML");
        }
        if (elm == null) {
            elm = msg.getRootElement().getFirstElementByPath("/MRM");
        }

        return elm;
    }

    /**
     * Class constructor
     * 
     * @param elm 
     * @param readOnly Specifies whether the payload is readonly 
     * @throws MbException
     */
    private XmlPayload(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);

        if (ElementUtil.isMRM(getMbElement())) {
            docElm = new XmlElement(getMbElement(), isReadOnly());
        } else {
            MbElement child = getMbElement().getFirstChild();

            while (child != null) {
                // find first and only element
                if (XmlUtil.isElement(child)) {
                    docElm = new XmlElement(child, isReadOnly());
                    break;
                }

                child = child.getNextSibling();
            }
        }
    }

    /**
     * Returns the root element
     * 
     * @return The root element 
     * @throws MbException
     */
    public XmlElement getRootElement() {
        return docElm;
    }

    /**
     * Creates a root element (without a namespace)
     * 
     * @param name The root element name 
     * @return The root element 
     * @throws MbException
     */
    public XmlElement createRootElement(String name) throws MbException {
        return createRootElement(null, name);
    }

    /**
     * Creates a root element (with a namespace)
     * 
     * @param ns Element name space
     * @param name The root element name 
     * @return The root element 
     * @throws MbException
     */    
    public XmlElement createRootElement(String ns, String name) throws MbException {
        checkReadOnly();

        MbElement elm;
        if (ElementUtil.isMRM(getMbElement())) {
            // for MRM, don't generate a root element
            elm = getMbElement();
        } else {
            elm = getMbElement().createElementAsLastChild(
                    XmlUtil.getFolderElementType(getMbElement()));
            elm.setName(name);
            if (ns != null) {
                elm.setNamespace(ns);
            }
        }
        docElm = new XmlElement(elm, isReadOnly());

        return docElm;
    }

    /**
     * Declares a namespace in the root element
     * 
     * @param prefix What prefix to use 
     * @param ns The namespace string 
     * @throws MbException
     */
    public void declareNamespace(String prefix, String ns) throws MbException {
        checkReadOnly();

        if (ElementUtil.isXML(docElm.getMbElement()) || ElementUtil.isXMLNS(docElm.getMbElement())
                || ElementUtil.isXMLNSC(docElm.getMbElement())) {
            docElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns)
                    .setNamespace("xmlns");
        }
    }
}
