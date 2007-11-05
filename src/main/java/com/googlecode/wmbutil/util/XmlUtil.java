package com.googlecode.wmbutil.util;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXMLNS;
import com.ibm.broker.plugin.MbXMLNSC;


public class XmlUtil {

	public static int getFolderElementType(MbElement elmInTree) throws MbException {
		String parseName = elmInTree.getParserClassName().toUpperCase(); 
		if(parseName.equals(MbXMLNSC.PARSER_NAME)) {
			return MbXMLNSC.FOLDER;
		} else if(parseName.equals("MRM")) {
			return MbElement.TYPE_NAME;
		} else {
			return MbXMLNS.ELEMENT;
		}
	}

	public static int getAttributeType(MbElement elmInTree) throws MbException {
		String parseName = elmInTree.getParserClassName().toUpperCase(); 
		if(parseName.equals(MbXMLNSC.PARSER_NAME)) {
			return MbXMLNSC.ATTRIBUTE;
		} else if(parseName.equals("MRM")) {
			return MbElement.TYPE_NAME_VALUE;
		} else {
			return MbXMLNS.ATTRIBUTE;
		}
	}
}
