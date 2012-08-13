package com.googlecode.wmbutil.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.engine.transaction.internal.jdbc.JdbcIsolationDelegate;
import org.hibernate.engine.transaction.spi.*;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

import javax.transaction.Synchronization;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbTransaction implements TransactionImplementor {

  private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, MbTransaction.class.getName());

  private final TransactionCoordinator transactionCoordinator;

  private boolean valid = true;

  private LocalStatus localStatus = LocalStatus.ACTIVE; // NOT_ACTIVE
  private int timeout = -1;

  protected MbTransaction(TransactionCoordinator transactionCoordinator) {
    this.transactionCoordinator = transactionCoordinator;
  }

  @Override
  public void invalidate() {
    valid = false;
  }

  /**
   * Provide subclasses with access to the transaction coordinator.
   *
   * @return This transaction's context.
   */
  protected TransactionCoordinator transactionCoordinator() {
    return transactionCoordinator;
  }

  @Override
  public void registerSynchronization(Synchronization synchronization) {
    transactionCoordinator().getSynchronizationRegistry().registerSynchronization(synchronization);
  }

  @Override
  public LocalStatus getLocalStatus() {
    return localStatus;
  }

  @Override
  public boolean isActive() {
    return localStatus == LocalStatus.ACTIVE && doExtendedActiveCheck();
  }

  @Override
  public boolean isParticipating() {
    return getJoinStatus() == JoinStatus.JOINED && isActive();
  }

  @Override
  public boolean wasCommitted() {
    return localStatus == LocalStatus.COMMITTED;
  }

  @Override
  public boolean wasRolledBack() throws HibernateException {
    return localStatus == LocalStatus.ROLLED_BACK;
  }

  /**
   * Active has been checked against local state.  Perform any needed checks against resource transactions.
   *
   * @return {@code true} if the extended active check checks out as well; false otherwise.
   */
  protected boolean doExtendedActiveCheck() {
    return true;
  }

  @Override
  public boolean isInitiator() {
    return false;
  }

  @Override
  public void begin() throws HibernateException {
//    throw new UnsupportedOperationException();
    try {
      throw new TransactionException("Transaction handled by Websphere Message Broker");
    } finally {
      localStatus = LocalStatus.ROLLED_BACK;
      invalidate();
    }
  }

  @Override
  public void commit() throws HibernateException {
//    throw new UnsupportedOperationException();
    try {
      throw new TransactionException("Transaction handled by Websphere Message Broker");
    } finally {
      localStatus = LocalStatus.ROLLED_BACK;
      invalidate();
    }
  }

  @Override
  public void rollback() throws HibernateException {
//    throw new UnsupportedOperationException();
    try {
      throw new TransactionException("Transaction handled by Websphere Message Broker");
    } finally {
      localStatus = LocalStatus.ROLLED_BACK;
      invalidate();
    }
  }

  @Override
  public void setTimeout(int seconds) {
    timeout = seconds;
  }

  @Override
  public int getTimeout() {
    return timeout;
  }

  @Override public IsolationDelegate createIsolationDelegate() {
    return new JdbcIsolationDelegate(transactionCoordinator);
  }

  @Override public JoinStatus getJoinStatus() {
    return isActive() ? JoinStatus.JOINED : JoinStatus.NOT_JOINED;
  }

  @Override
  public void markForJoin() {}

  @Override
  public void join() {}

  @Override
  public void resetJoinStatus() {}

  @Override
  public void markRollbackOnly() {}
}
