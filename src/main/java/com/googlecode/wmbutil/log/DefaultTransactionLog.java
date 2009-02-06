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

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNode;

public class DefaultTransactionLog implements TransactionLog {

    private Logger log;
    
    public DefaultTransactionLog(MbNode node, String componentId) {
        try {
            log = Logger.getLogger(componentId + "." + node.getMessageFlow().getName());
        } catch (MbException e) {
            // fall back
            log = Logger.getLogger(componentId);
        }
    }

    private void log(Level level, String message, String messageId, String[] businessIds) {
        log(level, message, messageId, businessIds, null);
    }
    
    private void log(Level level, String message, String messageId, String[] businessIds, Throwable t) {
        if(log.isEnabledFor(level)) {
            
            StringBuffer sb = new StringBuffer();
            sb.append(message);
            
            sb.append(" [ComponentMsgFlow: ");
            sb.append(log.getName());
            sb.append("]");
            
            if(messageId != null) {
                sb.append(" [MessageId: ");
                sb.append(messageId);
                sb.append("]");
            }
            if(businessIds != null) {
                sb.append(" [BusinessIds: ");
                for(int i = 0; i<businessIds.length; i++) {
                    if(i > 0) {
                        sb.append(", ");
                    }
                    sb.append(businessIds[i]);
                }
                sb.append("]");
            }
            log.log(level, sb.toString(), t);
        }        
    }
    
    public void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        log(Level.DEBUG, message, messageId, businessIds);
    }

    public void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        log(Level.INFO, message, messageId, businessIds);
    }

    public void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        log(Level.WARN, message, messageId, businessIds);
    }

    public void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly) {
        log(Level.ERROR, message, messageId, businessIds);
    }

    public void debug(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        log(Level.DEBUG, message, messageId, new String[]{businessId});
        
    }

    public void error(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        log(Level.ERROR, message, messageId, new String[]{businessId});
        
    }

    public void info(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        log(Level.INFO, message, messageId, new String[]{businessId});
        
    }

    public void warn(String message, String messageId, String businessId, MbMessageAssembly assembly) {
        log(Level.WARN, message, messageId, new String[]{businessId});
        
    }

    public void debug(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        log(Level.DEBUG, message, messageId, businessIds, t);
        
    }

    public void error(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        log(Level.ERROR, message, messageId, businessIds, t);
        
    }

    public void info(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        log(Level.INFO, message, messageId, businessIds, t);
        
    }

    public void warn(String message, String messageId, String[] businessIds, MbMessageAssembly assembly,
            Throwable t) {
        log(Level.WARN, message, messageId, businessIds, t);
        
    }
}
