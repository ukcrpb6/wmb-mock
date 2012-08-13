package com.googlecode.wmbutil.dao;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbProvider<T> {
  T get() throws MbException;
}
