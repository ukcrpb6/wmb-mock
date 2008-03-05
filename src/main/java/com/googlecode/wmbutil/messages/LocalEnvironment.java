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
import java.util.List;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * Wrapper class for the local environment. Makes it easier to work with some
 * common attributes like destinations lists and routing labels.
 */
public class LocalEnvironment extends Header {

    /**
     * Wrap an existing local environment message, note that this is not the
     * same {@link MbMessage} as the one containing your data
     * 
     * @param msg The message to wrap
     * @param readOnly Indicates whether the message is read-only (input
     *            message) or not.
     * @return The wrapped message
     * @throws MbException
     */
    public static LocalEnvironment wrap(MbMessage msg, boolean readOnly) throws MbException {
        MbElement elm = msg.getRootElement();

        if (elm == null) {
            throw new NiceMbException("Failed to find root element");
        }

        return new LocalEnvironment(elm, readOnly);
    }

    private LocalEnvironment(MbElement elm, boolean readOnly) throws MbException {
        super(elm, readOnly);
    }

    /**
     * Get all router list labels.
     * 
     * @return The array of all router list labels
     * @throws MbException
     */
    public String[] getRouterListLabelNames() throws MbException {
        List labelElms = (List) getMbElement().evaluateXPath(
                "Destination/RouterList/DestinationData/labelName");

        String[] labels = new String[labelElms.size()];
        for (int i = 0; i < labelElms.size(); i++) {
            labels[i] = (String) ((MbElement) labelElms.get(i)).getValue();
        }

        return labels;
    }

    /**
     * Add a new router list label.
     * 
     * @param labelName The label name to use
     * @throws MbException
     */
    public void addRouterListLabelName(String labelName) throws MbException {
        getMbElement().evaluateXPath(
                "?Destination/?RouterList/?$DestinationData/?labelName[set-value('" + labelName
                        + "')]");
    }

    /**
     * Add a TimeoutRequest to the LocalEnvironment. For more information
     * regarding the parameters see the Websphere Message Broker documentation.
     * 
     * @param identifier Unique identifier for the message.
     * @param startDateTime A Date telling when the message will be forwarded.
     * @param interval The number of seconds between propagation of the message. A null value corresponds to the default value 0.
     * @param count A null value corresponds to the default value 1.
     * @param ignoreMissed A null value corresponds to the default value TRUE.
     * @param allowOverwrite A null value corresponds to the default value TRUE.
     * @throws MbException
     */
    public void addTimeoutRequest(String identifier, Date startDateTime, Integer interval,
            Integer count, Boolean ignoreMissed, Boolean allowOverwrite) throws MbException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String startDate = sdfDate.format(startDateTime);
        String startTime = sdfTime.format(startDateTime);

        getMbElement().evaluateXPath("?TimeoutRequest/?Action[set-value('SET')]");
        if (identifier != null || identifier.length() > 0) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?Identifier[set-value('" + identifier + "')]");
        }
        if (startDateTime != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?StartDate[set-value('" + startDate + "')]");
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?StartTime[set-value('" + startTime + "')]");
        }
        if (interval != null) {
            getMbElement()
                    .evaluateXPath("?TimeoutRequest/?Interval[set-value('" + interval + "')]");
        }
        if (count != null) {
            getMbElement().evaluateXPath("?TimeoutRequest/?Count[set-value('" + count + "')]");
        }
        if (ignoreMissed != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?IgnoreMissed[set-value('" + ignoreMissed + "')]");
        }
        if (allowOverwrite != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?AllowOverwrite[set-value('" + allowOverwrite + "')]");
        }

    }
}
