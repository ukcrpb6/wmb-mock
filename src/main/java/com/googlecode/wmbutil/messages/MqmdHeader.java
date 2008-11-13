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
import com.ibm.broker.plugin.MbMessage;

public class MqmdHeader extends Header {

    public static final String FORMAT_BINARY = "        ";

    public static final String FORMAT_MQSTR = "MQSTR   ";

    public static final String FORMAT_RFH2 = "MQHRF2  ";

    private static final String PROPERTY_VERSION = "Version";

    private static final String PROPERTY_REPORT = "Report";

    private static final String PROPERTY_MSGTYPE = "MsgType";

    private static final String PROPERTY_EXPIRY = "Expiry";

    private static final String PROPERTY_FEEDBACK = "Feedback";

    private static final String PROPERTY_ENCODING = "Encoding";

    private static final String PROPERTY_CODEDCHARSETID = "CodedCharSetId";

    private static final String PROPERTY_FORMAT = "Format";

    private static final String PROPERTY_PRIORITY = "Priority";

    private static final String PROPERTY_PERSISTENCE = "Persistence";

    private static final String PROPERTY_MSGID = "MsgId";

    private static final String PROPERTY_CORRELID = "CorrelId";

    private static final String PROPERTY_BACKOUT_COUNT = "BackoutCount";

    private static final String PROPERTY_REPLYTO_Q = "ReplyToQ";

    private static final String PROPERTY_REPLYTO_QMGR = "ReplyToQMgr";

    private static final String PROPERTY_USER_IDENTIFIER = "UserIdentifer";

    private static final String PROPERTY_APPL_IDENTITY_DATA = "ApplIdentityData";

    private static final String PROPERTY_PUT_APPL_TYPE = "PutApplType";

    private static final String PROPERTY_GROUP_ID = "GroupId";

    private static final String PROPERTY_MSG_SEQ_NUMBER = "MsgSeqNumber";

    private static final String PROPERTY_OFFSET = "Offset";

    private static final String PROPERTY_MSGFLAGS = "MsgFlags";

    private static final String PROPERTY_PUT_DATE = "PutDate";

    private static final String PROPERTY_PUT_TIME = "PutTime";

    private static final String PROPERTY_APPL_ORIGIN_DATA = "ApplOriginData";

    public static MqmdHeader wrap(MbMessage msg, boolean readOnly)
            throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/MQMD");

        if (elm == null) {
            throw new NiceMbException("Failed to find MQMD");
        }

