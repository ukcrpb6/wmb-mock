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

public class EmailDestination {
	private String smtpServer;
	private String securityIdentity;
	private String bodyContentType;
	private String multiPartContentType;
	
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain"; 
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	
	public String getBodyContentType() {
		return bodyContentType;
	}

	public void setBodyContentType(String bodyContentType) {
		this.bodyContentType = bodyContentType;
	}

	public String getMultiPartContentType() {
		return multiPartContentType;
	}

	public void setMultiPartContentType(String multiPartContentType) {
		this.multiPartContentType = multiPartContentType;
	}

	public String getSecurityIdentity() {
		return securityIdentity;
	}

	public void setSecurityIdentity(String securityIdentity) {
		this.securityIdentity = securityIdentity;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public EmailDestination() {
		
	}
	
}
