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

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class WmbUtil2ExampleNode extends MbJavaComputeNode {

	private static final String NS = "http://www.portgot.se";
	
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbOutputTerminal out = getOutputTerminal("out");
		
		MbMessage inMsg = inAssembly.getMessage();
		
		// create outmessage from scratch, we normally do not want to copy to incoming message
		MbMessage outMsg = new MbMessage();

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// Properties header must be created first, before the MQMD
		PropertiesHeader props = PropertiesHeader.create(outMsg);
		
		props.setMessageSet("B3JDCVK002001");
		props.setMessageType("message2");
		props.setMessageFormat("XML1");

		// create MQMD for the out message
		MqmdHeader mqmd = MqmdHeader.create(outMsg);
		
		XmlPayload inXml = XmlPayload.wrap(inMsg, true);

		// create an XML based out message
		XmlPayload outXml = XmlPayload.create(outMsg, "MRM");
		
		// create the root element
		XmlElement rootElm = outXml.createRootElement(NS, "root");
		
		XmlElement outChild = rootElm.createLastChild(NS, "child");
		
		XmlElement inRoot = inXml.getRootElement();
		XmlElement inChild = inRoot.getChildByName(NS, "child")[0];
		
		String[] attrs = inChild.getAttributeNames();
		
		for (int i = 0; i < attrs.length; i++) {
			outChild.setAttribute(attrs[i], inChild.getAttribute(attrs[i]));
			
		}
		
		
		
		
		// set the text content on the element
		outChild.setStringValue("Fooo");
			
		// send out message
		out.propagate(outAssembly);
	}
}
