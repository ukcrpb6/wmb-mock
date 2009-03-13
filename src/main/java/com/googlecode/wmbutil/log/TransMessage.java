package com.googlecode.wmbutil.log;

import com.ibm.broker.plugin.MbMessageAssembly;


public class TransMessage {

    private String brokerName;
    private String nodeName;
    private String flowName;
    private String message;
    private String messageId;
    private String[] businessIds;
    private MbMessageAssembly messageAssembly;
    
    
    public TransMessage(String brokerName, String nodeName, String flowName, 
            String message, String messageId,
            String[] businessIds, 
            MbMessageAssembly messageAssembly) {
        this.brokerName = brokerName;
        this.nodeName = nodeName;
        this.flowName = flowName;
        this.message = message;
        this.messageId = messageId;
        this.messageAssembly = messageAssembly;
        this.businessIds = businessIds;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getFlowName() {
        return flowName;
    }
    
    public String getMessage() {
        return message;
    }
    public String getMessageId() {
        return messageId;
    }
    public String[] getBusinessIds() {
        return businessIds;
    }
    public MbMessageAssembly getMessageAssembly() {
        return messageAssembly;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(message);
                
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
        
        return sb.toString(); 
    }
    
}
