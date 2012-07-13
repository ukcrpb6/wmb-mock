package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbMQMDHeader {
    void setVersion(int version) throws MbException;

    int getReport() throws MbException;

    void setReport(int report) throws MbException;

    int getMsgType() throws MbException;

    void setMsgType(int msgType) throws MbException;

    int getExpiry() throws MbException;

    void setExpiry(int expiry) throws MbException;

    int getFeedback() throws MbException;

    void setFeedback(int feedback) throws MbException;

    int getEncoding() throws MbException;

    void setEncoding(int encoding) throws MbException;

    int getCodedCharSetId() throws MbException;

    void setCodedCharSetId(int codedCharSetId) throws MbException;

    String getFormat() throws MbException;

    void setFormat(String format) throws MbException;

    int getPriority() throws MbException;

    void setPriority(int priority) throws MbException;

    int getPersistence() throws MbException;

    void setPersistence(int persistence) throws MbException;

    byte[] getMsgId() throws MbException;

    String getMsgIdAsHex() throws MbException;

    void setMsgId(byte[] msgId) throws MbException;

    byte[] getCorrelId() throws MbException;

    String getCorrelIdAsHex() throws MbException;

    void setCorrelId(byte[] correlId) throws MbException;

    int getBackoutCount() throws MbException;

    void setBackoutCount(int backoutCount) throws MbException;

    String getReplyToQ() throws MbException;

    void setReplyToQ(String replyToQ) throws MbException;

    String getReplyToQMgr() throws MbException;

    void setReplyToQMgr(String replyToQMgr) throws MbException;

    String getUserIdentifier() throws MbException;

    void setUserIdentifier(String userIdentifier) throws MbException;

    String getApplIdentityData() throws MbException;

    void setApplIdentityData(String applIdentityData) throws MbException;

    int getPutApplType() throws MbException;

    void setPutApplType(int putApplType) throws MbException;

    String getPutApplName() throws MbException;

    void setPutApplName(String putApplName) throws MbException;

    String getPutDate() throws MbException;

    void setPutDate(String putDate) throws MbException;

    String getPutTime() throws MbException;

    void setPutTime(String putTime) throws MbException;

    String getApplOriginData() throws MbException;

    void setApplOriginData(String applOriginData) throws MbException;

    byte[] getGroupId() throws MbException;

    String getGroupIdAsHex() throws MbException;

    void setGroupId(byte[] groupId) throws MbException;

    int getMsgSeqNumber() throws MbException;

    void setMsgSeqNumber(int msgSeqNumber) throws MbException;

    int getOffset() throws MbException;

    void setOffset(int offset) throws MbException;

    int getMsgFlags() throws MbException;

    void setMsgFlags(int msgFlags) throws MbException;

    /**
     * @author Bob Browning <bob.browning@pressassociation.com>
     */
    public static class Properties {
        public static final String VERSION = "Version";
        public static final String REPORT = "Report";
        public static final String MSGTYPE = "MsgType";
        public static final String EXPIRY = "Expiry";
        public static final String FEEDBACK = "Feedback";
        public static final String ENCODING = "Encoding";
        public static final String CODEDCHARSETID = "CodedCharSetId";
        public static final String FORMAT = "Format";
        public static final String PRIORITY = "Priority";
        public static final String PERSISTENCE = "Persistence";
        public static final String MSGID = "MsgId";
        public static final String CORRELID = "CorrelId";
        public static final String BACKOUT_COUNT = "BackoutCount";
        public static final String REPLYTO_Q = "ReplyToQ";
        public static final String REPLYTO_QMGR = "ReplyToQMgr";
        public static final String USER_IDENTIFIER = "UserIdentifer";
        public static final String APPL_IDENTITY_DATA = "ApplIdentityData";
        public static final String PUT_APPL_TYPE = "PutApplType";
        public static final String GROUP_ID = "GroupId";
        public static final String MSG_SEQ_NUMBER = "MsgSeqNumber";
        public static final String OFFSET = "Offset";
        public static final String MSGFLAGS = "MsgFlags";
        public static final String PUT_DATE = "PutDate";
        public static final String PUT_TIME = "PutTime";
        public static final String APPL_ORIGIN_DATA = "ApplOriginData";
    }
}
