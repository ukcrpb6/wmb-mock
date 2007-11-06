/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

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
