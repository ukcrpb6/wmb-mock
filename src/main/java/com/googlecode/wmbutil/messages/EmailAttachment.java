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

public class EmailAttachment {
	private byte[] attachmentContentAsBlob;
	private String attachmentContentAsText;
	
	/*
	 * TODO: Not yet implemented
	 * 
	private String attachmentContentAsXpath;
	private String attachmentContentAsEsql;
	*/
	private String attachmentContentType;
	private String attachmentContentName;
	private String attachmentContentEncoding;
	
	public static final String ENCODING_7BIT = "7bit";
	public static final String ENCODING_BASE64 = "base64";
	public static final String ENCODING_QUOTED_PRINTABLE = "quoted-printable";
	
	public byte[] getAttachmentContentAsBlob() {
		return attachmentContentAsBlob;
	}
	public void setAttachmentContentAsBlob(byte[] attachmentContentBlob) {
		this.attachmentContentAsBlob = attachmentContentBlob;
	}
	public String getAttachmentContentAsText() {
		return attachmentContentAsText;
	}
	public void setAttachmentContentAsText(String attachmentContentText) {
		this.attachmentContentAsText = attachmentContentText;
	}
	/*
	public String getAttachmentContentXpath() {
		return attachmentContentAsXpath;
	}
	public void setAttachmentContentXpath(String attachmentContentXpath) {
		this.attachmentContentAsXpath = attachmentContentXpath;
	}
	public String getAttachmentContentEsql() {
		return attachmentContentAsEsql;
	}
	public void setAttachmentContentEsql(String attachmentContentEsql) {
		this.attachmentContentAsEsql = attachmentContentEsql;
	}
	*/
	public String getAttachmentContentEncoding() {
		return attachmentContentEncoding;
	}
	public void setAttachmentContentEncoding(String attachmentContentEncoding) {
		this.attachmentContentEncoding = attachmentContentEncoding;
	}
	public String getAttachmentContentName() {
		return attachmentContentName;
	}
	public void setAttachmentContentName(String attachmentContentName) {
		this.attachmentContentName = attachmentContentName;
	}
	public String getAttachmentContentType() {
		return attachmentContentType;
	}
	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}
	public EmailAttachment() {
		
	}

}
