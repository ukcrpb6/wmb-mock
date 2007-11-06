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

import com.ibm.broker.plugin.MbUserException;

public class NiceMbException extends MbUserException {

	private static final long serialVersionUID = -5760540385903728797L;

	public NiceMbException(Object source, String msg) {
		super(source.getClass().getName(), null, null, null, msg, null);
	}

	public NiceMbException(String msg) {
		
		super(null, null, null, null, msg, null);
	}


}
