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

package com.googlecode.wmbutil.messages.header;

import com.googlecode.wmbutil.util.HexUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

import static com.googlecode.wmbutil.messages.header.MbMQMDHeader.Properties.*;

public class MutableMbMQMDHeader extends AbstractMbHeader implements MbMQMDHeader {

    MutableMbMQMDHeader(MbElement elm) throws MbException {
        super(elm, MbHeaderType.MQMD);
    }

    @Override
    public void setVersion(int version) throws MbException {
        setValue(VERSION, version);
    }

    @Override
    public int getReport() throws MbException {
        return (Integer) getValue(REPORT);
    }

    @Override
    public void setReport(int report) throws MbException {
        setValue(REPORT, report);
    }

    @Override
    public int getMsgType() throws MbException {
        return (Integer) getValue(MSGTYPE);
    }

    @Override
    public void setMsgType(int msgType) throws MbException {
        setValue(MSGTYPE, msgType);
    }

    @Override
    public int getExpiry() throws MbException {
        return (Integer) getValue(EXPIRY);
    }

    @Override
    public void setExpiry(int expiry) throws MbException {
        setValue(EXPIRY, expiry);
    }

    @Override
    public int getFeedback() throws MbException {
        return (Integer) getValue(FEEDBACK);
    }

    @Override
    public void setFeedback(int feedback) throws MbException {
        setValue(FEEDBACK, feedback);
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
    public int getCodedCharSetId() throws MbException {
        return (Integer) getValue(CODEDCHARSETID);
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        setValue(CODEDCHARSETID, codedCharSetId);
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
    public int getPriority() throws MbException {
        return (Integer) getValue(PRIORITY);
    }

    @Override
    public void setPriority(int priority) throws MbException {
        setValue(PRIORITY, priority);
    }

    @Override
    public int getPersistence() throws MbException {
        return (Integer) getValue(PERSISTENCE);
    }

    @Override
    public void setPersistence(int persistence) throws MbException {
        setValue(PERSISTENCE, persistence);
    }

    @Override
    public byte[] getMsgId() throws MbException {
        return getValue(MSGID);
    }

    @Override
    public String getMsgIdAsHex() throws MbException {
        return HexUtils.asHex(getMsgId());
    }

    @Override
    public void setMsgId(byte[] msgId) throws MbException {
        setFixedByteArrayValue(MSGID, msgId, 24);
    }

    @Override
    public byte[] getCorrelId() throws MbException {
        return getValue(CORRELID);
    }

    @Override
    public String getCorrelIdAsHex() throws MbException {
        return HexUtils.asHex(getCorrelId());
    }

    @Override
    public void setCorrelId(byte[] correlId) throws MbException {
        setFixedByteArrayValue(CORRELID, correlId, 24);
    }

    @Override
    public int getBackoutCount() throws MbException {
        return (Integer) getValue(BACKOUT_COUNT);
    }

    @Override
    public void setBackoutCount(int backoutCount) throws MbException {
        setValue(BACKOUT_COUNT, backoutCount);
    }

    @Override
    public String getReplyToQ() throws MbException {
        return getValue(REPLYTO_Q);
    }

    @Override
    public void setReplyToQ(String replyToQ) throws MbException {
        setFixedStringValue(REPLYTO_Q, replyToQ, 48);
    }

    @Override
    public String getReplyToQMgr() throws MbException {
        return getValue(REPLYTO_QMGR);
    }

    @Override
    public void setReplyToQMgr(String replyToQMgr) throws MbException {
        setFixedStringValue(REPLYTO_QMGR, replyToQMgr, 48);
    }

    @Override
    public String getUserIdentifier() throws MbException {
        return getValue(USER_IDENTIFIER);
    }

    @Override
    public void setUserIdentifier(String userIdentifier) throws MbException {
        setFixedStringValue(USER_IDENTIFIER, userIdentifier, 12);
    }

    @Override
    public String getApplIdentityData() throws MbException {
        return getValue(APPL_IDENTITY_DATA);
    }

    @Override
    public void setApplIdentityData(String applIdentityData) throws MbException {
        setFixedStringValue(APPL_IDENTITY_DATA, applIdentityData, 32);
    }

    @Override
    public int getPutApplType() throws MbException {
        return (Integer) getValue(PUT_APPL_TYPE);
    }

    @Override
    public void setPutApplType(int putApplType) throws MbException {
        setValue(PUT_APPL_TYPE, putApplType);
    }

    @Override
    public String getPutApplName() throws MbException {
        return getValue(PUT_APPL_TYPE);
    }

    @Override
    public void setPutApplName(String putApplName) throws MbException {
        setFixedStringValue(PUT_APPL_TYPE, putApplName, 28);
    }

    @Override
    public String getPutDate() throws MbException {
        return getValue(PUT_DATE);
    }

    @Override
    public void setPutDate(String putDate) throws MbException {
        setFixedStringValue(PUT_DATE, putDate, 8);
    }

    @Override
    public String getPutTime() throws MbException {
        return getValue(PUT_TIME);
    }

    @Override
    public void setPutTime(String putTime) throws MbException {
        setFixedStringValue(PUT_TIME, putTime, 8);
    }

    @Override
    public String getApplOriginData() throws MbException {
        return getValue(APPL_ORIGIN_DATA);
    }

    @Override
    public void setApplOriginData(String applOriginData) throws MbException {
        setFixedStringValue(APPL_ORIGIN_DATA, applOriginData, 4);
    }

    @Override
    public byte[] getGroupId() throws MbException {
        return getValue(GROUP_ID);
    }

    @Override
    public String getGroupIdAsHex() throws MbException {
        return HexUtils.asHex(getGroupId());
    }

    @Override
    public void setGroupId(byte[] groupId) throws MbException {
        setFixedByteArrayValue(GROUP_ID, groupId, 24);
    }

    @Override
    public int getMsgSeqNumber() throws MbException {
        return (Integer) getValue(MSG_SEQ_NUMBER);
    }

    @Override
    public void setMsgSeqNumber(int msgSeqNumber) throws MbException {
        setValue(MSG_SEQ_NUMBER, msgSeqNumber);
    }

    @Override
    public int getOffset() throws MbException {
        return (Integer) getValue(OFFSET);
    }

    @Override
    public void setOffset(int offset) throws MbException {
        setValue(OFFSET, offset);
    }

    @Override
    public int getMsgFlags() throws MbException {
        return (Integer) getValue(MSGFLAGS);
    }

    @Override
    public void setMsgFlags(int msgFlags) throws MbException {
        setValue(MSGFLAGS, msgFlags);
    }

}
