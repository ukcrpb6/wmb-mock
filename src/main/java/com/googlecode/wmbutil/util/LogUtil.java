package com.googlecode.wmbutil.util;

import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public class LogUtil {

	public static void logTreeDump(Logger log, MbElement elm) throws MbException {
		if(elm != null) {
			logElementDump("", log, elm);
		}
		
	}
	
	private static void logElementDump(String spacer, Logger log, MbElement elm) throws MbException {
		MbElement child = elm.getFirstChild();
		log.debug(spacer + "Element:         " + elm);
		log.debug(spacer + "Name:            " + elm.getName());
		log.debug(spacer + "Namespace:       " + elm.getNamespace());
		log.debug(spacer + "Type:            " + elm.getType());
		log.debug(spacer + "SpecificType:    " + elm.getSpecificType());
		log.debug(spacer + "ParserClassName: " + elm.getParserClassName());
		log.debug(spacer + "Value:           " + elm.getValue());
		log.debug(spacer + "ValueState:      " + elm.getValueState());
		
		
		while(child != null) {
			logElementDump(spacer + '-', log, child);
			child = child.getNextSibling();
		}
	}
}
