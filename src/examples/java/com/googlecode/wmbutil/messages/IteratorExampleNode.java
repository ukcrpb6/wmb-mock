package com.googlecode.wmbutil.messages;

import com.googlecode.wmbutil.MbElementIterator;
import com.googlecode.wmbutil.XPathIterator;
import com.googlecode.wmbutil.util.ElementUtil;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class IteratorExampleNode extends MbJavaComputeNode {

	@Override
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
