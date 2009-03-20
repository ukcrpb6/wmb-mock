/*
 * Copyright 2007 (C) Callista Enterprise.
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

package com.googlecode.wmbutil.messages;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A TimeoutRequest is used by the TimeoutControl/TimeoutNotification nodes to
 * propagate messages according to its content. For more information regarding
 * the TimeoutRequest attributes, see the IBM Websphere Message Broker
 * documentation.
 * 
 */
public class TimeoutRequest {
    public static final String ACTION_SET = "SET";
    public static final String ACTION_CANCEL = "CANCEL";

    private String action;
    private String identifier;
    private Date startDateTime;
    private Integer interval;
    private Integer count;
    private boolean ignoreMissed;
    private boolean allowOverwrite;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

    /**
     * Class constructor
     *
     */
    public TimeoutRequest() {
        this.action = TimeoutRequest.ACTION_SET;
        // Default values in WMB
        this.ignoreMissed = true;
        this.allowOverwrite = true;
    }

    /**
     * Class constructor using an identifier & start date/time
     * 
     * @param identifier An alphanumeric identifier  
     * @param startDateTime When the Timeout should occur
     */
    public TimeoutRequest(String identifier, Date startDateTime) {
        this.action = TimeoutRequest.ACTION_SET;
        this.identifier = identifier;
        this.startDateTime = startDateTime;        
        // Default values in WMB
        this.ignoreMissed = true;
        this.allowOverwrite = true;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean getAllowOverwrite() {
        return allowOverwrite;
    }

    public void setAllowOverwrite(boolean allowOverwrite) {
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

    public boolean getIgnoreMissed() {
        return ignoreMissed;
    }

    public void setIgnoreMissed(boolean ignoreMissed) {
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
