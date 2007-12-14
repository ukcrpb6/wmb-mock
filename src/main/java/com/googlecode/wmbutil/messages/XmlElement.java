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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.googlecode.wmbutil.util.ElementUtil;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbDate;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbTime;
import com.ibm.broker.plugin.MbTimestamp;
import com.ibm.broker.plugin.MbXPath;

public class XmlElement extends MbElementWrapper {

	public XmlElement(MbElement wrappedElm, boolean readOnly) throws MbException {
		super(wrappedElm, readOnly);
	}
	
	public String getName() throws MbException {
		return getMbElement().getName();
	}

	public String getNameSpace() throws MbException {
		return getMbElement().getNamespace();
	}
	
	public boolean hasAttribute(String name) throws MbException {
		return hasAttribute(null, name);
	}

	public boolean hasAttribute(String ns, String name) throws MbException {
		return getAttributeElement(ns, name) != null;
	}
	
	private MbElement getAttributeElement(String ns, String name) throws MbException {
		List elms = getAttributeElements(ns, name);
		
		if(elms.size() > 0) {
			return (MbElement) elms.get(0);
		} else {
			return null;
		}
	}
	
	private List getAttributeElements(String ns, String name) throws MbException {
		MbXPath xpath = new MbXPath("@*", getMbElement());

		if (ns != null) {
			xpath.setDefaultNamespace(ns);
		}

		List matches = (List) getMbElement().evaluateXPath(xpath);
		List filtered = new ArrayList();
		
		// WMB prepends a @ on undefined MRM attributes
		String atName = "@" + name;
		
		for(int i = 0; i<matches.size(); i++) {
			MbElement elm = (MbElement) matches.get(i);
			if(name != null) {
				
				if(name.equals(elm.getName()) || atName.equals(elm.getName())) {
					if(ns == null || ns.equals(elm.getNamespace())) {
						filtered.add(elm);
						// there can be only one attribute with a specific name
						break;
					}
				}
				
			}else {
				filtered.add(elm);
			}
			
		}
		
		return filtered;

	}

	public String getAttribute(String name) throws MbException {
		return getAttribute(null, name);
	}

