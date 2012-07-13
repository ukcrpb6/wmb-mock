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

public class MutableMbPropertiesHeader extends Header implements MbPropertiesHeader {

    MutableMbPropertiesHeader(MbElement elm) throws MbException {
        super(elm);
    }

    @Override
    public String getMessageFormat() throws MbException {
        return getStringValue(Properties.FORMAT);
    }

    @Override
    public void setMessageFormat(String messageFormat) throws MbException {
        setStringValue(Properties.FORMAT, messageFormat);
    }

    @Override
    public String getMessageSet() throws MbException {
        return getStringValue(Properties.MSG_SET);
    }

    @Override
    public void setMessageSet(String messageSet) throws MbException {
        setStringValue(Properties.MSG_SET, messageSet);
    }

    @Override
    public String getMessageType() throws MbException {
        return getStringValue(Properties.MSG_TYPE);
    }

    @Override
    public void setMessageType(String messageType) throws MbException {
        setStringValue(Properties.MSG_TYPE, messageType);
    }

    @Override
    public String getTopic() throws MbException {
        return getStringValue(Properties.TOPIC);
    }

    @Override
    public void setTopic(String topic) throws MbException {
        setStringValue(Properties.TOPIC, topic);
    }
}
