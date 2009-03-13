package com.googlecode.wmbutil.log;

import java.io.File;

import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;

public class TransactionLoggerRunner {

    public static void main(String[] args) throws MbException {
        System.setProperty("broker.workpath", new File("src/test/resources").getAbsolutePath());

        Logger log4jLogger = Logger.getLogger("TestLogger");
        log4jLogger.info("Should not go to JDBC appender");
        
        TransactionLog logger = TransactionLogFactory.createLog("My Broker", "My Node", "Some Flow", "MyComponent");
        logger.debug("A debug message", "Msg123", (String[])null, (MbMessageAssembly)null);
        logger.error("An error message", "Msg456", new String[]{"Bus123", "Bus456"}, null, new Exception("Bad bad bad"));
    }
}
