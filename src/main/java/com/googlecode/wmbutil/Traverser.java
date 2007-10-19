package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public interface Traverser {

	void evaluate(MbElement inElm, MbElement outElm) throws MbException;
	
	void forEachElement(MbElement elm) throws MbException;
}
