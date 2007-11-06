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
import com.ibm.broker.plugin.MbXPath;

public abstract class XPathTraverser implements Traverser {

	private MbXPath xpath;
	
	public XPathTraverser(String xpath) throws MbException {
		this.xpath = new MbXPath(xpath);
	}

	public XPathTraverser(MbXPath xpath) {
		this.xpath = xpath;
	}
	
	public void evaluate(MbElement inElm, MbElement outElm) throws MbException {
		Object result = inElm.evaluateXPath(xpath);
		if (result instanceof List) {
			Iterator iterator = ((List) result).iterator();

			while (iterator.hasNext()) {
				MbElement node = (MbElement) iterator.next();
				forEachElement(node, outElm);
			}
		}
	}
	
	public abstract void forEachElement(MbElement elm, MbElement outElm);
}
