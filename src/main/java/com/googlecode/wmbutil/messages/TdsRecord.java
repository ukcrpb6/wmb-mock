package com.googlecode.wmbutil.messages;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbDate;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbTime;
import com.ibm.broker.plugin.MbTimestamp;

public class TdsRecord extends MbElementWrapper {

    public TdsRecord(MbElement wrappedElm, boolean readOnly) throws MbException {
        super(wrappedElm, readOnly);
    }

    private Object getField(String name) throws MbException {
        MbElement elm = getMbElement().getFirstElementByPath(name);

        if (elm != null) {
            return elm.getValue();
        } else {
            throw new NiceMbException("Unknown field: " + name);
        }
    }

    private void setField(String name, Object value) throws MbException {
        checkReadOnly();

        MbElement elm = getMbElement().getFirstElementByPath(name);
        if (elm == null) {
            elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME_VALUE, name, value);
        } else {
            elm.setValue(value);
        }
    }

    public String getStringField(String name) throws MbException {
        return (String) getField(name);
    }

    public void setStringField(String name, String value) throws MbException {
        setField(name, value);
    }

    public int getIntField(String name) throws MbException {
        return ((Integer) getField(name)).intValue();
    }

    public void setIntField(String name, int value) throws MbException {
        setField(name, new Integer(value));
    }

    public Date getDateField(String name) throws MbException {
        return ((MbTimestamp) getField(name)).getTime();
    }

    /**
     * Sets the date for a known element. The date is extracted out of a Date object and an MbDate object is created.
     * @param name The element whos value is to be changed.
     * @param value A Date object that contains a specific date.
     * @throws MbException
     */
    public void setDateField(String name, Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbDate mbDate = new MbDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH));
        setField(name, mbDate);
    }

    /**
     * Sets the time for a known element. The time is extracted out of a Date object and an MbTime object is created.
     * @param name The element whos value is to be changed.
     * @param value A Date object that contains a specific time.
     * @throws MbException
     */
    public void setTimeField(String name, Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbTime mbTime = new MbTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal
                .get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        setField(name, (MbTime) mbTime);
    }

    /**
     * Sets the timestamp for a known element. The timestamp is extracted out of a Date object and an MbTimestamp object is created.
     * @param name The element whos value is to be changed.
     * @param value A Date object that contains a specific timestamp.
     * @throws MbException
     */
    public void setTimestampField(String name, Date value) throws MbException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(value);
        MbTimestamp mbTimestamp = new MbTimestamp(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        setField(name, (MbTimestamp) mbTimestamp);
    }
}
