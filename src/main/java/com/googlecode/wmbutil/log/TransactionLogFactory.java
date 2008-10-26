package com.googlecode.wmbutil.log;

import com.ibm.broker.plugin.MbNode;

public class TransactionLogFactory {

    public static TransactionLog createLog(MbNode node, String componentId) {
        return new DefaultTransactionLog(node, componentId);
    }
}
