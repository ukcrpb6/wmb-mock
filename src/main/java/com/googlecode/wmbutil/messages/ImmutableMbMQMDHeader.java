package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ImmutableMbMQMDHeader extends MutableMbMQMDHeader {

    public ImmutableMbMQMDHeader(MbElement elm) throws MbException {
        super(elm);
    }

    @Override
    public void setApplIdentityData(String applIdentityData) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setApplOriginData(String applOriginData) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBackoutCount(int backoutCount) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCodedCharSetId(int codedCharSetId) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCorrelId(byte[] correlId) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEncoding(int encoding) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExpiry(int expiry) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFeedback(int feedback) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFormat(String format) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGroupId(byte[] groupId) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMsgFlags(int msgFlags) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMsgId(byte[] msgId) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMsgSeqNumber(int msgSeqNumber) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMsgType(int msgType) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOffset(int offset) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistence(int persistence) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPriority(int priority) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPutApplName(String putApplName) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPutApplType(int putApplType) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPutDate(String putDate) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPutTime(String putTime) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setReplyToQ(String replyToQ) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setReplyToQMgr(String replyToQMgr) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setReport(int report) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserIdentifier(String userIdentifier) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVersion(int version) throws MbException {
        throw new UnsupportedOperationException();
    }
}
