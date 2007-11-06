package com.googlecode.wmbutil.util;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXML;
import com.ibm.broker.plugin.MbXMLNS;
import com.ibm.broker.plugin.MbXMLNSC;

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
	
	public static boolean isMRM(MbElement elm) throws MbException {
		return elm.getParserClassName().toUpperCase().equals("MRM");
	}

	public static boolean isXML(MbElement elm) throws MbException {
		return elm.getParserClassName().toUpperCase().equals(MbXML.PARSER_NAME);
	}
	public static boolean isXMLNS(MbElement elm) throws MbException {
		return elm.getParserClassName().toUpperCase().equals(MbXMLNS.PARSER_NAME);
	}
	public static boolean isXMLNSC(MbElement elm) throws MbException {
		return elm.getParserClassName().toUpperCase().equals(MbXMLNSC.PARSER_NAME);
	}
}
