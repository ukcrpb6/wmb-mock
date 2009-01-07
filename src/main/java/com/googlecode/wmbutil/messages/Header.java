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

package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * Abstract base class for the Header helpers.
 *
 */
public abstract class Header extends MbElementWrapper {

	/**
	 * Constructor defining the header with the specific element.
	 * 
	 * @param elm The message element
	 * @param readOnly Specifies Whether the payload is read only or not
	 * @throws MbException
	 */
	public Header(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);
	}
}
