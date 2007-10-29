package com.googlecode.wmbutil.util;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class ElementUtil {

	public static Object copyValue(MbElement inElm, String inXpath, MbElement outElm, String outXpath) throws MbException {
		Object value = inElm.getFirstElementByPath(inXpath).getValue();
		
		// hellos[0]/hello
		// mynewhellos/hi
		
		// ?mynewhellos/?hi[set-value($kalle)]
		// assignVariable("kalle", hellos[0]/hello);
		
		outElm.getFirstElementByPath(outXpath).setValue(value);
		
		return value;
	}

	public static void setValue(MbElement outElm, String outXpath, Object value) throws MbException {
		outElm.getFirstElementByPath(outXpath).setValue(value);
	}

	public static Object getValue(MbElement inElm, String inXpath) throws MbException {
		return inElm.getFirstElementByPath(inXpath).getValue();
	}
}
