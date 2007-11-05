package com.googlecode.wmbutil.messages;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        return null;
    }

    public String getAttribute(String ns, String name) {
        return null;
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
    }

    public String[] getAttributeNames() {
        return null;
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

    public Object getValue() throws MbException {
        return getMbElement().getValue();
    }

    public String getStringValue() throws MbException {
        return (String) getMbElement().getValue();
    }

    public int getIntValue() throws MbException {
        return Integer.parseInt((String) getMbElement().getValue());
    }

    public long getLongValue() throws MbException {
        return Long.parseLong((String) getMbElement().getValue());
    }

    public float getFloatValue() throws MbException {
        return Float.parseFloat((String) getMbElement().getValue());
    }

    public double getDoubleValue() throws MbException {
        return Double.parseDouble((String) getMbElement().getValue());
    }

    public boolean getBooleanValue() throws MbException {
        return Boolean.getBoolean((String) getMbElement().getValue());
    }

    public Date getDateValue() throws MbException {
        if (getMbElement().getValue() instanceof MbTimestamp) {
            return ((MbTimestamp) getMbElement().getValue()).getTime();
        } else if (getMbElement().getValue() instanceof MbDate) {
            return ((MbDate) getMbElement().getValue()).getTime();
        } else {
            return ((MbTime) getMbElement().getValue()).getTime();
        }
    }

    public void setValue(Object value) throws MbException {
        getMbElement().setValue(value);
    }

    public void setStringValue(String value) throws MbException {
        getMbElement().setValue(value);
    }

    public void setIntValue(int value) throws MbException {
        getMbElement().setValue(new Integer(value));
    }

    public void setLongValue(long value) throws MbException {
        getMbElement().setValue(new Long(value));
    }

    public void setFloatValue(float value) throws MbException {
        getMbElement().setValue(new Float(value));
    }

    public void setDoubleValue(double value) throws MbException {
        getMbElement().setValue(new Double(value));
    }

    public void setBooleanValue(boolean value) throws MbException {
        getMbElement().setValue(new Boolean(value));
    }

    public void setTimeValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbTime mbTime = new MbTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal
                .get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        getMbElement().setValue(mbTime);
    }

    public void setDateValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbDate mbDate = new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH));
        getMbElement().setValue(mbDate);
    }

    public void setTimestampValue(Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbTimestamp mbTimestamp = new MbTimestamp(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        getMbElement().setValue(mbTimestamp);
    }
}
