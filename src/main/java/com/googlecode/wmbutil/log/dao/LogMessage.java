package com.googlecode.wmbutil.log.dao;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LogMessage {

    @Id
    @GeneratedValue
    private BigInteger id;

    @Column
    private String brokerName;

    @Column
    private String flowName;

    @Column
    private String componentName;

    @Column
    private String nodeName;

    @Column
    private String messageId;

    @Column
    private String message;

    @Column
    private String exception;

    @Column
    private Date updated;

    public LogMessage(String brokerName,
                      String flowName,
                      String componentName,
                      String nodeName,
                      String messageId,
                      String message,
                      String exception,
                      Date updated) {
        this.brokerName = brokerName;
        this.componentName = componentName;
        this.nodeName = nodeName;
        this.exception = exception;
        this.flowName = flowName;
        this.message = message;
        this.messageId = messageId;
        this.updated = updated;
    }

    public String getLoggerName() {
        return "wmb." + brokerName + "." + componentName + "." + flowName + "." + nodeName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
