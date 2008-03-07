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
     * @param timeoutRequest A TimeoutRequest object.
     * @throws MbException Throws exception if identifier is missing.
     */
    public void addTimeoutRequest(TimeoutRequest timeoutRequest) throws MbException {
        // <Action>
        getMbElement().evaluateXPath(
                "?TimeoutRequest/?Action[set-value('" + timeoutRequest.getAction() + "')]");

        // <Identifier>
        if (timeoutRequest.getIdentifier() != null || timeoutRequest.getIdentifier().length() > 0) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?Identifier[set-value('" + timeoutRequest.getIdentifier()
                            + "')]");
        } else {
            throw new NiceMbException("Missing identifier attribute on TimeoutRequest");
        }

        // <StartDate> + <StartTime>
        if (timeoutRequest.getStartDateTime() != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?StartDate[set-value('" + timeoutRequest.getStartDate()
                            + "')]");
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?StartTime[set-value('" + timeoutRequest.getStartTime()
                            + "')]");
        }

        // <Interval>
        if (timeoutRequest.getInterval() != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?Interval[set-value('" + timeoutRequest.getInterval() + "')]");
        }

        // <Count>
        if (timeoutRequest.getCount() != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?Count[set-value('" + timeoutRequest.getCount() + "')]");
        }

        // <IgnoreMissed>
        if (timeoutRequest.getIgnoreMissed() != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?IgnoreMissed[set-value('" + timeoutRequest.getIgnoreMissed()
                            + "')]");
        }

        // <AllowOverwrite>
        if (timeoutRequest.getAllowOverwrite() != null) {
            getMbElement().evaluateXPath(
                    "?TimeoutRequest/?AllowOverwrite[set-value('"
                            + timeoutRequest.getAllowOverwrite() + "')]");
        }
    }
}
