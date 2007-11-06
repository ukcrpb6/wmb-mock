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

import java.util.Iterator;
import java.util.List;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXPath;

public class XPathIterator implements MbElementIterator {

	private Iterator matches;
	
	public XPathIterator(MbElement elm, MbXPath xpath) throws MbException {
		matches = ((List) elm.evaluateXPath(xpath)).iterator();
	}

	public XPathIterator(MbElement elm, String xpath) throws MbException {
		this(elm, new MbXPath(xpath));
	}

	public XPathIterator(MbMessage msg, MbXPath xpath) throws MbException {
		matches = ((List) msg.evaluateXPath(xpath)).iterator();
	}
	
	public XPathIterator(MbMessage msg, String xpath) throws MbException {
		this(msg, new MbXPath(xpath));
	}

	public boolean hasNext() {
		return matches.hasNext();
	}

	public MbElement next() {
		return (MbElement) matches.next();
	}

}
