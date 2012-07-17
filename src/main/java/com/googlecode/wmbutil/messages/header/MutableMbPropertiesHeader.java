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

package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class MutableMbPropertiesHeader extends AbstractMbHeader implements MbPropertiesHeader {

    MutableMbPropertiesHeader(MbElement elm) throws MbException {
        super(elm, MbHeaderType.PROPERTIES);
    }

    @Override
    public String getMessageFormat() throws MbException {
        return getValue(Properties.FORMAT);
    }

    @Override
    public void setMessageFormat(String messageFormat) throws MbException {
        setValue(Properties.FORMAT, messageFormat);
    }

    @Override
    public String getMessageSet() throws MbException {
        return getValue(Properties.MSG_SET);
    }

    @Override
    public void setMessageSet(String messageSet) throws MbException {
        setValue(Properties.MSG_SET, messageSet);
    }

    @Override
    public String getMessageType() throws MbException {
        return getValue(Properties.MSG_TYPE);
    }

    @Override
    public void setMessageType(String messageType) throws MbException {
        setValue(Properties.MSG_TYPE, messageType);
    }

    @Override
    public String getTopic() throws MbException {
        return getValue(Properties.TOPIC);
    }

    @Override
    public void setTopic(String topic) throws MbException {
        setValue(Properties.TOPIC, topic);
    }
}
