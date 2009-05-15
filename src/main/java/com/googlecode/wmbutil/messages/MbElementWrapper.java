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

public class MbElementWrapper {

	private MbElement wrappedElm;
	private boolean readOnly;
	
	public MbElementWrapper(MbElement elm, boolean readOnly) throws MbException {
		this.wrappedElm = elm;
		this.readOnly = readOnly;
	}
	
	protected MbElement getMbElement() {
		return wrappedElm;
	}

	protected boolean isReadOnly() {
		return readOnly;
	}

	protected void checkReadOnly() throws MbException {
		if(isReadOnly()) {
			throw new NiceMbException(this, "Message is read-only, can not be changed");
		}
	}
	
	private Object getValue(String field) throws MbException {
		MbElement elm = getMbElement().getFirstElementByPath(field);
		if(elm != null) {
			return elm.getValue();
		} else {
			throw new NiceMbException("Property not found in MQMD: " + field);
		}
	}
	
	private void setValue(String field, Object value) throws MbException {
		checkReadOnly();
		
		MbElement elm = getMbElement().getFirstElementByPath(field);
		if(elm == null) {
			elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, field, null);
		}
		elm.setValue(value);
		
	}
	
	
	protected int getIntValue(String field) throws MbException {
		return ((Integer)getValue(field)).intValue();
	}

	protected void setIntValue(String field, int value) throws MbException {
		setValue(field, new Integer(value));
	}

	protected String getStringValue(String field) throws MbException {
		return (String)getValue(field);
	}
	
	protected void setStringValue(String field, String value, int length) throws MbException {
		if(value.length() > length) {
			throw new NiceMbException("Value for field '" +  field + "' to long, max length is " + length);
		} else {
			StringBuffer sb = new StringBuffer(length);
			sb.append(value);
			
			for(int i = value.length(); i < length; i++) {
				sb.append(' ');
			}
			setValue(field, sb.toString());
		}
	}

	protected void setStringValue(String field, String value) throws MbException {
		setValue(field, value);
	}
	
	protected byte[] getByteArrayValue(String field) throws MbException {
		return (byte[])getValue(field);
	}
	
	protected void setByteArray(String field, byte[] value, int length) throws MbException {
		byte[] b;
		if(value.length > length) {
			throw new NiceMbException("Value for field '" +  field + "' to long, max length is " + length);
		} else if(value.length < length) {
			b = new byte[length];
			System.arraycopy(value, 0, b, 0, value.length);
		} else {
			b = value;
		}
		
		setValue(field, b);
	}
}
