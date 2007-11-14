/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.wmbutil.messages;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class TdsPayload extends Payload {

    public static TdsPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
        MbElement elm = locatePayload(msg);

        if (elm == null) {
            throw new NiceMbException("Failed to find CSV payload");
        }

        return new TdsPayload(elm, readOnly);
    }

    /**
     * Creates a payload as the last child, even if one already exists
     * 
     * @param msg
     * @return
     * @throws MbException
     */
    public static TdsPayload create(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().createElementAsLastChild("MRM");
        return new TdsPayload(elm, false);
    }

    public static TdsPayload wrapOrCreate(MbMessage msg) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg);
        }
    }

    /**
     * Removes the first XML payload
     * 
     * @param msg
     * @return
     * @throws MbException
     */
    public static TdsPayload remove(MbMessage msg) throws MbException {
        MbElement elm = locatePayload(msg);

        if (elm != null) {
            elm.detach();
            return new TdsPayload(elm, true);
        } else {
            throw new NiceMbException("Failed to find XML payload");
        }
    }

    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = locatePayload(msg);
        return elm != null;
    }

    private static MbElement locatePayload(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/MRM");

        return elm;
    }

    private TdsPayload(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);

    }

    public TdsRecord createRecord(String name) throws MbException {
        checkReadOnly();

        MbElement elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME, name, null);

        return new TdsRecord(elm, isReadOnly());
    }

    public List getRecords(String name) throws MbException {
        List elms = (List) getMbElement().evaluateXPath(name);
        List records = new ArrayList();
        for (int i = 0; i < elms.size(); i++) {
            records.add(new TdsRecord((MbElement) elms.get(i), isReadOnly()));
        }
        return records;
    }

}
