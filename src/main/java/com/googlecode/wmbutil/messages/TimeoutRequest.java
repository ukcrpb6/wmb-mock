package com.googlecode.wmbutil.messages;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeoutRequest {
    public static final String ACTION_SET = "SET";
    public static final String ACTION_CANCEL = "CANCEL";

    private String action;
    private String identifier;
    private Date startDateTime;
    private Integer interval;
    private Integer count;
    private Boolean ignoreMissed;
    private Boolean allowOverwrite;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

    public TimeoutRequest() {
        this.action = TimeoutRequest.ACTION_SET;
    }

    public TimeoutRequest(String identifier, Date startDateTime) {
        this.action = TimeoutRequest.ACTION_SET;
        this.identifier = identifier;
        this.startDateTime = startDateTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getAllowOverwrite() {
        return allowOverwrite;
    }

    public void setAllowOverwrite(Boolean allowOverwrite) {
        this.allowOverwrite = allowOverwrite;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getIgnoreMissed() {
        return ignoreMissed;
    }

    public void setIgnoreMissed(Boolean ignoreMissed) {
        this.ignoreMissed = ignoreMissed;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStartTime() {
        String startTime = null;
        if (this.startDateTime != null) {
            startTime = sdfTime.format(startDateTime);
        }
        return startTime;
    }

    public String getStartDate() {
        String startDate = null;
        if (this.startDateTime != null) {
            startDate = sdfDate.format(startDateTime);
        }
        return startDate;
    }
}
