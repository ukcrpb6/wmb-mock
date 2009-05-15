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

/**
 * Helper class for working with XML elements.
 * 
 */
public class XmlElement extends MbElementWrapper {
	/**
	 * 
	 * @param wrappedElm
	 * @param readOnly
	 * @throws MbException
	 */
	public XmlElement(MbElement wrappedElm, boolean readOnly) throws MbException {
		super(wrappedElm, readOnly);
	}
	
	/**
	 * Gets the element name
	 * 
	 * @return Element name
	 * @throws MbException
	 */
	public String getName() throws MbException {
		return getMbElement().getName();
	}

	/**
	 * Gets the element namespace
	 * 
	 * @return Element namespace
	 * @throws MbException
	 */
	public String getNameSpace() throws MbException {
		return getMbElement().getNamespace();
	}
	
	/**
	 * Checks if the element has an attribute of the specified name 
	 * 
	 * @param name Attribute name
	 * @return True if the attribute exists
	 * @throws MbException
	 */
	public boolean hasAttribute(String name) throws MbException {
		return hasAttribute(null, name);
	}
	
	/**
	 * Checks if the element has an attribute of the specified name and namespace
	 * 
	 * @param ns Attribute namespace
	 * @param name Attribute name
	 * @return True if the attribute exists
	 * @throws MbException
	 */
	public boolean hasAttribute(String ns, String name) throws MbException {
		return getAttributeElement(ns, name) != null;
	}
	
