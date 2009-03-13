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

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbNode;

public class TransactionLogFactory {

    public static TransactionLog createLog(MbNode node, String componentId) {
        try {
            return new Log4jTransactionLog(node.getBroker().getName(), 
                    node.getName(), node.getMessageFlow().getName(), componentId);
        } catch (MbException e) {
            throw new RuntimeException("Failed to create log", e);
        }
    }

    // only for testing purposes
    static TransactionLog createLog(String brokerName, String nodeName, String flowName,
            String componentId) {
        return new Log4jTransactionLog(brokerName, nodeName, flowName, componentId);
    }

}
