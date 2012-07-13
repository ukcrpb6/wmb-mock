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

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

import java.io.UnsupportedEncodingException;

import static com.googlecode.wmbutil.messages.MbMQMDHeader.Properties.*;

public class MutableMbMQMDHeader extends Header implements MbMQMDHeader {

    MutableMbMQMDHeader(MbElement elm) throws MbException {
        super(elm);
    }

    @Override
    public void setVersion(int version) throws MbException {
        setIntValue(VERSION, version);
    }

    @Override
    public int getReport() throws MbException {
        return getIntValue(REPORT);
    }

    @Override
    public void setReport(int report) throws MbException {
        setIntValue(REPORT, report);
    }

    @Override
    public int getMsgType() throws MbException {
        return getIntValue(MSGTYPE);
    }

    @Override
    public void setMsgType(int msgType) throws MbException {
        setIntValue(MSGTYPE, msgType);
    }

    @Override
    public int getExpiry() throws MbException {
        return getIntValue(EXPIRY);
    }

    @Override
    public void setExpiry(int expiry) throws MbException {
        setIntValue(EXPIRY, expiry);
    }

    @Override
    public int getFeedback() throws MbException {
        return getIntValue(FEEDBACK);
    }

    @Override
    public void setFeedback(int feedback) throws MbException {
        setIntValue(FEEDBACK, feedback);
    }

    @Override
    public int getEncoding() throws MbException {
        return getIntValue(ENCODING);
    }

    @Override
    public void setEncoding(int encoding) throws MbException {
        setIntValue(ENCODING, encoding);
    }

    @Override
    public int getCodedCharSetId() throws MbException {
        return getIntValue(CODEDCHARSETID);
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        setIntValue(CODEDCHARSETID, codedCharSetId);
    }

    @Override
    public String getFormat() throws MbException {
        return getStringValue(FORMAT);
    }

    @Override
    public void setFormat(String format) throws MbException {
        setStringValue(FORMAT, format, 8);
    }

    @Override
    public int getPriority() throws MbException {
        return getIntValue(PRIORITY);
    }

    @Override
    public void setPriority(int priority) throws MbException {
        setIntValue(PRIORITY, priority);
    }

    @Override
    public int getPersistence() throws MbException {
        return getIntValue(PERSISTENCE);
    }

    @Override
    public void setPersistence(int persistence) throws MbException {
        setIntValue(PERSISTENCE, persistence);
    }

    @Override
    public byte[] getMsgId() throws MbException {
        return getByteArrayValue(MSGID);
    }

    @Override
    public String getMsgIdAsHex() throws MbException {
        try {
            return asHex(getMsgId());
        } catch (UnsupportedEncodingException e) {
            throw new NiceMbException(e.getMessage());
        }
    }

    @Override
    public void setMsgId(byte[] msgId) throws MbException {
        setByteArray(MSGID, msgId, 24);
    }

    @Override
    public byte[] getCorrelId() throws MbException {
        return getByteArrayValue(CORRELID);
    }

    @Override
    public String getCorrelIdAsHex() throws MbException {
        try {
            return asHex(getCorrelId());
        } catch (UnsupportedEncodingException e) {
            throw new NiceMbException(e.getMessage());
        }
    }

    @Override
    public void setCorrelId(byte[] correlId) throws MbException {
        setByteArray(CORRELID, correlId, 24);
    }

    @Override
    public int getBackoutCount() throws MbException {
        return getIntValue(BACKOUT_COUNT);
    }

    @Override
    public void setBackoutCount(int backoutCount) throws MbException {
        setIntValue(BACKOUT_COUNT, backoutCount);
    }

    @Override
    public String getReplyToQ() throws MbException {
        return getStringValue(REPLYTO_Q);
    }

    @Override
    public void setReplyToQ(String replyToQ) throws MbException {
        setStringValue(REPLYTO_Q, replyToQ, 48);
    }

