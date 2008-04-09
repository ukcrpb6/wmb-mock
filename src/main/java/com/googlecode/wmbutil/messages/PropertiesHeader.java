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
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class PropertiesHeader extends Header {

    public static final String PARSER_MQ_PROPERTIES = "MQPROPERTYPARSER";
    public static final String PARSER_WS_PROPERTIES = "WSPROPERTYPARSER";

    private static final String ELEMENT_NAME = "Properties";

    private static final String PROPERTY_MSG_SET = "MessageSet";
    private static final String PROPERTY_MSG_TYPE = "MessageType";
    private static final String PROPERTY_FORMAT = "MessageFormat";
    private static final String PROPERTY_TOPIC = "Topic";

    public static PropertiesHeader wrap(MbMessage msg, boolean readOnly) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);

        if (elm == null) {
            throw new NiceMbException("Failed to find " + ELEMENT_NAME);
        }

        return new PropertiesHeader(elm, readOnly);
    }

    public static PropertiesHeader create(MbMessage msg) throws MbException {
        return create(msg, PARSER_MQ_PROPERTIES);
    }

    public static PropertiesHeader create(MbMessage msg, String parser) throws MbException {
        if (has(msg)) {
            throw new NiceMbException("Already have " + ELEMENT_NAME + " header");
        }

        MbElement elm = msg.getRootElement().createElementAsLastChild(parser);

        PropertiesHeader properties = new PropertiesHeader(elm, false);

        return properties;
    }

    public static PropertiesHeader wrapOrCreate(MbMessage msg) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg);
        }
    }

    public static PropertiesHeader remove(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);

        if (elm != null) {
            elm.detach();
            return new PropertiesHeader(elm, true);
        } else {
            throw new NiceMbException("Failed to find " + ELEMENT_NAME + " header");
        }
    }

    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);
        return elm != null;
    }

    private PropertiesHeader(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);
    }

    public String getMessageFormat() throws MbException {
        return getStringValue(PROPERTY_FORMAT);
    }

    public void setMessageFormat(String messageFormat) throws MbException {
        setStringValue(PROPERTY_FORMAT, messageFormat);
    }

    public String getMessageSet() throws MbException {
        return getStringValue(PROPERTY_MSG_SET);
    }

    public void setMessageSet(String messageSet) throws MbException {
        setStringValue(PROPERTY_MSG_SET, messageSet);
    }

    public String getMessageType() throws MbException {
        return getStringValue(PROPERTY_MSG_TYPE);
    }

    public void setMessageType(String messageType) throws MbException {
        setStringValue(PROPERTY_MSG_TYPE, messageType);
    }

    public String getTopic() throws MbException {
        return getStringValue(PROPERTY_TOPIC);
    }

    public void setTopic(String topic) throws MbException {
        setStringValue(PROPERTY_TOPIC, topic);
    }
}
