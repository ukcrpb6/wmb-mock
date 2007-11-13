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

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetEncoder;

import com.googlecode.wmbutil.CCSID;
import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class BlobPayload extends Payload {

	private static final String DEFAULT_PARSER = "NONE";
	
	public static BlobPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locateBlob(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find BLOB payload");
		}
		
		return new BlobPayload(elm, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static BlobPayload create(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild(DEFAULT_PARSER);
		
		MbElement blobElm = elm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", null);
		
		return new BlobPayload(blobElm, false);
	}

	public static BlobPayload wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	
	/** 
	 * Removes the first XML payload
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static BlobPayload remove(MbMessage msg) throws MbException {
		MbElement elm = locateBlob(msg);
		
		if(elm != null) {
			elm.detach();
			return new BlobPayload(elm, true);
		} else {
			throw new NiceMbException("Failed to find BLOB payload");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = locateBlob(msg);
		return elm != null;
	}
	
	private static MbElement locateBlob(MbMessage msg) throws MbException {
		return msg.getRootElement().getFirstElementByPath("/BLOB/BLOB");
	}
	
	private BlobPayload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}

	public byte[] getData() throws MbException {
		return (byte[]) getMbElement().getValue();
	}

	public void setData(byte[] b) throws MbException {
		getMbElement().setValue(b);
	}

	public String getDataAsString(int ccsid) throws MbException {
		String encoding = CCSID.ccsidToCharset(ccsid);
		try {
			return new String(getData(), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new NiceMbException("Encoding not supported " + encoding);
		}
	}

	public void setDataAsString(String s, int ccsid) throws MbException {
		System.out.println(ccsid);
		
		String encoding = CCSID.ccsidToCharset(ccsid);
		try {
			setData(s.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new NiceMbException("Encoding not supported " + encoding);
		}
	}

}