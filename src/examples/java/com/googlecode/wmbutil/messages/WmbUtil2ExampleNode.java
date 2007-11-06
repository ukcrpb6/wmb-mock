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

		String messageSetId = "B3JDCVK002001";
		String messageType = "message2";
		String messageFormat = "XML1";

		// Properties header must be created first, before the MQMD
		PropertiesHeader props = PropertiesHeader.create(outMsg);
		
		props.setMessageSet("B3JDCVK002001");
		props.setMessageType("message2");
		props.setMessageFormat("XML1");

		// create MQMD for the out message
		MqmdHeader mqmd = MqmdHeader.create(outMsg);
		
		// create an XML based out message
		XmlPayload payload = XmlPayload.create(outMsg, "MRM");
		
		// create the root element
		XmlElement rootElm = payload.createRootElement(NS, "root");
		
		XmlElement barElm = rootElm.createLastChild(NS, "child");
		
		barElm.setAttribute("attr", "somevalue");
		
		// set the text content on the element
		barElm.setStringValue("Fooo");
			
		// send out message
		out.propagate(outAssembly);
	}
}
