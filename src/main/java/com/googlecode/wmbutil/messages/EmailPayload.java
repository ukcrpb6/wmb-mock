/*
 * Copyright 2009 (C) Port of Göteborg AB.
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
import java.nio.charset.Charset;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/** 
 * Helper class for working with Email headers used by the EmailOuput node.
 * 
 */
public class EmailPayload extends Payload {
	
	private MbElement headerElm;
	private MbElement bodyElm;
	
	private String messageEncoding;

	public String getEncoding() {
		return messageEncoding;
	}

	public void setEncoding(String encoding) {
		this.messageEncoding = encoding;
	}
	
	public void setTo(String s) throws MbException {
		set("To", s);
	}
	
	public void setCc(String s) throws MbException {
		set("Cc", s);
	}
	
	public void setBcc(String s) throws MbException {
		set("Bcc", s);
	}
	
	public void setFrom(String s) throws MbException {
		set("From", s);
	}
	
	public void setReplyTo(String s) throws MbException {
		set("Reply-To", s);
	}
	
	public void setSubject(String s) throws MbException {
		set("Subject", s);
	}
	/**
	 * Sets the email message body text
	 * 
	 * @param msg Formatted email message 
	 * @throws MbException
	 */
	public void setMessage(String msg) throws MbException {
		try {
			bodyElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", msg.getBytes(messageEncoding));
		} catch (UnsupportedEncodingException e) {
			throw new NiceMbException("Unsupported encoding used for email message!");
		}
	}
	/**
	 * Sets value in the EmailOutputHeader
	 * 
	 * @param field
	 * @param value
	 * @throws MbException
	 */
	private void set(String field, String value) throws MbException {
		headerElm.evaluateXPath("?" + field + "[set-value('" + value + "')]");
	}
	
	/**
	 * Creates an EmailPayload as the last child, even if one already exists
	 * 
	 * @param msg The message where to create an Email payload
	 * @return A newly created Email payload
	 * @throws MbException
	 */
	public static EmailPayload create(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement();
		return new EmailPayload(elm, false);
	}
	
	/**
	 * Class constructor
	 * 
	 * @param elm
	 * @param readOnly
	 * @throws MbException
	 */
	private EmailPayload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
		headerElm = getMbElement().createElementAsLastChild("EmailOutputHeader");
		bodyElm = getMbElement().createElementAsLastChild(MbBLOB.PARSER_NAME);
		//TODO: verify encoding
		this.messageEncoding = Charset.defaultCharset().name();
	}

}