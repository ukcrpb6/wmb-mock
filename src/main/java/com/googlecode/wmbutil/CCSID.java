package com.googlecode.wmbutil;

import java.util.HashMap;
import java.util.Map;

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
	
	public static String ccsidToCharset(int ccsid) {
		String cs = (String) CHARSETS.get(new Integer(ccsid));
		
		if(cs != null) {
			return cs;
		} else {
			return "Cp"+ ccsid;
		}
	}
}
