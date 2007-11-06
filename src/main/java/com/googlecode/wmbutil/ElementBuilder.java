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

package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class ElementBuilder {

	private MbElement currentElm;
	
	public ElementBuilder(MbElement currentElm) {
		this.currentElm = currentElm;
	}

	public ElementBuilder(MbMessage msg) throws MbException {
		this.currentElm = msg.getRootElement();
	}
	
	
	
	public ElementBuilder get(String elmName) throws MbException {
		currentElm = currentElm.getFirstElementByPath(elmName);
		
		return this;
	}
	
	public ElementBuilder createParser(String elmName) throws MbException {
		currentElm = currentElm.createElementAsLastChild(elmName);
		
		return this;
	}

	public ElementBuilder create(String elmName) throws MbException {
		currentElm = currentElm.createElementAsLastChild(MbElement.TYPE_NAME, elmName, null);
		
		return this;
	}

	public ElementBuilder create(String elmName, Object value) throws MbException {
		currentElm = currentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, elmName, value);
		
		return this;
	}
	
	public ElementBuilder setValue(Object value) throws MbException {
		if(value instanceof MbElement ) {
			currentElm.setValue(((MbElement)value).getValue());
		} else {
			currentElm.setValue(value);
		}
		
		return this;
	}

	public Object getValue() throws MbException {
		return currentElm.getValue();
	}
}
