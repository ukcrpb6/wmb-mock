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

public abstract class MbElementWrapper {

    private MbElement wrappedElm;

    public MbElementWrapper(MbElement elm) throws MbException {
        this.wrappedElm = elm;
    }

    protected MbElement getMbElement() {
        return wrappedElm;
    }

    private Object getValue(String field) throws MbException {
        MbElement elm = getMbElement().getFirstElementByPath(field);
        if (elm != null) {
            return elm.getValue();
        } else {
            throw new NiceMbException("Property not found in MQMD: " + field);
        }
    }

    private void setValue(String field, Object value) throws MbException {
        MbElement elm = getMbElement().getFirstElementByPath(field);
        if (elm == null) {
            elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, field, null);
        }
        elm.setValue(value);
    }

    protected long getLongValue(String field) throws MbException {
        return (Long) getValue(field);
    }

    protected void setLongValue(String field, long value) throws MbException {
        setValue(field, value);
    }

    protected int getIntValue(String field) throws MbException {
        return (Integer) getValue(field);
    }

    protected void setIntValue(String field, int value) throws MbException {
        setValue(field, value);
    }

    protected String getStringValue(String field) throws MbException {
        return (String) getValue(field);
    }

    protected void setStringValue(String field, String value, int length) throws MbException {
        if (value.length() > length) {
            throw new NiceMbException("Value for field '" + field + "' to long, max length is " + length);
        } else {
            StringBuilder sb = new StringBuilder(length);
            sb.append(value);

            for (int i = value.length(); i < length; i++) {
                sb.append(' ');
            }
            setValue(field, sb.toString());
        }
    }

    protected void setStringValue(String field, String value) throws MbException {
        setValue(field, value);
    }

    protected byte[] getByteArrayValue(String field) throws MbException {
        return (byte[]) getValue(field);
    }

    protected void setByteArray(String field, byte[] value, int length) throws MbException {
        byte[] b;
        if (value.length > length) {
            throw new NiceMbException("Value for field '" + field + "' to long, max length is " + length);
        } else if (value.length < length) {
            b = new byte[length];
            System.arraycopy(value, 0, b, 0, value.length);
        } else {
            b = value;
        }

        setValue(field, b);
    }
}
