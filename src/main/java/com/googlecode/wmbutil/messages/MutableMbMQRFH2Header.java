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

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class MutableMbMQRFH2Header extends Header implements MbMQRFH2Header {

    public class Properties {
        public static final String ENCODING = "Encoding";
        public static final String CODEDCHARSETID = "CodedCharSetId";
        public static final String FORMAT = "Format";
        public static final String FLAGS = "Flags";
        public static final String NAME_VALUE_CODEDCHARSETID = "NameValueCCSID";
    }

    MutableMbMQRFH2Header(MbElement elm) throws MbException {
        super(elm);
    }

    private Object getProperty(String area, String name) throws MbException {
        MbElement valueElm = getMbElement().getFirstElementByPath(area + "/" + name);
        return (valueElm != null) ? valueElm.getValue() : null;
    }

    private void setProperty(String area, String name, Object value) throws MbException {
        MbElement areaElm = getMbElement().getFirstElementByPath(area);
        if (areaElm == null) {
            areaElm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME);
            areaElm.setName(area);
        }

        MbElement valueElm = areaElm.getFirstElementByPath(name);

        if (valueElm == null) {
            areaElm.createElementAsLastChild(MbElement.TYPE_NAME, name, value);
        } else {
            valueElm.setValue(value);
        }
    }

    @Override
    public String getStringProperty(String area, String name) throws MbException {
        return (String) getProperty(area, name);
    }

    @Override
    public void setStringProperty(String area, String name, String value) throws MbException {
        setProperty(area, name, value);
    }


    @Override
    public int getIntProperty(String area, String name) throws MbException {
        return (Integer) getProperty(area, name);
    }

    @Override
    public void setIntProperty(String area, String name, int value) throws MbException {
        setProperty(area, name, value);
    }

    @Override
    public int getCodedCharSetId() throws MbException {
        return getIntValue(Properties.CODEDCHARSETID);
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        setIntValue(Properties.CODEDCHARSETID, codedCharSetId);
    }

    @Override
    public int getEncoding() throws MbException {
        return getIntValue(Properties.ENCODING);
    }

    @Override
    public void setEncoding(int encoding) throws MbException {
        setIntValue(Properties.ENCODING, encoding);
    }

    @Override
    public int getFlags() throws MbException {
        return getIntValue(Properties.FLAGS);
    }

    @Override
    public void setFlags(int flags) throws MbException {
        setIntValue(Properties.FLAGS, flags);
    }

    @Override
    public String getFormat() throws MbException {
        return getStringValue(Properties.FORMAT);
    }

    @Override
    public void setFormat(String format) throws MbException {
        setStringValue(Properties.FORMAT, format, 8);
    }

    @Override
    public int getNameValueCCSID() throws MbException {
        return getIntValue(Properties.NAME_VALUE_CODEDCHARSETID);
    }

    @Override
    public void setNameValueCCSID(int nameValueCCSID) throws MbException {
        setIntValue(Properties.NAME_VALUE_CODEDCHARSETID, nameValueCCSID);
    }
}
