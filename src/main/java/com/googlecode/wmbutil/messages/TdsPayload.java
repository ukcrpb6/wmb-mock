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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * Helper class for working with typical flat file data. This class assumes you 
 * have a message containing a set of records, each with a number of fields.
 */
public class TdsPayload extends Payload {

	/**
	 * Wrap an message containing a flat file message with the helper class.
	 * Automatically locates the MRM tree.
	 * 
	 * @param msg The message to wrap.
	 * @param readOnly Indicates whether the message is read-only (input message) 
	 * 	   or not.
	 * @return The helper class
	 * @throws MbException
	 */
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
	 * @param msg The message on which the payload should be created
	 * @return The helper class
	 * @throws MbException
	 */
    public static TdsPayload create(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().createElementAsLastChild("MRM");
        return new TdsPayload(elm, false);
    }

	/**
	 * Wraps if payload already exists, of creates payload otherwise.
	 * 
	 * @param msg The message on which to wrap the payload
	 * @return The helper class
	 * @throws MbException
	 */
    public static TdsPayload wrapOrCreate(MbMessage msg) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg);
        }
    }

	/** 
	 * Removes (detaches) the first MRM payload
	 * 
	 * @param msg The message on which to remove the payload
	 * @return The helper class for the removed payload
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

	/**
	 * Checks if the message has a payload of this type
	 * 
	 * @param msg The message to check
	 * @return true if the payload exists, false otherwise.
	 * @throws MbException
	 */
    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = locatePayload(msg);
        return elm != null;
    }

    /**
     * Locates the payload in a message
     * 
     * @param msg
     * @return
     * @throws MbException
     */
    private static MbElement locatePayload(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/MRM");

        return elm;
    }
   
    /**
     * Class constructor
     * 
     * @param elm The message element
     * @param readOnly Specifies Whether the payload is read only or not
     * @throws MbException
     */
    private TdsPayload(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);

    }

    /**
     * Create a record with the specfied name as the last
     * child in the message
     * 
     * @param name The name of the record to create
     * @return The created record
     * @throws MbException
     */
    public TdsRecord createRecord(String name) throws MbException {
        checkReadOnly();

        MbElement elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME, name, null);

        return new TdsRecord(elm, isReadOnly());
    }

    /**
     * Get all records with the specified name in the order they are 
     * located in the message.
     * 
     * @param name The name of the records to retrieve
     * @return The list of records
     * @throws MbException
     */
    public List getRecords(String name) throws MbException {
        List elms = (List) getMbElement().evaluateXPath(name);
        List records = new ArrayList();
        for (int i = 0; i < elms.size(); i++) {
            records.add(new TdsRecord((MbElement) elms.get(i), isReadOnly()));
        }
        return records;
    }

    /**
     * Get all records in the order they are located in the message
     * 
     * @return List of all records
     * @throws MbException
     */
    public List getAllRecords() throws MbException {
        List elms = (List) getMbElement().evaluateXPath("*");
        List records = new ArrayList();
        for (int i = 0; i < elms.size(); i++) {
            records.add(new TdsRecord((MbElement) elms.get(i), isReadOnly()));
        }
        return records;
    }
}
