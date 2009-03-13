/*
 * Copyright 2008 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmbutil.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbMessageAssembly;

public class Log4jTransactionLog extends AbstractTransactionLog {

    private Logger log;
    
    private String brokerName;
    private String nodeName;
    private String flowName;

    public Log4jTransactionLog(String brokerName, String nodeName, String flowName, String componentId) {
        log = Logger.getLogger(prepareForLoggerName("wmb." + brokerName + "." + componentId + "." + flowName + "." + nodeName));
        
        this.brokerName = brokerName;
        this.nodeName = nodeName;
        this.flowName = flowName;
    }
    
    private static String prepareForLoggerName(String s) {
        return s.replace(" ", "");
    }

    protected void transLog(Level level, String message, String messageId,
            String[] businessIds, MbMessageAssembly assembly, Throwable t) {
        if (log.isEnabledFor(level)) {
            TransactionMessage msg = new TransactionMessage(brokerName, nodeName, flowName, message, messageId,
                    businessIds, assembly);

            log.log(level, msg, t);
        }
    }
}
