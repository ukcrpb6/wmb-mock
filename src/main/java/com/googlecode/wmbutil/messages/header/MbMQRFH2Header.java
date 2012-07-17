package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbMQRFH2Header extends MbHeader {
    String getStringProperty(String area, String name) throws MbException;

    void setStringProperty(String area, String name, String value) throws MbException;

    int getIntProperty(String area, String name) throws MbException;

    void setIntProperty(String area, String name, int value) throws MbException;

    /**
     * @return Returns the codedCharSetId.
     * @throws com.ibm.broker.plugin.MbException
     */
    int getCodedCharSetId() throws MbException;

    /**
     * @param codedCharSetId The codedCharSetId to set.
     * @throws com.ibm.broker.plugin.MbException
     */
    void setCodedCharSetId(int codedCharSetId) throws MbException;

    /**
     * @return Returns the encoding.
     * @throws com.ibm.broker.plugin.MbException
     */
    int getEncoding() throws MbException;

    /**
     * @param encoding The encoding to set.
     * @throws com.ibm.broker.plugin.MbException
     */
    void setEncoding(int encoding) throws MbException;

    /**
     * @return Returns the flags.
     * @throws com.ibm.broker.plugin.MbException
     */
    int getFlags() throws MbException;

    /**
     * @param flags The flags to set.
     * @throws com.ibm.broker.plugin.MbException
     */
    void setFlags(int flags) throws MbException;

    /**
     * @return Returns the format.
     * @throws com.ibm.broker.plugin.MbException
     */
    String getFormat() throws MbException;

    /**
     * @param format The format to set.
     * @throws com.ibm.broker.plugin.MbException
     */
    void setFormat(String format) throws MbException;

    /**
     * @return Returns the nameValueCCSID.
     * @throws com.ibm.broker.plugin.MbException
     */
    int getNameValueCCSID() throws MbException;

    /**
     * @param nameValueCCSID The nameValueCCSID to set.
     * @throws com.ibm.broker.plugin.MbException
     */
    void setNameValueCCSID(int nameValueCCSID) throws MbException;
}
