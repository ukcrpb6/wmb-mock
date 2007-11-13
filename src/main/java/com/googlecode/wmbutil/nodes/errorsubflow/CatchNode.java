package com.googlecode.wmbutil.nodes.errorsubflow;
import org.apache.log4j.Logger;

import com.googlecode.wmbutil.util.WmbLogger;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class CatchNode extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		// ----------------------------------------------------------
		// Add user code below

		WmbLogger log = new WmbLogger(Logger.getLogger(getBroker().getName() + "." + getMessageFlow().getName() + ".ErrorSubflow.Catch"));
		
		log.warn("Problem detected in flow, will retry. Message content logged at debug level.");
		log.debug("Problematic message", assembly);
		
		if(getUserDefinedAttribute("SleepTime") != null) {
			long sleepTime = ((Integer)getUserDefinedAttribute("SleepTime")).intValue();
			
			if(sleepTime > 0) {
				log.info("Will wait for " + sleepTime + " ms before retrying");
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO revisit, do we need to handle?
					// ignore
				}
				log.debug("Waited for " + sleepTime + " ms, will now retry");
			}
		}
		
		// End of user code
		// ----------------------------------------------------------

		// The following should only be changed
		// if not propagating message to the 'out' terminal

		out.propagate(assembly);
		
	}
}
