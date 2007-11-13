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



import java.util.Enumeration;
import java.util.Properties;

import com.googlecode.wmbutil.CCSID;
import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.lookup.Lookup;
import com.googlecode.wmbutil.lookup.LookupCacheException;
import com.googlecode.wmbutil.messages.BlobPayload;
import com.googlecode.wmbutil.messages.MqmdHeader;
import com.googlecode.wmbutil.messages.Rfh2Header;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class LookupExampleNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbOutputTerminal out = getOutputTerminal("out");
		
		MbMessage inMsg = inAssembly.getMessage();
		
		// create outmessage from scratch, we normally do not want to copy to incoming message
		MbMessage outMsg = new MbMessage();

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// create MQMD for the out message
		MqmdHeader mqmd = MqmdHeader.create(outMsg);

		Rfh2Header rfh2 = Rfh2Header.create(outMsg);
		
		try {
			Lookup  lookup = new Lookup("component1");
			rfh2.setStringProperty("usr", "pc", lookup.lookupValue("key1"));
		} catch (LookupCacheException e) {
			throw new NiceMbException(e.getMessage());
		}
		
		Properties props = System.getProperties();
		
		Enumeration propsNames = props.propertyNames();
		
		while(propsNames.hasMoreElements()) {
			String propName = (String) propsNames.nextElement();
			rfh2.setStringProperty("usr", propName, props.getProperty(propName));
		}
		
		BlobPayload blob = BlobPayload.create(outMsg);
		blob.setDataAsString("ÅÄÖ", CCSID.UTF8);

			
		// send out message
		out.propagate(outAssembly);
	}
}
