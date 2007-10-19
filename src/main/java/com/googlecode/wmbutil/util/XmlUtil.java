package com.googlecode.wmbutil.util;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXMLNS;
import com.ibm.broker.plugin.MbXMLNSC;


public class XmlUtil {

	public static int getFolderElementType(MbElement elmInTree) throws MbException {
		if(elmInTree.getParserClassName().toUpperCase().equals(MbXMLNSC.PARSER_NAME)) {
			return MbXMLNSC.FOLDER;
		} else {
			return MbXMLNS.ELEMENT;
		}
	}

	public static int getAttributeType(MbElement elmInTree) throws MbException {
		if(elmInTree.getParserClassName().toUpperCase().equals(MbXMLNSC.PARSER_NAME)) {
			return MbXMLNSC.ATTRIBUTE;
		} else {
			return MbXMLNS.ATTRIBUTE;
		}
	}
}
