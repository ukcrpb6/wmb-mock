package com.googlecode.wmbutil.log.dao;

import com.googlecode.wmbutil.dao.GenericDAOImpl;
import com.googlecode.wmbutil.dao.HibernateUtil;

import java.math.BigInteger;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LogMessageDAOImpl extends GenericDAOImpl<LogMessage, BigInteger> implements LogMessageDAO {
    public LogMessageDAOImpl(HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }
}
