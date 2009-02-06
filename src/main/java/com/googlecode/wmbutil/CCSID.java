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

import java.util.HashMap;
import java.util.Map;

/**
 * Simplifies using the IBM CCSID values for charsets by
 * providing constants for common CCSIDs and methods
 * for converting to Java charsets
 */
public class CCSID {

	public static final int UTF16_BIG_ENDIAN = 1200;
	public static final int UTF16_LITTLE_ENDIAN = 1202;
	public static final int UTF8 = 1208;
	public static final int ISO_88591 = 819;
	public static final int ISO_LATIN1 = ISO_88591;
	public static final int ASCII = 437;
	public static final int EBCDIC_SWE_FIN = 278;
	
	private static final Map CHARSETS = new HashMap();
	static {
		CHARSETS.put(new Integer(UTF8), "UTF-8");
		CHARSETS.put(new Integer(ASCII), "ASCII");
	}
	
	/**
	 * Converts a CCSID into the name used for the encoding
	 * in Java. Note that this method might return charset 
	 * names which are not supported on the particular platform.
	 * @param ccsid The CCSID to find the name for
	 * @return The Java name for the encoding.
	 */
	public static String ccsidToCharset(int ccsid) {
		String cs = (String) CHARSETS.get(new Integer(ccsid));
		
		if(cs != null) {
			return cs;
		} else {
			return "Cp"+ ccsid;
		}
	}
}