	public String getAttribute(String ns, String name) throws MbException {
		MbElement attr = getAttributeElement(ns, name);

		if (attr != null) {
			Object value = attr.getValue();
			if(value != null) {
				return value.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setAttribute(String name, String value) throws MbException {
		setAttribute(null, name, value);
	}

	public void setAttribute(String ns, String name, String value) throws MbException {
		MbElement attr = getAttributeElement(ns, name);

		if (attr == null) {
			attr = getMbElement().createElementAsFirstChild(
					XmlUtil.getAttributeType(getMbElement()), name, value);

			if (ns != null) {
				attr.setNamespace(ns);
			}
		} else {
			attr.setValue(value);
		}
	}

	public String[] getAttributeNames() throws MbException {
		return getAttributeNames(null);
	}

	public String[] getAttributeNames(String ns) throws MbException {
		List matches = getAttributeElements(null, null);

		String[] names = new String[matches.size()];

		for (int i = 0; i < names.length; i++) {
			String name = ((MbElement) matches.get(i)).getName();
			
			if(name.charAt(0) == '@') {
				// remove leading @, WMB prepends it in the MRM domain. Why oh god why. 
				name = name.substring(1);
			}
			
			names[i] = name;
		}

		return names;
	}

	public XmlElement createLastChild(String name) throws MbException {
		return createLastChild(null, name);
	}

	public XmlElement createLastChild(String ns, String name) throws MbException {
		MbElement parent = getMbElement();
		MbElement elm = parent.createElementAsLastChild(XmlUtil.getFolderElementType(parent), name,
				null);

		if (ns != null) {
			elm.setNamespace(ns);
		}

		return new XmlElement(elm, isReadOnly());
	}

	public List getChildrenByName(String name) throws MbException {
		return getChildrenByName(null, name);
	}

	public List getChildrenByName(String ns, String name) throws MbException {
		MbXPath xpath = new MbXPath(name, getMbElement());
		
		if(ns != null) {
			xpath.setDefaultNamespace(ns);
		}
		
		List childList = (List) getMbElement().evaluateXPath(xpath);
		List returnList = new ArrayList();
		for(int i = 0; i<childList.size(); i++) {
			returnList.add(new XmlElement((MbElement) childList.get(i), isReadOnly()));
		}
		
		return returnList;
	}

	public XmlElement getFirstChild() throws MbException {
		return getFirstChildByName(null, null);
	}

	
	public XmlElement getFirstChildByName(String name) throws MbException {
		return getFirstChildByName(null, name);
	}

	public XmlElement getFirstChildByName(String ns, String name) throws MbException {
		if(name == null) {
			name = "*";
		}
		
		MbXPath xpath = new MbXPath(name + "[1]", getMbElement());
		
		if(ns != null) {
			xpath.setDefaultNamespace(ns);
		}
		
		List childList = (List) getMbElement().evaluateXPath(xpath);

		if(childList.size() > 0 ) {
			return new XmlElement((MbElement) childList.get(0), isReadOnly());
		} else {
			return null;
		}
	}

	private Object getValue() throws MbException {
		return getMbElement().getValue();
	}

	public String getStringValue() throws MbException {
		Object value = getValue();
		
		if(value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	public int getIntValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return 0;
		} else if(value instanceof Integer) {
			return ((Integer)value).intValue();
		} else {
			return Integer.parseInt(value.toString());
		}
	}

	public long getLongValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return 0;
		} else if(value instanceof Long) {
			return ((Long)value).longValue();
		} else {
			return Long.parseLong(value.toString());
		}
	}

	public float getFloatValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return 0;
		} else if(value instanceof Float) {
			return ((Float)value).floatValue();
		} else {
			return Float.parseFloat(value.toString());
		}
	}

	public double getDoubleValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return 0;
		} else if(value instanceof Double) {
			return ((Double)value).doubleValue();
		} else {
			return Double.parseDouble(value.toString());
		}
	}

	public boolean getBooleanValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return false;
		} else if(value instanceof Boolean) {
			return ((Boolean)value).booleanValue();
		} else {
			return Boolean.parseBoolean(value.toString());
		}
	}

	public Date getDateValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return null;
		} else if (value instanceof MbTimestamp) {
			return ((MbTimestamp) getValue()).getTime();
		} else if (value instanceof MbDate) {
			return ((MbDate) getValue()).getTime();
		} else if(value instanceof MbTime){
			return ((MbTime) getValue()).getTime();
		} else {
			throw new ClassCastException("Type can not be cast to a date type: " + value.getClass());
		}
	}

	private void setValue(Object value) throws MbException {
		if(ElementUtil.isMRM(getMbElement())) {
			getMbElement().setValue(value);
		} else {
			getMbElement().setValue(value.toString());
		}
	}

	public void setStringValue(String value) throws MbException {
		setValue(value);
	}

	public void setIntValue(int value) throws MbException {
		setValue(new Integer(value));
	}

	public void setLongValue(long value) throws MbException {
		setValue(new Long(value));
	}

	public void setFloatValue(float value) throws MbException {
		setValue(new Float(value));
	}

	public void setDoubleValue(double value) throws MbException {
		setValue(new Double(value));
	}

	public void setBooleanValue(boolean value) throws MbException {
		setValue(new Boolean(value));
	}

	public void setTimeValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}

	public void setTimeValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbTime mbTime = new MbTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal
				.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
		setValue(mbTime);
	}

	public void setDateValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}
	
	public void setDateValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbDate mbDate = new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH));
		setValue(mbDate);
	}

	public void setTimestampValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}
	
	public void setTimestampValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbTimestamp mbTimestamp = new MbTimestamp(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
		setValue(mbTimestamp);
	}
}
