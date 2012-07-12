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

package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXPath;

import java.util.Iterator;

public abstract class XPathTraverser implements Traverser {

    private MbXPath xpath;

    public XPathTraverser(String xpath) throws MbException {
        this.xpath = new MbXPath(xpath);
    }

    public XPathTraverser(MbXPath xpath) {
        this.xpath = xpath;
    }

    public void accept(MbElement inElm, MbElement outElm) throws MbException {
        Iterator<MbElement> iterator = new XPathIterator(inElm, xpath);
        while (iterator.hasNext()) {
            visit(iterator.next(), outElm);
        }
    }

    public abstract void visit(MbElement elm, MbElement outElm);

}
