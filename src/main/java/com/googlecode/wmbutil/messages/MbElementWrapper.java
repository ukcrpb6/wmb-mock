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

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.util.ElementUtil;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public abstract class MbElementWrapper {

    private MbElement wrappedElm;

    public MbElementWrapper(MbElement elm) {
        this.wrappedElm = elm;
    }

    public MbElement getMbElement() {
        return wrappedElm;
    }

    public <T> T getValue(String field) throws MbException {
        MbElement elm = getMbElement().getFirstElementByPath(field);
        if (elm != null) {
            //noinspection unchecked
            return (T) elm.getValue();
        } else {
            throw new NiceMbException("Property not found " + field);
        }
    }

    /**
     * Gets the value
     *
     * @return T representation of object value
     * @throws MbException
     */
    public <T> Optional<T> getValue() throws MbException {
        //noinspection unchecked
        return Optional.fromNullable((T) getMbElement().getValue());
    }

    /**
     * Sets the value
     *
     * @param value Element value
     * @throws MbException
     */
    public <T> void setValue(T value) throws MbException {
        getMbElement().setValue(value);
    }

    public <T> void setValue(String field, T value) throws MbException {
        MbElement elm = getMbElement().getFirstElementByPath(field);
        if (elm == null) {
            elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, field, null);
        }
        elm.setValue(value);
    }

    protected void setFixedStringValue(String field, String value, int length) throws MbException {
        if (value.length() > length) {
            throw new NiceMbException("Value for field '%s' to long, max length is %d", field, length);
        } else {
            setValue(field, Strings.padEnd(value, length, ' '));
        }
    }

    protected void setFixedByteArrayValue(String field, byte[] value, int length) throws MbException {
        byte[] b;
        if (value.length > length) {
            throw new NiceMbException("Value for field '%s' to long, max length is %d", field, length);
        } else if (value.length < length) {
            b = new byte[length];
            System.arraycopy(value, 0, b, 0, value.length);
        } else {
            b = value;
        }
        setValue(field, b);
    }
}
