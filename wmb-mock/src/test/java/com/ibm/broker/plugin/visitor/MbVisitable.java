package com.ibm.broker.plugin.visitor;

import com.ibm.broker.plugin.MbException;

public interface MbVisitable {
    void accept(MbMessageVisitor visitor) throws MbException;
}