        return new MqmdHeader(elm, readOnly);
    }

    public static MqmdHeader create(MbMessage msg) throws MbException {
        if (has(msg)) {
            throw new NiceMbException("Already have MQMD header");
        }

        MbElement elm = msg.getRootElement().createElementAsLastChild("MQHMD");

        MqmdHeader mqmd = new MqmdHeader(elm, false);

        mqmd.setVersion(2);
        mqmd.setReport(0);
        mqmd.setFormat("        ");
        mqmd.setEncoding(546);
        mqmd.setCodedCharSetId(437);
        mqmd.setMsgType(8);
        mqmd.setExpiry(-1);
        mqmd.setFeedback(0);
        mqmd.setPriority(0);
        mqmd.setPersistence(1);
        mqmd.setBackoutCount(0);
        mqmd.setOffset(0);
        mqmd.setMsgSeqNumber(1);
        mqmd.setMsgFlags(0);

        return mqmd;
    }

    public static MqmdHeader wrapOrCreate(MbMessage msg) throws MbException {
        if (has(msg)) {
            return wrap(msg, false);
        } else {
            return create(msg);
        }
    }

    public static MqmdHeader remove(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/MQMD");

        if (elm != null) {
            elm.detach();
            return new MqmdHeader(elm, true);
        } else {
            throw new NiceMbException("Failed to find MQMD");
        }
    }

    public static boolean has(MbMessage msg) throws MbException {
        MbElement elm = msg.getRootElement().getFirstElementByPath("/MQMD");
        return elm != null;
    }

    private MqmdHeader(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);

    }

    private void setVersion(int version) throws MbException {
        setIntValue(PROPERTY_VERSION, version);
    }

    public int getReport() throws MbException {
        return getIntValue(PROPERTY_REPORT);
    }

    public void setReport(int report) throws MbException {
        setIntValue(PROPERTY_REPORT, report);
    }

    public int getMsgType() throws MbException {
        return getIntValue(PROPERTY_MSGTYPE);
    }

    public void setMsgType(int msgType) throws MbException {
        setIntValue(PROPERTY_MSGTYPE, msgType);
    }

    public int getExpiry() throws MbException {
        return getIntValue(PROPERTY_EXPIRY);
    }

    public void setExpiry(int expiry) throws MbException {
        setIntValue(PROPERTY_EXPIRY, expiry);
    }

    public int getFeedback() throws MbException {
        return getIntValue(PROPERTY_FEEDBACK);
    }

    public void setFeedback(int feedback) throws MbException {
        setIntValue(PROPERTY_FEEDBACK, feedback);
    }

    public int getEncoding() throws MbException {
        return getIntValue(PROPERTY_ENCODING);
    }

    public void setEncoding(int encoding) throws MbException {
        setIntValue(PROPERTY_ENCODING, encoding);
    }

    public int getCodedCharSetId() throws MbException {
        return getIntValue(PROPERTY_CODEDCHARSETID);
    }

    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        setIntValue(PROPERTY_CODEDCHARSETID, codedCharSetId);
    }

    public String getFormat() throws MbException {
        return getStringValue(PROPERTY_FORMAT);
    }

    public void setFormat(String format) throws MbException {
        setStringValue(PROPERTY_FORMAT, format, 8);
    }

    public int getPriority() throws MbException {
        return getIntValue(PROPERTY_PRIORITY);
    }

    public void setPriority(int priority) throws MbException {
        setIntValue(PROPERTY_PRIORITY, priority);
    }

    public int getPersistence() throws MbException {
        return getIntValue(PROPERTY_PERSISTENCE);
    }

    public void setPersistence(int persistence) throws MbException {
        setIntValue(PROPERTY_PERSISTENCE, persistence);
    }

    public byte[] getMsgId() throws MbException {
        return getByteArrayValue(PROPERTY_MSGID);
    }

    private String asHex(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toHexString((b[i] & 0xff) + 0x100).substring(1));
        }
        return result.toString();

    }

    public String getMsgIdAsHex() throws MbException {
        return asHex(getMsgId());
    }

    public void setMsgId(byte[] msgId) throws MbException {
        setByteArray(PROPERTY_MSGID, msgId, 24);
    }

    public byte[] getCorrelId() throws MbException {
        return getByteArrayValue(PROPERTY_CORRELID);
    }

    public String getCorrelIdAsHex() throws MbException {
        return asHex(getCorrelId());
    }

    public void setCorrelId(byte[] correlId) throws MbException {
        setByteArray(PROPERTY_CORRELID, correlId, 24);
    }

    public int getBackoutCount() throws MbException {
        return getIntValue(PROPERTY_BACKOUT_COUNT);
    }

    public void setBackoutCount(int backoutCount) throws MbException {
        setIntValue(PROPERTY_BACKOUT_COUNT, backoutCount);
    }

    public String getReplyToQ() throws MbException {
        return getStringValue(PROPERTY_REPLYTO_Q);
    }

    public void setReplyToQ(String replyToQ) throws MbException {
        setStringValue(PROPERTY_REPLYTO_Q, replyToQ, 48);
    }

    public String getReplyToQMgr() throws MbException {
        return getStringValue(PROPERTY_REPLYTO_QMGR);
    }

    public void setReplyToQMgr(String replyToQMgr) throws MbException {
        setStringValue(PROPERTY_REPLYTO_QMGR, replyToQMgr, 48);
    }

    public String getUserIdentifier() throws MbException {
        return getStringValue(PROPERTY_USER_IDENTIFIER);
    }

    public void setUserIdentifier(String userIdentifier) throws MbException {
        setStringValue(PROPERTY_USER_IDENTIFIER, userIdentifier, 12);
    }

    public String getApplIdentityData() throws MbException {
        return getStringValue(PROPERTY_APPL_IDENTITY_DATA);
    }

    public void setApplIdentityData(String applIdentityData) throws MbException {
        setStringValue(PROPERTY_APPL_IDENTITY_DATA, applIdentityData, 32);
    }

    public int getPutApplType() throws MbException {
        return getIntValue(PROPERTY_PUT_APPL_TYPE);
    }

    public void setPutApplType(int putApplType) throws MbException {
        setIntValue(PROPERTY_PUT_APPL_TYPE, putApplType);
    }

    public String getPutApplName() throws MbException {
        return getStringValue(PROPERTY_PUT_APPL_TYPE);
    }

    public void setPutApplName(String putApplName) throws MbException {
        setStringValue(PROPERTY_PUT_APPL_TYPE, putApplName, 28);
    }

    public String getPutDate() throws MbException {
        return getStringValue(PROPERTY_PUT_DATE);
    }

    public void setPutDate(String putDate) throws MbException {
        setStringValue(PROPERTY_PUT_DATE, putDate, 8);
    }

    public String getPutTime() throws MbException {
        return getStringValue(PROPERTY_PUT_TIME);
    }

    public void setPutTime(String putTime) throws MbException {
        setStringValue(PROPERTY_PUT_TIME, putTime, 8);
    }

    public String getApplOriginData() throws MbException {
        return getStringValue(PROPERTY_APPL_ORIGIN_DATA);
    }

    public void setApplOriginData(String applOriginData) throws MbException {
        setStringValue(PROPERTY_APPL_ORIGIN_DATA, applOriginData, 4);
    }

    public byte[] getGroupId() throws MbException {
        return getByteArrayValue(PROPERTY_GROUP_ID);
    }

    public String getGroupIdAsHex() throws MbException {
        return asHex(getGroupId());
    }
    
    public void setGroupId(byte[] groupId) throws MbException {
        setByteArray(PROPERTY_GROUP_ID, groupId, 24);
    }

    public int getMsgSeqNumber() throws MbException {
        return getIntValue(PROPERTY_MSG_SEQ_NUMBER);
    }

    public void setMsgSeqNumber(int msgSeqNumber) throws MbException {
        setIntValue(PROPERTY_MSG_SEQ_NUMBER, msgSeqNumber);
    }

    public int getOffset() throws MbException {
        return getIntValue(PROPERTY_OFFSET);
    }

    public void setOffset(int offset) throws MbException {
        setIntValue(PROPERTY_OFFSET, offset);
    }

    public int getMsgFlags() throws MbException {
        return getIntValue(PROPERTY_MSGFLAGS);
    }

    public void setMsgFlags(int msgFlags) throws MbException {
        setIntValue(PROPERTY_MSGFLAGS, msgFlags);
    }
}
