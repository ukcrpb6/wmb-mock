package com.googlecode.wmbutil;

import java.util.Iterator;
import java.util.List;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXPath;

public abstract class XPathTraverser implements Traverser {

	private MbXPath xpath;
	
	public XPathTraverser(String xpath) throws MbException {
		this.xpath = new MbXPath(xpath);
	}

	public XPathTraverser(MbXPath xpath) {
		this.xpath = xpath;
	}
	
	public void evaluate(MbElement inElm, MbElement outElm) throws MbException {
		Object result = inElm.evaluateXPath(xpath);
		if (result instanceof List) {
			Iterator iterator = ((List) result).iterator();

			while (iterator.hasNext()) {
				MbElement node = (MbElement) iterator.next();
				forEachElement(node, outElm);
			}
		}
	}
	
	public abstract void forEachElement(MbElement elm, MbElement outElm);
}
