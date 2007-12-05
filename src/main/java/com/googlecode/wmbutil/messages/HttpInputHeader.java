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

public class HttpInputHeader extends Header {

	private static final String ELEMENT_NAME = "Properties";
	private static final String PARSER_NAME = "PropertyParser";

	private static final String PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String PROPERTY_CONTENT_LENGHT = "Content-Length";
	private static final String PROPERTY_HOST = "Host";

   	
	public static HttpInputHeader wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);

		if(elm == null) {
			throw new NiceMbException("Failed to find " + ELEMENT_NAME);
		}
		
		return new HttpInputHeader(elm, readOnly);
	}

	public static HttpInputHeader create(MbMessage msg) throws MbException {
		if(has(msg)) {
			throw new NiceMbException("Already have " + ELEMENT_NAME + " header");
		}

		MbElement elm = msg.getRootElement().createElementAsLastChild(PARSER_NAME);
		
		HttpInputHeader properties = new HttpInputHeader(elm, false); 
		
		
		return properties;
	}
	
	public static HttpInputHeader wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	public static HttpInputHeader remove(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);
		
		if(elm != null) {
			elm.detach();
			return new HttpInputHeader(elm, true);
		} else {
			throw new NiceMbException("Failed to find " + ELEMENT_NAME + " header");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/" + ELEMENT_NAME);
		return elm != null;
	}
	
	private HttpInputHeader(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}

	public String getContentType() throws MbException {
		return getStringValue(PROPERTY_CONTENT_TYPE);
	}
	public void setContentType(String contentType) throws MbException {
		setStringValue(PROPERTY_CONTENT_TYPE, contentType);
	}
	public int getContentLength() throws MbException {
		return Integer.parseInt(getStringValue(PROPERTY_CONTENT_LENGHT));
	}
	public void setContentLength(int contentLength) throws MbException {
		setStringValue(PROPERTY_CONTENT_LENGHT, Integer.toString(contentLength));
	}
	public String getHost() throws MbException {
		return getStringValue(PROPERTY_HOST);
	}
	public void setHost(String host) throws MbException {
		setStringValue(PROPERTY_HOST, host);
	}
	
	public String getHttpHeader(String name) throws MbException {
		return getStringValue(name);
	}

	public void setHttpHeader(String name, String value) throws MbException {
		setStringValue(name, value);
	}
}

