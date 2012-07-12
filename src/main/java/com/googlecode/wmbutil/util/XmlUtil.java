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

import com.ibm.broker.plugin.*;

public class XmlUtil {

    public static boolean isElement(MbElement elmInTree) throws MbException {
        final int type = elmInTree.getSpecificType();
        if (ElementUtil.isXMLNSC(elmInTree)) {
            return type == MbXMLNSC.FOLDER || type == MbXMLNSC.FIELD;
        } else if (ElementUtil.isMRM(elmInTree)) {
            return type == MbElement.TYPE_NAME;
        } else {
            return type == MbXMLNS.ELEMENT;
        }

    }

    public static int getFolderElementType(MbElement elmInTree) throws MbException {
        if (ElementUtil.isXMLNSC(elmInTree)) {
            return MbXMLNSC.FOLDER;
        } else if (ElementUtil.isMRM(elmInTree)) {
            return MbElement.TYPE_NAME;
        } else {
            return MbXMLNS.ELEMENT;
        }
    }

    public static int getAttributeType(MbElement elmInTree) throws MbException {
        if (ElementUtil.isXMLNSC(elmInTree)) {
            return MbXMLNSC.ATTRIBUTE;
        } else if (ElementUtil.isMRM(elmInTree)) {
            return MbElement.TYPE_NAME_VALUE;
        } else {
            return MbXMLNS.ATTRIBUTE;
        }
    }

}
