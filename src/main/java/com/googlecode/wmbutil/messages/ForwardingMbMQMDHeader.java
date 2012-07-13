package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ForwardingMbMQMDHeader {
    private MbMQMDHeader delegate;

    public ForwardingMbMQMDHeader(MbMQMDHeader delegate) {
        this.delegate = delegate;
    }

    public void setVersion(int version) throws MbException {delegate.setVersion(version);}

    public void setMsgType(int msgType) throws MbException {delegate.setMsgType(msgType);}

    public void setOffset(int offset) throws MbException {delegate.setOffset(offset);}

    public int getPriority() throws MbException {return delegate.getPriority();}

    public int getCodedCharSetId() throws MbException {return delegate.getCodedCharSetId();}

    public byte[] getGroupId() throws MbException {return delegate.getGroupId();}

    public void setPutDate(String putDate) throws MbException {delegate.setPutDate(putDate);}

    public void setMsgFlags(int msgFlags) throws MbException {delegate.setMsgFlags(msgFlags);}

    public String getApplIdentityData() throws MbException {return delegate.getApplIdentityData();}

    public void setPutApplType(int putApplType) throws MbException {delegate.setPutApplType(putApplType);}

    public int getReport() throws MbException {return delegate.getReport();}

    public int getMsgFlags() throws MbException {return delegate.getMsgFlags();}

    public int getMsgType() throws MbException {return delegate.getMsgType();}

    public int getMsgSeqNumber() throws MbException {return delegate.getMsgSeqNumber();}

    public void setUserIdentifier(String userIdentifier) throws MbException {
        delegate.setUserIdentifier(userIdentifier);
    }

    public String getGroupIdAsHex() throws MbException {return delegate.getGroupIdAsHex();}

    public void setMsgSeqNumber(int msgSeqNumber) throws MbException {delegate.setMsgSeqNumber(msgSeqNumber);}

    public String getPutApplName() throws MbException {return delegate.getPutApplName();}

    public int getEncoding() throws MbException {return delegate.getEncoding();}

    public void setPutTime(String putTime) throws MbException {delegate.setPutTime(putTime);}

    public String getPutTime() throws MbException {return delegate.getPutTime();}

    public void setReplyToQ(String replyToQ) throws MbException {delegate.setReplyToQ(replyToQ);}

    public String getApplOriginData() throws MbException {return delegate.getApplOriginData();}

    public int getFeedback() throws MbException {return delegate.getFeedback();}

    public void setFeedback(int feedback) throws MbException {delegate.setFeedback(feedback);}

    public String getCorrelIdAsHex() throws MbException {return delegate.getCorrelIdAsHex();}

    public String getFormat() throws MbException {return delegate.getFormat();}

    public int getExpiry() throws MbException {return delegate.getExpiry();}

    public int getOffset() throws MbException {return delegate.getOffset();}

    public void setReplyToQMgr(String replyToQMgr) throws MbException {delegate.setReplyToQMgr(replyToQMgr);}

    public void setApplOriginData(String applOriginData) throws MbException {
        delegate.setApplOriginData(applOriginData);
    }

    public void setGroupId(byte[] groupId) throws MbException {delegate.setGroupId(groupId);}

    public void setApplIdentityData(String applIdentityData) throws MbException {
        delegate.setApplIdentityData(applIdentityData);
    }

    public String getReplyToQ() throws MbException {return delegate.getReplyToQ();}

    public String getUserIdentifier() throws MbException {return delegate.getUserIdentifier();}

    public void setPriority(int priority) throws MbException {delegate.setPriority(priority);}

    public String getMsgIdAsHex() throws MbException {return delegate.getMsgIdAsHex();}

    public void setPutApplName(String putApplName) throws MbException {delegate.setPutApplName(putApplName);}

    public void setPersistence(int persistence) throws MbException {delegate.setPersistence(persistence);}

    public int getPersistence() throws MbException {return delegate.getPersistence();}

    public void setBackoutCount(int backoutCount) throws MbException {delegate.setBackoutCount(backoutCount);}

    public byte[] getMsgId() throws MbException {return delegate.getMsgId();}

    public void setReport(int report) throws MbException {delegate.setReport(report);}

    public void setCorrelId(byte[] correlId) throws MbException {delegate.setCorrelId(correlId);}

    public String getReplyToQMgr() throws MbException {return delegate.getReplyToQMgr();}

    public void setCodedCharSetId(int codedCharSetId) throws MbException {delegate.setCodedCharSetId(codedCharSetId);}

    public int getBackoutCount() throws MbException {return delegate.getBackoutCount();}

    public byte[] getCorrelId() throws MbException {return delegate.getCorrelId();}

    public void setMsgId(byte[] msgId) throws MbException {delegate.setMsgId(msgId);}

    public int getPutApplType() throws MbException {return delegate.getPutApplType();}

    public void setFormat(String format) throws MbException {delegate.setFormat(format);}

    public void setExpiry(int expiry) throws MbException {delegate.setExpiry(expiry);}

    public void setEncoding(int encoding) throws MbException {delegate.setEncoding(encoding);}

    public String getPutDate() throws MbException {return delegate.getPutDate();}

}
