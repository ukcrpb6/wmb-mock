package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbMQRFH2HeaderImpl extends AbstractMbHeader implements MbMQRFH2Header {

    MbMQRFH2HeaderImpl(MbElement elm) throws MbException {
        super(elm, MbHeaderType.MQRFH2);
    }

    private Object getProperty(String area, String name) throws MbException {
        MbElement valueElm = getMbElement().getFirstElementByPath(area + "/" + name);
        return (valueElm != null) ? valueElm.getValue() : null;
    }

    private void setProperty(String area, String name, Object value) throws MbException {
        checkState(!isReadOnly(), "MbElement is immutable");

        MbElement areaElm = getMbElement().getFirstElementByPath(area);
        if (areaElm == null) {
            areaElm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME);
            areaElm.setName(area);
        }

        MbElement valueElm = areaElm.getFirstElementByPath(name);

        if (valueElm == null) {
            areaElm.createElementAsLastChild(MbElement.TYPE_NAME, name, value);
        } else {
            valueElm.setValue(value);
        }
    }

    @Override
    public String getStringProperty(String area, String name) throws MbException {
        return (String) getProperty(area, name);
    }

    @Override
    public void setStringProperty(String area, String name, String value) throws MbException {
        setProperty(area, name, value);
    }


    @Override
    public int getIntProperty(String area, String name) throws MbException {
        return (Integer) getProperty(area, name);
    }

    @Override
    public void setIntProperty(String area, String name, int value) throws MbException {
        setProperty(area, name, value);
    }

    @Override
    public int getCodedCharSetId() throws MbException {
        return (Integer) getValue(CODEDCHARSETID);
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        setValue(CODEDCHARSETID, codedCharSetId);
    }

    @Override
    public int getEncoding() throws MbException {
        return (Integer) getValue(ENCODING);
    }

    @Override
    public void setEncoding(int encoding) throws MbException {
        setValue(ENCODING, encoding);
    }

    @Override
    public int getFlags() throws MbException {
        return (Integer) getValue(FLAGS);
    }

    @Override
    public void setFlags(int flags) throws MbException {
        setValue(FLAGS, flags);
    }

    @Override
    public String getFormat() throws MbException {
        return getValue(FORMAT);
    }

    @Override
    public void setFormat(String format) throws MbException {
        setFixedStringValue(FORMAT, format, 8);
    }

    @Override
    public int getNameValueCCSID() throws MbException {
        return (Integer) getValue(NAME_VALUE_CODEDCHARSETID);
    }

    @Override
    public void setNameValueCCSID(int nameValueCCSID) throws MbException {
        setValue(NAME_VALUE_CODEDCHARSETID, nameValueCCSID);
    }
}