	/**
	 * Gets an attribute of the specified name and namespace.
	 * Only returns the first attribute as multiple attributes
	 * with the same name is invalid XML
	 * 
	 * @param ns Attribute namespace
	 * @param name Attribute name
	 * @return An attribute element
	 * @throws MbException
	 */
	private MbElement getAttributeElement(String ns, String name) throws MbException {
		List elms = getAttributeElements(ns, name);
		
		if(elms.size() > 0) {
			return (MbElement) elms.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets all attributes. Attribute name and/or namespace can also be specified to
	 * filter wanted attributes
	 * 
	 * @param ns Attribute(s) namespace
	 * @param name Attribute(s) name
	 * @return All found attribute elements
	 * @throws MbException
	 */
	private List getAttributeElements(String ns, String name) throws MbException {
		MbXPath xpath = new MbXPath("@*", getMbElement());

		if (ns != null) {
			xpath.setDefaultNamespace(ns);
		}

		List matches = (List) getMbElement().evaluateXPath(xpath);
		List filtered = new ArrayList();
		
		// WMB prepends a @ on undefined MRM attributes
		String atName = "@" + name;
		
		for(int i = 0; i < matches.size(); i++) {
			MbElement elm = (MbElement) matches.get(i);
			if(name != null) {
				
				if(name.equals(elm.getName()) || atName.equals(elm.getName())) {
					if(ns == null || ns.equals(elm.getNamespace())) {
						filtered.add(elm);
						// there can be only one attribute with a specific name
						break;
					}
				}
				
			} else {
				filtered.add(elm);
			}
			
		}
		
		return filtered;

	}

	/**
	 * Gets an attribute of the specified name
	 * 
	 * @param name Attribute name
	 * @return The attribute, if found
	 * @throws MbException
	 */
	public String getAttribute(String name) throws MbException {
		return getAttribute(null, name);
	}

	/**
	 * Gets an attribute of the specified name & namespace
	 * 
	 * @param ns Attribute namespace
	 * @param name Attribute name
	 * @return The attribute, if found
	 * @throws MbException
	 */
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

	/**
	 * Sets an attribute of the specified name
	 * The attribute is automatically created if needed
	 * 
	 * @param name Attribute name
	 * @param value Attribute value
	 * @throws MbException
	 */
	public void setAttribute(String name, String value) throws MbException {
		setAttribute(null, name, value);
	}

	/**
	 * Sets an attribute of the specified name & namspace
	 * 
	 * @param ns Attribute namespace
	 * @param name Attribute name
	 * @param value Attribute value
	 * @throws MbException
	 */
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

	/**
	 * Gets the names of all attributes
	 * 
	 * @return The names of all attributes
	 * @throws MbException
	 */
	public String[] getAttributeNames() throws MbException {
		return getAttributeNames(null);
	}

	/**
	 * Gets the names of all attributes of the specified namespace
	 * 
	 * @param ns Attribute namespace
	 * @return The names of all attributes
	 * @throws MbException
	 */
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

	/**
	 * Creates a new element as the last child using the specified name
	 * 
	 * @param name Element name
	 * @return The newly created element
	 * @throws MbException
	 */
	public XmlElement createLastChild(String name) throws MbException {
		return createLastChild(null, name);
	}

	/**
	 * Creates a new element as the last child using the specified name & namespace
	 * 
	 * @param ns Element namespace
	 * @param name Element name
	 * @return The newly created element
	 * @throws MbException
	 */
	public XmlElement createLastChild(String ns, String name) throws MbException {
		MbElement parent = getMbElement();
		MbElement elm = parent.createElementAsLastChild(XmlUtil.getFolderElementType(parent), name,
				null);

		if (ns != null) {
			elm.setNamespace(ns);
		}

		return new XmlElement(elm, isReadOnly());
	}

	/**
	 * Gets all children elements of the specified name
	 * 
	 * @param name Children element(s) name
	 * @return All found elements, if any
	 * @throws MbException
	 */
	public List getChildrenByName(String name) throws MbException {
		return getChildrenByName(null, name);
	}

	/**
	 * Gets all children elements of the specified name & namespace
	 * 
	 * @param ns Children element(s) namespace
	 * @param name Children element(s) name
	 * @return All found elements
	 * @throws MbException
	 */
	public List getChildrenByName(String ns, String name) throws MbException {
		MbXPath xpath = new MbXPath(name, getMbElement());
		
		if(ns != null) {
			xpath.setDefaultNamespace(ns);
		}
		
		List childList = (List) getMbElement().evaluateXPath(xpath);
		List returnList = new ArrayList();
		for(int i = 0; i < childList.size(); i++) {
			returnList.add(new XmlElement((MbElement) childList.get(i), isReadOnly()));
		}
		
		return returnList;
	}

	/**
	 * Gets the first child element
	 * 
	 * @return The first child element
	 * @throws MbException
	 */
	public XmlElement getFirstChild() throws MbException {
		return getFirstChildByName(null, null);
	}

	/**
	 * Gets the first child element of the specified name
	 * 
	 * @param name Child element name
	 * @return The first child element
	 * @throws MbException
	 */
	public XmlElement getFirstChildByName(String name) throws MbException {
		return getFirstChildByName(null, name);
	}

	/**
	 * Gets the first child element of the specified name & namespace
	 * 
	 * @param ns Child element namespace
	 * @param name Child element name
	 * @return The first child element
	 * @throws MbException
	 */
	public XmlElement getFirstChildByName(String ns, String name) throws MbException {
		if(name == null) {
			name = "*";
		}
		
		MbXPath xpath = new MbXPath(name + "[1]", getMbElement());
		
		if(ns != null) {
			xpath.setDefaultNamespace(ns);
		}
		
		List childList = (List) getMbElement().evaluateXPath(xpath);

		if(childList.size() > 0) {
			return new XmlElement((MbElement) childList.get(0), isReadOnly());
		} else {
			return null;
		}
	}

	/**
	 * Gets the value
	 * 
	 * @return Element value
	 * @throws MbException
	 */
	private Object getValue() throws MbException {
		return getMbElement().getValue();
	}

	/**
	 * Gets the value as a string
	 * 
	 * @return Element value as a string
	 * @throws MbException
	 */
	public String getStringValue() throws MbException {
		Object value = getValue();
		
		if(value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	/**
	 * Gets the value as an integer
	 * 
	 * @return Element value as an integer
	 * @throws MbException
	 */
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

	/**
	 * Gets the value as a long
	 * 
	 * @return Element value as a long
	 * @throws MbException
	 */
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

	/**
	 * Gets the value as a float
	 * 
	 * @return Element value as a float
	 * @throws MbException
	 */
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

	/**
	 * Gets the value as a double
	 * 
	 * @return Element value as a double
	 * @throws MbException
	 */
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

	/**
	 * Gets the value as a boolean
	 * 
	 * @return Element value as a boolean
	 * @throws MbException
	 */
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

	/**
	 * Gets the value as a date
	 * 
	 * @return Element value as a date
	 * @throws MbException
	 */
	public Date getDateValue() throws MbException {
		Object value = getValue();

		if(value == null) {
			return null;
		} else if (value instanceof MbTimestamp) {
			return ((MbTimestamp) getValue()).getTime();
		} else if (value instanceof MbDate) {
			return ((MbDate) getValue()).getTime();
		} else if(value instanceof MbTime) {
			return ((MbTime) getValue()).getTime();
		} else {
			throw new ClassCastException("Type can not be cast to a date type: " + value.getClass());
		}
	}

	/**
	 * Sets the value
	 * 
	 * @param value Element value
	 * @throws MbException
	 */
	private void setValue(Object value) throws MbException {
		if(ElementUtil.isMRM(getMbElement())) {
			getMbElement().setValue(value);
		} else {
			getMbElement().setValue(value.toString());
		}
	}

	/**
	 * Sets the value of a string
	 * 
	 * @param value Element value string
	 * @throws MbException
	 */
	public void setStringValue(String value) throws MbException {
		setValue(value);
	}

	/**
	 * Sets the value of an integer
	 * 
	 * @param value Integer element value
	 * @throws MbException
	 */
	public void setIntValue(int value) throws MbException {
		setValue(new Integer(value));
	}

	/**
	 * Sets the value of a long
	 * 
	 * @param value Long Element value
	 * @throws MbException
	 */
	public void setLongValue(long value) throws MbException {
		setValue(new Long(value));
	}

	/**
	 * Sets the value of a float
	 * 
	 * @param value Float element value
	 * @throws MbException
	 */
	public void setFloatValue(float value) throws MbException {
		setValue(new Float(value));
	}

	/**
	 * Sets the value of a double
	 * 
	 * @param value Double element value
	 * @throws MbException
	 */
	public void setDoubleValue(double value) throws MbException {
		setValue(new Double(value));
	}

	/**
	 * Sets the value of a boolean
	 * 
	 * @param value Boolean element value
	 * @throws MbException
	 */
	public void setBooleanValue(boolean value) throws MbException {
		setValue(new Boolean(value));
	}

	/**
	 * Sets the time value of a date with the specified dateformat
	 * 
	 * @param value Date element value
	 * @param df Value dateformat
	 * @throws MbException
	 */
	public void setTimeValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}

	/**
	 * Sets the time value of a date. 
	 * 
	 * @param value Date element value
	 * @throws MbException
	 */
	public void setTimeValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbTime mbTime = new MbTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal
				.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
		setValue(mbTime);
	}

	/**
	 * Sets the value of a date with the specified dateformat 
	 * 
	 * @param value Date element value
	 * @param df Value dateformat
	 * @throws MbException
	 */
	public void setDateValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}
	
	/**
	 * Sets the value of a date
	 * 
	 * @param value Date element value
	 * @throws MbException
	 */
	public void setDateValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbDate mbDate = new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH));
		setValue(mbDate);
	}

	/**
	 * Sets the value of a date with the specified dateformat.
	 * 
	 * @param value Date element value
	 * @param df Value dateformat
	 * @throws MbException
	 */
	public void setTimestampValue(Date value, DateFormat df) throws MbException {
		setValue(df.format(value));
	}
	
	/**
	 * Sets the timestamp value of a date
	 * 
	 * @param value Date element value
	 * @throws MbException
	 */
	public void setTimestampValue(Date value) throws MbException {
		Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		MbTimestamp mbTimestamp = new MbTimestamp(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
		setValue(mbTimestamp);
	}
}
