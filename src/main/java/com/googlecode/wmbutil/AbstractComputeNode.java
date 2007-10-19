package com.googlecode.wmbutil;


import org.apache.log4j.Logger;

import com.googlecode.wmbutil.messages.ExtendedMessage;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public abstract class AbstractComputeNode extends MbJavaComputeNode {
	private static final Logger LOG = Logger.getLogger(AbstractComputeNode.class);

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = assembly.getMessage();

		ExtendedMessage outMessage;
		try {
			outMessage = evaluate(ExtendedMessage.wrap(inMessage, true));
		} catch(MbException e) {
			LOG.error("MbException caught during evaulation of message", e);
			throw e;
		} catch(RuntimeException e) {
			LOG.error("RuntimeException caught during evaulation of message", e);
			throw e;
		}
		
		MbMessageAssembly outAssembly = new MbMessageAssembly(assembly, outMessage.getMbMessage());
		
		out.propagate(outAssembly);
	}
	
	protected abstract ExtendedMessage evaluate(ExtendedMessage inMessage) throws MbException;
	

}
