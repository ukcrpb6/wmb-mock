package com.googlecode.wmbutil.messages;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.googlecode.wmbutil.util.ElementUtil;
import com.googlecode.wmbutil.util.XmlUtil;
import com.ibm.broker.plugin.MbDate;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbTime;
import com.ibm.broker.plugin.MbTimestamp;

public class XmlElement extends MbElementWrapper {

    public XmlElement(MbElement wrappedElm, boolean readOnly) throws MbException {
        super(wrappedElm, readOnly);
    }

    public String getAttribute(String name) {
    	// TODO implement
    	throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getAttribute(String ns, String name) {
    	// TODO implement
    	throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setAttribute(String name, String value) throws MbException {
        MbElement attr = getMbElement().getFirstElementByPath("@" + name);

        if (attr == null) {
            attr = getMbElement().createElementAsFirstChild(
                    XmlUtil.getAttributeType(getMbElement()), name, value);
        } else {
            attr.setValue(value);
        }
    }

    public void setAttribute(String ns, String name, String value) {
    	// TODO implement
    	throw new UnsupportedOperationException("Not yet implemented");
    }

    public String[] getAttributeNames() {
    	// TODO implement
    	throw new UnsupportedOperationException("Not yet implemented");
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

    private Object getValue() throws MbException {
    	if(ElementUtil.isMRM(getMbElement())) {
        	// TODO implement for MRM
    		throw new UnsupportedOperationException("Not yet implemented for MRM");
    	} else {
    		return getMbElement().getValue();
    	}
    }

    public String getStringValue() throws MbException {
        return (String) getValue();
    }

    public int getIntValue() throws MbException {
        return Integer.parseInt((String) getValue());
    }

    public long getLongValue() throws MbException {
        return Long.parseLong((String) getValue());
    }

    public float getFloatValue() throws MbException {
        return Float.parseFloat((String) getValue());
    }

    public double getDoubleValue() throws MbException {
        return Double.parseDouble((String) getValue());
    }

    public boolean getBooleanValue() throws MbException {
        return Boolean.getBoolean((String) getValue());
    }

    public Date getDateValue() throws MbException {
        if (getMbElement().getValue() instanceof MbTimestamp) {
            return ((MbTimestamp) getValue()).getTime();
        } else if (getMbElement().getValue() instanceof MbDate) {
            return ((MbDate) getValue()).getTime();
        } else {
            return ((MbTime) getValue()).getTime();
        }
    }

    private void setValue(Object value) throws MbException {
    	if(ElementUtil.isMRM(getMbElement())) {
    		getMbElement().createElementAsLastChild(MbElement.TYPE_VALUE, null, value);
    	} else {
    	    getMbElement().setValue(value);
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

    public void setTimeValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbTime mbTime = new MbTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal
                .get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        setValue(mbTime);
    }

    public void setDateValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbDate mbDate = new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH));
        setValue(mbDate);
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
