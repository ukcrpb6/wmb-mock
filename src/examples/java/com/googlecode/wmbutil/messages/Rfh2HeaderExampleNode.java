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
		
		// create outmessage from inMessage
		MbMessage outMsg = new MbMessage();

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMsg);

		// create MQMD
		MqmdHeader mqmd = MqmdHeader.create(outMsg);
		mqmd.setCorrelId("FOOOO".getBytes());
		
		// change RFH2 header
		Rfh2Header rfh2 = Rfh2Header.create(outMsg);
		rfh2.setFormat("MQSTR");
		
		// set a string value in the usr folder
		rfh2.setStringProperty("usr", "foo", "bar");
		rfh2.setIntProperty("usr", "MyInt", 123);
		
		CsvPayload csv = CsvPayload.wrap(inMsg, true);

		XmlPayload payload = XmlPayload.create(outMsg);
		XmlElement rootElm = payload.createDocumentElement("foo");
		
		CsvRecord[] records = csv.getRecords("record");
		
		for (int i = 0; i < records.length; i++) {
			CsvRecord record = records[i];
			
			XmlElement barElm = rootElm.createLastChild("bar");
			
			barElm.setValue(record.getStringField("field1"));
			barElm.setAttribute("xyz", Integer.toString(record.getIntField("field2")));
			
		}
		
		
		out.propagate(outAssembly);
	}
}
