package com.googlecode.wmbutil.messages;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class WmbUtil1ExampleNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {

		MbOutputTerminal out = getOutputTerminal("out");
		
		MbMessage inMsg = inAssembly.getMessage();
		
		// create outmessage from scratch, we normally do not want to copy to incoming message
		MbMessage outMsg = new MbMessage();

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// create MQMD for the out message
		MqmdHeader mqmd = MqmdHeader.create(outMsg);
		
		// create an RFH2 header
		// creating the RFH2 header will automatically set the MQMD format correctly
		Rfh2Header outRfh2 = Rfh2Header.create(outMsg);

		// since we'll be creating XML, wer set the format to be a string
		outRfh2.setFormat("MQSTR");

		// make sure it is a valid JMS message
		outRfh2.setStringProperty("mcd", "Msd", "jms_text");
		
		// wrap incoming RFH2 header, if it exists
		if(Rfh2Header.has(inMsg)) {
			Rfh2Header inRfh2 = Rfh2Header.wrap(inMsg, true);
			// set a string value in the usr folder
			outRfh2.setStringProperty("usr", "baz", inRfh2.getStringProperty("usr", "foo"));
		} else {
			// set it to a default value
			outRfh2.setStringProperty("usr", "baz", "default");
		}
		
		// wrap the incoming record based message
		TdsPayload csv = TdsPayload.wrap(inMsg, true);

		// create an XML based out message
		XmlPayload payload = XmlPayload.create(outMsg);
		
		// create the root element
		XmlElement rootElm = payload.createRootElement("root");
		
		// get all records named "record"
		TdsRecord[] records = csv.getRecords("record");
		
		// loop over all records and create a new XML element for each
		for (int i = 0; i < records.length; i++) {
			TdsRecord record = records[i];
			
			XmlElement barElm = rootElm.createLastChild("child");
			
			// set the text content on the element
			barElm.setStringValue(record.getStringField("field1"));
			
			// create and attribte and set its value
			barElm.setAttribute("attr", Integer.toString(record.getIntField("field2")));
		}

		// send out message
		out.propagate(outAssembly);
	}
}
