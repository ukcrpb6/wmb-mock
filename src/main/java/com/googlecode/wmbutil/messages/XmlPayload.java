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
import com.googlecode.wmbutil.util.WmbUtilDebug;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNS;
import com.ibm.broker.plugin.MbXMLNSC;

/**
 * Helper class for working with XML messages.
 * 
 */
public class XmlPayload extends Payload {

    private static final String DEFAULT_PARSER = "XMLNS";

    private XmlElement docElm;

    public static XmlPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
        MbElement elm = locateXmlBody(msg);

        if (elm == null) {
            throw new NiceMbException("Failed to find XML payload");
        }

        return new XmlPayload(elm, readOnly);
    }

    /**
     * Creates a payload as the last child, even if one already exists
     * 
     * @param msg
     * @return
     * @throws MbException
     */
    public static XmlPayload create(MbMessage msg) throws MbException {
        return create(msg, DEFAULT_PARSER);
    }

    public static XmlPayload create(MbMessage msg, String parser) throws MbException {
        MbElement elm = msg.getRootElement().createElementAsLastChild(parser);
        return new XmlPayload(elm, false);
    }

    public static XmlPayload wrapOrCreate(MbMessage msg) throws MbException {
        return wrapOrCreate(msg, DEFAULT_PARSER);
    }

    public static XmlPayload wrapOrCreate(MbMessage msg, String parser) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg, parser);
        }
    }

    /**
     * Removes the first XML payload
     * 
     * @param msg
     * @return
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

    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = locateXmlBody(msg);
        return elm != null;
    }

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

    private XmlPayload(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);

        if (ElementUtil.isMRM(getMbElement())) {
            docElm = new XmlElement(getMbElement(), isReadOnly());
        } else {
            MbElement child = getMbElement().getFirstChild();

            while (child != null) {
                // find first and only element
                if (XmlUtil.isFolderElementType(child)) {
                    docElm = new XmlElement(child, isReadOnly());
                    break;
                }

                child = child.getNextSibling();
            }
        }
    }

    public XmlElement getRootElement() {
        return docElm;
    }

    public XmlElement createRootElement(String name) throws MbException {
        return createRootElement(null, name);
    }

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

    public void declareNamespace(String prefix, String ns) throws MbException {
        checkReadOnly();

        if (ElementUtil.isXML(docElm.getMbElement()) || ElementUtil.isXMLNS(docElm.getMbElement())
                || ElementUtil.isXMLNSC(docElm.getMbElement())) {
            docElm.getMbElement().createElementAsFirstChild(MbXMLNS.NAMESPACE_DECL, prefix, ns)
                    .setNamespace("xmlns");
        }
    }
}
