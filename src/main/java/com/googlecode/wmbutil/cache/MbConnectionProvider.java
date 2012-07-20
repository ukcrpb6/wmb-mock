package com.googlecode.wmbutil.cache;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbNode;
import org.hibernate.service.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class MbConnectionProvider extends UserSuppliedConnectionProviderImpl {

    private final MbJavaComputeNode javaComputeNode;
    private final String configurableService;
    private final MbNode.JDBC_TransactionType transactionType;

    public MbConnectionProvider(MbJavaComputeNode javaComputeNode,
                                String configurableService,
                                MbNode.JDBC_TransactionType transactionType) {
        this.javaComputeNode = javaComputeNode;
        this.configurableService = configurableService;
        this.transactionType = transactionType;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return javaComputeNode.getJDBCType4Connection(configurableService, transactionType);
        } catch (MbException e) {
            throw new SQLException(e);
        }
    }
}
