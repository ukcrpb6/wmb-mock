package com.googlecode.wmbutil.messages;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class Rfh2HeaderExampleNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbMessage inMsg = inAssembly.getMessage();
		
		// create outmessage from inMessage
		MbMessage outMsg = new MbMessage(inMsg);

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// change RFH2 header
		Rfh2Header rfh2 = Rfh2Header.wrapOrCreate(outMsg);
		
		// set a string value in the usr folder
		rfh2.setStringProperty("usr", "foo", "bar");
		
		createOutputTerminal("out").propagate(outAssembly);
		
	}

}
