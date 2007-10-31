package com.googlecode.wmbutil.messages;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class Rfh2HeaderExampleNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbOutputTerminal out = getOutputTerminal("out");
		
		MbMessage inMsg = inAssembly.getMessage();
		
		// create outmessage from scratch, we normally do not want to copy to incoming message
		MbMessage outMsg = new MbMessage();

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// create MQMD for the out message
		MqmdHeader mqmd = MqmdHeader.create(outMsg);
		mqmd.setCorrelId("FOOOO".getBytes());
		
		// create an RFH2 header
		// creating the RFH2 header will automatically set the MQMD format correctly
		Rfh2Header rfh2 = Rfh2Header.create(outMsg);
		
		// since we'll be creating XML, wer set the format to be a string
		rfh2.setFormat("MQSTR");
		
		// set a string value in the usr folder
		rfh2.setStringProperty("usr", "foo", "bar");
		
		// set a int value in the usr folder
		rfh2.setIntProperty("usr", "MyInt", 123);
		
		// wrap the incoming record based message
		TdsPayload csv = TdsPayload.wrap(inMsg, true);

		// create an XML based out message
		XmlPayload payload = XmlPayload.create(outMsg);
		
		// create the root element
		XmlElement rootElm = payload.createRootElement("foo");
		
		// get all records named "record"
		TdsRecord[] records = csv.getRecords("record");
		
		// loop over all records and create a new XML element for each
		for (int i = 0; i < records.length; i++) {
			TdsRecord record = records[i];
			
			XmlElement barElm = rootElm.createLastChild("bar");
			
			// set the text content on the element
			barElm.setValue(record.getStringField("field1"));
			
			// create and attribte and set its value
			barElm.setAttribute("xyz", Integer.toString(record.getIntField("field2")));
		}

		// send out message
		out.propagate(outAssembly);
	}
}
