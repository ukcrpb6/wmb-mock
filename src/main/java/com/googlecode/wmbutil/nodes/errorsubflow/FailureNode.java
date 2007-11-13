package com.googlecode.wmbutil.nodes.errorsubflow;
import org.apache.log4j.Logger;

import com.googlecode.wmbutil.util.WmbLogger;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class FailureNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		// ----------------------------------------------------------
		// Add user code below

		
		WmbLogger log = new WmbLogger(Logger.getLogger(getBroker().getName() + "." + getMessageFlow().getName() + ".ErrorSubflow.Failure"));
		
		log.error("Error detected in flow, transaction will fail and message back out", assembly);
		
		// End of user code
		// ----------------------------------------------------------

		// The following should only be changed
		// if not propagating message to the 'out' terminal

		out.propagate(assembly);
	}
}
