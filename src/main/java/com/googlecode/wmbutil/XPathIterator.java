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

import com.google.common.collect.*;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXPath;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class XPathIterator extends ForwardingIterator<MbElement> {

    private Iterator<MbElement> matches = Iterators.emptyIterator();

    public XPathIterator(MbElement element, MbXPath xpath) throws MbException {
        Object result = checkNotNull(element).evaluateXPath(checkNotNull(xpath));
        if (result instanceof List) {
            setMatches((List<?>) result);
        }
    }

    public XPathIterator(MbElement element, String xpath) throws MbException {
        this(element, new MbXPath(xpath));
    }

    public XPathIterator(MbMessage message, MbXPath xpath) throws MbException {
        Object result = checkNotNull(message).evaluateXPath(checkNotNull(xpath));
        if (result instanceof List) {
            setMatches((List<?>) result);
        }
    }

    public XPathIterator(MbMessage msg, String xpath) throws MbException {
        this(msg, new MbXPath(xpath));
    }

    private void setMatches(List<?> nodes) {
        matches = createMbIterable(nodes).iterator();
    }

    private Iterable<MbElement> createMbIterable(final List<?> untypedList) {
        final Iterator<?> iterator = ImmutableList.copyOf(untypedList).iterator();
        return new FluentIterable<MbElement>() {
            @Override
            public Iterator<MbElement> iterator() {
                return new AbstractIterator<MbElement>() {
                    @Override
                    protected MbElement computeNext() {
                        while(iterator.hasNext()) {
                            Object o = iterator.next();
                            if(o != null && o instanceof MbElement) {
                                return (MbElement) o;
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    protected Iterator<MbElement> delegate() {
        return matches;
    }

}