package com.googlecode.wmbutil.dao;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbNode;

import java.sql.Connection;

import static com.google.common.base.Preconditions.checkNotNull;

public class MbConnectionProvider implements MbProvider<Connection> {
    private final MbNode node;
    private final String serviceName;
    private final MbNode.JDBC_TransactionType transactionType;

    public MbConnectionProvider(MbNode node, String serviceName, MbNode.JDBC_TransactionType transactionType) {
        this.node = node;
        this.serviceName = serviceName;
        this.transactionType = transactionType;
    }

    @Override
    public Connection get() throws MbException {
        return node.getJDBCType4Connection(serviceName, transactionType);
    }

    public static MbConnectionProvider create(
            MbNode node, String serviceName, MbNode.JDBC_TransactionType transactionType) {
        return new MbConnectionProvider(checkNotNull(node), checkNotNull(serviceName), checkNotNull(transactionType));
    }
}