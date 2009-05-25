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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbDate;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbTime;
import com.ibm.broker.plugin.MbTimestamp;
import com.ibm.broker.plugin.MbXML;
import com.ibm.broker.plugin.MbXMLNS;

public class WmbLogger {

	private static final String CRLF     = "\r\n";

	private static final Map TYPES = new HashMap();
	static {
		TYPES.put(new Integer(MbElement.TYPE_NAME_VALUE), 	"NAME_VALUE");
		TYPES.put(new Integer(MbElement.TYPE_NAME), 		"NAME");
		TYPES.put(new Integer(MbElement.TYPE_VALUE), 		"VALUE");
		TYPES.put(new Integer(MbXML.ATTRIBUTE), 			"ATTRIBUTE");
		TYPES.put(new Integer(MbXML.ELEMENT), 				"ELEMENT");
		TYPES.put(new Integer(MbXML.COMMENT), 				"COMMENT");
		TYPES.put(new Integer(MbXMLNS.NAMESPACE_DECL), 		"NAMESPACE_DECL");
	}
	
	private Logger log;
	
	public WmbLogger(Logger log) {
		this.log = log;
	}

	private String getNiceType(int type) {
		String nice = (String) TYPES.get(new Integer(type));
		
		if (nice != null) {
			return nice;
		} else {
			return Integer.toString(type, 8);
		}
	}
	
	public void debug(String msg) throws MbException {
		if (log.isDebugEnabled()) {
			log.debug(msg);
		}
	}

	public void debug(String msg, MbElement elm) throws MbException {
		if (log.isDebugEnabled()) {
			log.debug(msg + CRLF + logTreeDump(elm));
		}
	}

	public void debug(String msg, MbMessageAssembly msgAss) throws MbException {
		if (log.isDebugEnabled()) {
			log.debug(msg + CRLF + logTreeDump(msgAss));
		}
	}

	public void info(String msg) throws MbException {
		if (log.isInfoEnabled()) {
			log.info(msg);
		}
	}

	public void info(String msg, MbElement elm) throws MbException {
		if (log.isInfoEnabled()) {
			log.info(msg + CRLF + logTreeDump(elm));
		}
	}

	public void info(String msg, MbMessageAssembly msgAss) throws MbException {
		if (log.isInfoEnabled()) {
			log.info(msg + CRLF + logTreeDump(msgAss));
		}
	}

	public void warn(String msg) throws MbException {
		log.warn(msg);
	}

	public void warn(String msg, MbElement elm) throws MbException {
		log.warn(msg + CRLF + logTreeDump(elm));
	}
	
	public void warn(String msg, MbMessageAssembly msgAss) throws MbException {
		log.warn(msg + CRLF + logTreeDump(msgAss));
	}

	public void warn(String msg, MbElement elm, Throwable t) throws MbException {
		log.warn(msg + CRLF + logTreeDump(elm), t);
	}
	
	public void warn(String msg, MbMessageAssembly msgAss, Throwable t) throws MbException {
		log.warn(msg + CRLF + logTreeDump(msgAss), t);
	}
	
	public void error(String msg) throws MbException {
		log.error(msg);
	}
	
	public void error(String msg, MbElement elm) throws MbException {
		log.error(msg + CRLF + logTreeDump(elm));
	}

	public void error(String msg, MbMessageAssembly msgAss) throws MbException {
		log.error(msg + CRLF + logTreeDump(msgAss));
	}

	public void error(String msg, MbElement elm, Throwable t) throws MbException {
		log.error(msg + CRLF + logTreeDump(elm), t);
	}
	
	public void error(String msg, MbMessageAssembly msgAss, Throwable t) throws MbException {
		log.error(msg + CRLF + logTreeDump(msgAss), t);
	}
	
	private String logTreeDump(MbElement elm) throws MbException {
		if (elm != null) {
			StringBuffer sb = new StringBuffer();
			
			logElementDump("", sb, elm);
			
			return sb.toString();
		} else {
			return null;
		}
		
	}

	private String logTreeDump(MbMessageAssembly msgAss) throws MbException {
		if (msgAss != null) {
			StringBuffer sb = new StringBuffer();
			
			sb.append("[Message");
			sb.append(CRLF);
			logElementDump("\t", sb, msgAss.getMessage().getRootElement());
			sb.append("]");
			sb.append(CRLF);
			sb.append("[LocalEnvironment");
			sb.append(CRLF);
			logElementDump("\t", sb, msgAss.getLocalEnvironment().getRootElement());
			sb.append("]");
			sb.append(CRLF);
			sb.append("[Environment");
			sb.append(CRLF);
			logElementDump("\t", sb, msgAss.getGlobalEnvironment().getRootElement());
			sb.append("]");
			sb.append(CRLF);
			sb.append("[ExceptionList");
			sb.append(CRLF);
			logElementDump("\t", sb, msgAss.getExceptionList().getRootElement());
			sb.append("]");
			
			
			return sb.toString();
		} else {
			return null;
		}
		
	}

	private void logElementDump(String spacer, StringBuffer sb, MbElement elm) throws MbException {
		MbElement child = elm.getFirstChild();
		
		sb.append(spacer);
		sb.append("[");
		sb.append(getNiceType(elm.getType()));
		sb.append(" ");
		sb.append(elm.getName());

		Object v = elm.getValue();
		
		if (v != null) {
			sb.append(" = ");
			sb.append(formatValue(elm.getValue()));
		}
		
		if (child != null) {
			sb.append(CRLF);
			
			while (child != null) {
				logElementDump(spacer + '\t', sb, child);
				child = child.getNextSibling();
			}
			
			sb.append(spacer);
		}
		
		sb.append("]");
		sb.append(CRLF);
	}
	
	private String formatValue(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof MbDate) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			MbDate date = (MbDate) value;
			return df.format(date.getTime());
		} else if (value instanceof MbTime) {
			DateFormat df = new SimpleDateFormat("HH:mm:ss'.'SSS");
			MbTime time = (MbTime) value;
			return df.format(time.getTime());
		} else if (value instanceof MbTimestamp) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'H'H:mm:ss'.'SSS");
			MbTimestamp dateTime = (MbTimestamp) value;
			return df.format(dateTime.getTime());
		} else {
			return value.toString();
		}
	}
}
