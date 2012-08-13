package com.googlecode.wmbutil.logging.dao;

import com.googlecode.wmbutil.dao.hibernate.GenericDAOImpl;
import com.googlecode.wmbutil.dao.hibernate.HibernateUtil;

import java.math.BigInteger;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LogMessageDAOImpl extends GenericDAOImpl<LogMessage, BigInteger> implements LogMessageDAO {
    public LogMessageDAOImpl(HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }
}