    @Override
    public String getReplyToQMgr() throws MbException {
        return getStringValue(REPLYTO_QMGR);
    }

    @Override
    public void setReplyToQMgr(String replyToQMgr) throws MbException {
        setStringValue(REPLYTO_QMGR, replyToQMgr, 48);
    }

    @Override
    public String getUserIdentifier() throws MbException {
        return getStringValue(USER_IDENTIFIER);
    }

    @Override
    public void setUserIdentifier(String userIdentifier) throws MbException {
        setStringValue(USER_IDENTIFIER, userIdentifier, 12);
    }

    @Override
    public String getApplIdentityData() throws MbException {
        return getStringValue(APPL_IDENTITY_DATA);
    }

    @Override
    public void setApplIdentityData(String applIdentityData) throws MbException {
        setStringValue(APPL_IDENTITY_DATA, applIdentityData, 32);
    }

    @Override
    public int getPutApplType() throws MbException {
        return getIntValue(PUT_APPL_TYPE);
    }

    @Override
    public void setPutApplType(int putApplType) throws MbException {
        setIntValue(PUT_APPL_TYPE, putApplType);
    }

    @Override
    public String getPutApplName() throws MbException {
        return getStringValue(PUT_APPL_TYPE);
    }

    @Override
    public void setPutApplName(String putApplName) throws MbException {
        setStringValue(PUT_APPL_TYPE, putApplName, 28);
    }

    @Override
    public String getPutDate() throws MbException {
        return getStringValue(PUT_DATE);
    }

    @Override
    public void setPutDate(String putDate) throws MbException {
        setStringValue(PUT_DATE, putDate, 8);
    }

    @Override
    public String getPutTime() throws MbException {
        return getStringValue(PUT_TIME);
    }

    @Override
    public void setPutTime(String putTime) throws MbException {
        setStringValue(PUT_TIME, putTime, 8);
    }

    @Override
    public String getApplOriginData() throws MbException {
        return getStringValue(APPL_ORIGIN_DATA);
    }

    @Override
    public void setApplOriginData(String applOriginData) throws MbException {
        setStringValue(APPL_ORIGIN_DATA, applOriginData, 4);
    }

    @Override
    public byte[] getGroupId() throws MbException {
        return getByteArrayValue(GROUP_ID);
    }

    @Override
    public String getGroupIdAsHex() throws MbException {
        try {
            return asHex(getGroupId());
        } catch (UnsupportedEncodingException e) {
            throw new NiceMbException(e.getMessage());
        }
    }

    @Override
    public void setGroupId(byte[] groupId) throws MbException {
        setByteArray(GROUP_ID, groupId, 24);
    }

    @Override
    public int getMsgSeqNumber() throws MbException {
        return getIntValue(MSG_SEQ_NUMBER);
    }

    @Override
    public void setMsgSeqNumber(int msgSeqNumber) throws MbException {
        setIntValue(MSG_SEQ_NUMBER, msgSeqNumber);
    }

    @Override
    public int getOffset() throws MbException {
        return getIntValue(OFFSET);
    }

    @Override
    public void setOffset(int offset) throws MbException {
        setIntValue(OFFSET, offset);
    }

    @Override
    public int getMsgFlags() throws MbException {
        return getIntValue(MSGFLAGS);
    }

    @Override
    public void setMsgFlags(int msgFlags) throws MbException {
        setIntValue(MSGFLAGS, msgFlags);
    }

    // TODO: Move to helper class

    private static final byte[] HEX_CHAR_TABLE = {
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
    };

    /**
     * Converts raw byte array into a string representation
     *
     * @param raw byte array
     * @return String representation of the byte array
     * @throws UnsupportedEncodingException
     */
    private static String asHex(byte[] raw) throws UnsupportedEncodingException {
        byte[] hex = new byte[2 * raw.length];
        int index = 0;

        for (byte b : raw) {
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex, "ASCII"); //$NON-NLS-1$
    }
}
