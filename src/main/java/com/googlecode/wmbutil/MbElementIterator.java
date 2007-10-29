package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbElement;

public interface MbElementIterator {

	boolean hasNext();
	
	MbElement next();
}
