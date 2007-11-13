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

package com.googlecode.wmbutil.examples;

import com.googlecode.wmbutil.MbElementIterator;
import com.googlecode.wmbutil.XPathIterator;
import com.googlecode.wmbutil.util.ElementUtil;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class IteratorExampleNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbMessage inMsg = inAssembly.getMessage();
		
		MbMessage outMsg = new MbMessage(inMsg);

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);
		
		MbElement inRoot = inMsg.getRootElement();
		MbElement outRoot = outMsg.getRootElement();
		
		
		MbElementIterator hellos = new XPathIterator(inMsg, "hellos");
		
		while(hellos.hasNext()) {
			MbElement aHello = hellos.next();
			
			ElementUtil.copyValue(aHello, "hello", outRoot, "mynewhellos/hi");
		
			MbElementIterator hellosChildren = new XPathIterator(aHello, "hellos-child");
		}
		
		
		createOutputTerminal("out").propagate(outAssembly);
		
	}

}
