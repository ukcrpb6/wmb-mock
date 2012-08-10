package com.googlecode.wmbutil.dao.hibernate;

import org.hibernate.ConnectionReleaseMode;
import org.hibernate.engine.transaction.spi.TransactionCoordinator;
import org.hibernate.engine.transaction.spi.TransactionFactory;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbTransactionFactory implements TransactionFactory<MbTransaction> {
  @Override public MbTransaction createTransaction(TransactionCoordinator coordinator) {
    return new MbTransaction(coordinator);
  }

  @Override public boolean canBeDriver() {
    return true;
  }

  @Override public boolean compatibleWithJtaSynchronization() {
    return false;
  }

  @Override
  public boolean isJoinableJtaTransaction(TransactionCoordinator transactionCoordinator, MbTransaction transaction) {
    return false;
  }

  @Override public ConnectionReleaseMode getDefaultReleaseMode() {
    return ConnectionReleaseMode.ON_CLOSE;
  }
}
