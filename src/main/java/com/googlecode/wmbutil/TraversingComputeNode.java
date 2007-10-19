package com.googlecode.wmbutil;

import java.util.Iterator;
import java.util.List;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXPath;

public abstract class TraversingComputeNode extends AbstractComputeNode {

	private MbXPath xpath;

	
	public TraversingComputeNode(MbXPath xpath) {
		this.xpath = xpath;
	}
	
	protected void evaluate(MbMessage inMessage, MbMessage outMessage) throws MbException {
		MbElement inElm = inMessage.getRootElement();
		MbElement outElm = outMessage.getRootElement();
		
		Object result = inElm.evaluateXPath(xpath);
		if (result instanceof List) {
			Iterator iterator = ((List) result).iterator();

			while (iterator.hasNext()) {
				MbElement node = (MbElement) iterator.next();
				forEachElement(node, outElm);
			}
		} else if(result instanceof MbElement) {
			forEachElement((MbElement)result, outElm);
		}
	}
	
	public abstract void forEachElement(MbElement elm, MbElement outElm);
}
